import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { SidenavDashboardComponent } from 'src/app/core/components/sidenav-dashboard/sidenav-dashboard.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  @ViewChild(SidenavDashboardComponent) sidenavDashboard!: SidenavDashboardComponent;

  toggleMenu() {
    this.sidenavDashboard.toggleMenu();
  }
}
