import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('jwtToken') || ''; // Usa un string vacío si el token es null
    const userName = localStorage.getItem('UserName') || '';
    const req1 = request.clone({
      headers: request.headers
        .set('Authorization', `Bearer ${token}`)
        .set('UserName', userName)
    })
    console.log("send token")
    return next.handle(req1);
  }

}

