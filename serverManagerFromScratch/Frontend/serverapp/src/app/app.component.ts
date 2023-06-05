import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { ServerService } from './service/server.service';
import { catchError, map, startWith } from 'rxjs/operators';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { DataState } from './enum/data-state.enum';
import { Status } from './enum/status';
import { Server } from './interface/server';
import { NgForm } from '@angular/forms';
import { NotificationService } from './service/notification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  //probably the most important line of the app. By default Angular has an always check for changes strategy. Since the reactive approach is used all over the app (Observables and subscription to them with the async pipe), I can change the stategy to OnPush. This way changes will only be detected on @Input change, Event Emits or Observable Emits (which is used here), thus improving the performance.
})
export class AppComponent implements OnInit {
  appState$: Observable<AppState<CustomResponse>>;
  readonly DataState = DataState;
  readonly Status = Status;

  private dataSubject = new BehaviorSubject<CustomResponse>(null!);
  private filterSubject = new BehaviorSubject<string>('');
  filterStatus$ = this.filterSubject.asObservable();

  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();

  private isDeletingId = new BehaviorSubject<number>(-1);
  isDeletingId$ = this.isDeletingId.asObservable();

  constructor(
    private serverService: ServerService,
    private toaster: NotificationService
  ) {
    this.appState$ = of({
      dataState: DataState.LOADING_STATE,
    });
  }

  ngOnInit(): void {
    this.appState$ = this.serverService.servers$.pipe(
      map((response: CustomResponse) => {
        this.dataSubject.next(response);
        return {
          dataState: DataState.LOADED_STATE,
          appData: response,
        };
      }),
      startWith({
        dataState: DataState.LOADING_STATE,
      }),
      catchError((err: any) => {
        console.log(err);
        this.toaster.onError(err.message);
        return of({
          dataState: DataState.ERROR_STATE,
          error: err,
        });
      })
    );
  }

  pingServer(ipAddress: string): void {
    this.filterSubject.next(ipAddress);

    this.appState$ = this.serverService.ping$(ipAddress).pipe(
      map((response: CustomResponse) => {
        this.updateServers(response);
        this.filterSubject.next('');

        response.message === 'Ping success'
          ? this.toaster.onSuccess(response.message)
          : this.toaster.onWarning(response.message);
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value,
        };
      }),
      startWith({
        dataState: DataState.LOADED_STATE,
        appData: this.dataSubject.value,
      }),
      catchError((err: any) => {
        this.filterSubject.next('');
        this.toaster.onError(err.message);
        return of({
          dataState: DataState.ERROR_STATE,
          error: err,
        });
      })
    );
  }

  filterServers(status: Status): void {
    this.appState$ = this.serverService
      .filter$(status, this.dataSubject.value)
      .pipe(
        map((response: CustomResponse) => {
          this.toaster.onDefault(response.message);
          return {
            dataState: DataState.LOADED_STATE,
            appData: response,
          };
        }),
        startWith({
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value,
        }),
        catchError((err: string) => {
          return of({
            dataState: DataState.ERROR_STATE,
            error: err,
          });
        })
      );
  }

  saveServer(serverForm: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.serverService.save$(serverForm.value).pipe(
      map((response: CustomResponse) => {
        if (response.data.server) {
          const currentServers = this.dataSubject.value.data.servers;

          currentServers?.unshift(response.data.server);

          if (currentServers) {
            this.dataSubject.next({
              ...response,
              data: {
                servers: currentServers,
              },
            });
          }
        }

        //TODO: Maybe there's a more elegant way
        document.getElementById('closeModal')?.click();
        this.isLoading.next(false);
        this.toaster.onSuccess(response.message);
        serverForm.resetForm({ status: this.Status.SERVER_DOWN });

        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value,
        };
      }),
      startWith({
        dataState: DataState.LOADED_STATE,
        appData: this.dataSubject.value,
      }),
      catchError((err: any) => {
        this.toaster.onError(err.message);
        this.isLoading.next(false);
        return of({
          dataState: DataState.ERROR_STATE,
          error: err,
        });
      })
    );
  }

  deleteServer(id: number): void {
    this.isDeletingId.next(id);
    this.appState$ = this.serverService.delete$(id).pipe(
      map((response: CustomResponse) => {
        if (this.dataSubject.value.data.servers) {
          const deleteServer = this.dataSubject.value.data.servers.filter(
            (server) => server.id !== id
          );

          this.dataSubject.next({
            ...this.dataSubject.value,
            data: {
              servers: deleteServer,
            },
          });
        }

        this.toaster.onSuccess(response.message);
        this.isDeletingId.next(-1);
        return {
          dataState: DataState.LOADED_STATE,
          appData: this.dataSubject.value,
        };
      }),
      startWith({
        dataState: DataState.LOADED_STATE,
        appData: this.dataSubject.value,
      }),
      catchError((err: any) => {
        this.isDeletingId.next(-1);
        this.filterSubject.next('');
        this.toaster.onError(err.message);
        return of({
          dataState: DataState.ERROR_STATE,
          error: err,
        });
      })
    );
  }

  private updateServers(response: CustomResponse) {
    if (this.dataSubject.value.data.servers) {
      const currentServers = this.dataSubject.value.data.servers;
      const pingedServerIndex = currentServers.findIndex((server: Server) => {
        return response.data.server
          ? server.id === response.data.server.id
          : -1;
      });

      if (response.data.server && pingedServerIndex > -1) {
        currentServers[pingedServerIndex] = response.data.server;
      }
    }
  }

  printReport(): void {
    //to save it as pdf
    // window.print();
    //to download as excel
    let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
    let tableSelect = document.getElementById('servers');
    let tableHtml = tableSelect?.outerHTML.replace(/ /g, '%20');
    let downloadLink = document.createElement('a');
    document.body.appendChild(downloadLink);
    downloadLink.href = 'data:' + dataType + ', ' + tableHtml;
    downloadLink.download = 'server-report.xls';
    downloadLink.click();
    document.body.removeChild(downloadLink);
    this.toaster.onInfo('Report downloaded');
  }
}
