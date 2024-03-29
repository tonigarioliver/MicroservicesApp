import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent, } from './dashboard.component';
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
  ]
})
export class Dashboardodule { }
