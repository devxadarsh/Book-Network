import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { httpTokenInterceptor } from './services/interceptor/http-token.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    // provideHttpClient(),
    // {
    //   provide: HTTP_INTERCEPTORS,
    //   useClass: HttpTokenInterceptor,
    //   multi: true
    // }
    provideHttpClient(withInterceptors(
      [
        httpTokenInterceptor
      ]
    ))
  ],
};
