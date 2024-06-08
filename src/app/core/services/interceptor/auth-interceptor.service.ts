import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { RoutingService } from 'src/app/core/services/routing.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private readonly routingService: RoutingService
  ) {
  }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('jwtToken') || ''; // Usa un string vacío si el token es null
    const userName = localStorage.getItem('UserName') || '';
    const req1 = request.clone({
      headers: request.headers
        .set('Authorization', `Bearer ${token}`)
        .set('UserName', userName)
    })
    console.log("send token")
    return next.handle(req1).pipe(catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        this.routingService.navigateToLogin()
      }
      return throwError(error)
    }))
  }

}

