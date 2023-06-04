import { CustomResponse } from './../interface/custom-response';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Server } from '../interface/server';
import { Status } from '../enum/status';

@Injectable({
  providedIn: 'root',
})
export class ServerService {
  private readonly apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  //Normal procedural approach
  // getServers(): Observable<CustomResponse> {
  //   return this.http.get<CustomResponse>('http://8080/server/list');
  // }

  //Reactive approach

  //alternative solution
  // servers2$ = this.http
  //   .get<CustomResponse>(`${this.apiUrl}/server/list`)
  //   .pipe(
  //     tap(console.log),
  //     catchError(this.handleError)
  //   ) as Observable<CustomResponse>;

  //casting <>
  //servers$: Observable<CustomResponse>
  //otherwise it would be Observable<any>
  servers$ = <Observable<CustomResponse>>(
    this.http
      .get<CustomResponse>(`${this.apiUrl}/server/list`)
      .pipe(tap(console.log), catchError(this.handleError))
  );

  save$ = (server: Server) =>
    <Observable<CustomResponse>>(
      this.http
        .post<CustomResponse>(`${this.apiUrl}/server/save`, server)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  ping$ = (ipAddress: string) =>
    <Observable<CustomResponse>>(
      this.http
        .get<CustomResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  //TODO: This below is hideous. Server side filtering would be preferable
  filter$ = (status: Status, response: CustomResponse) =>
    <Observable<CustomResponse>>new Observable<CustomResponse>((subscriber) => {
      console.log(response);
      subscriber.next(
        status === Status.ALL
          ? { ...response, message: `Servers filtered by ${status} status` }
          : {
              ...response,
              //At this point we will have already made a call for all the servers and would have gotten an error, so we can make an assumption with the non-null assertion operator (!) below
              message:
                this.filterServers(response, status)!.length > 0
                  ? `Servers were filtered by ${
                      status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'
                    } status`
                  : `No servers of ${status} was found`,
              data: { servers: this.filterServers(response, status) },
            }
      );
      subscriber.complete();
    }).pipe(tap(console.log), catchError(this.handleError));

  delete$ = (serverId: number) =>
    <Observable<CustomResponse>>(
      this.http
        .delete<CustomResponse>(`${this.apiUrl}/server/delete/${serverId}`)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  private filterServers(response: CustomResponse, filterStatus: Status) {
    return response.data.servers?.filter(
      (server) => server.status === filterStatus
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(() => {
      `An error occured - Error code: ${error.status}`;
    });
  }
}
