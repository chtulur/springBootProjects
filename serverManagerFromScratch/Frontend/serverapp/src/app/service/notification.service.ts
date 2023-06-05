import { Injectable } from '@angular/core';
import { NotifierService } from 'angular-notifier';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  constructor(private notifier: NotifierService) {}

  onDefault(message: string): void {
    this.notifier.notify(Type.DEFAULT, message);
  }

  onSuccess(message: string): void {
    this.notifier.notify(Type.SUCCESS, message);
  }

  onError(message: string): void {
    this.notifier.notify(Type.ERROR, message);
  }

  onWarning(message: string): void {
    this.notifier.notify(Type.WARNING, message);
  }

  onInfo(message: string): void {
    this.notifier.notify(Type.INFO, message);
  }
}

enum Type {
  DEFAULT = 'default',
  INFO = 'info',
  SUCCESS = 'success',
  WARNING = 'warning',
  ERROR = 'error',
}
