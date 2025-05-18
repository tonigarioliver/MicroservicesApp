import { DOCUMENT, Location, LocationStrategy } from '@angular/common'
import { Inject, Injectable } from '@angular/core'
import { Router } from '@angular/router'

@Injectable({
  providedIn: 'root',
})
export class RoutingService {
  constructor(
    protected readonly router: Router,
  ) {
  }

  navigateToLogin(): void {
    this.router.navigate(['/login'])
  }

  navigateToRegister(): void {
    this.router.navigate(['/register'])
  }

}
