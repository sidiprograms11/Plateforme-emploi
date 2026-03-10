import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { routes } from './app.routes';
import { JwtInterceptor } from './auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [

    // 🔀 Routing
    provideRouter(routes),

    // 🌐 HTTP Client avec support des interceptors DI
    provideHttpClient(
      withInterceptorsFromDi()
    ),

    // 🔐 JWT Interceptor
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }

  ]
};