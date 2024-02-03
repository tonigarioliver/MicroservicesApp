import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceModelApiService } from 'src/app/core/services/device-model-api.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateDeviceModelComponent } from 'src/app/core/pages/maintenance/components/device-model/components/create-device-model/create-device-model.component';

@Component({
  selector: 'app-device-model',
  templateUrl: './device-model.component.html',
  styleUrls: ['./device-model.component.css'],
})
export class DeviceModelComponent implements OnInit {
  displayedColumns: string[] = ['name', 'code'];
  dataSource = new MatTableDataSource<DeviceModel>(/* tu array de datos aquí */);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort; // Añade el signo de exclamación aquí

  constructor(private deviceModelApiService: DeviceModelApiService,private createDeviceModelDialog:MatDialog) {}

  ngOnInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.deviceModelApiService.getDeviceModels().subscribe(
      (deviceModels: DeviceModel[]) => {
        this.dataSource.data = deviceModels;
      },
      (error) => {
        console.error('Error fetching device models:', error);
      }
    );

    // Llena this.dataSource.data con tus datos
  }

  addDeviceModelDialog(): void {
    const dialogRef = this.createDeviceModelDialog.open(CreateDeviceModelComponent, {
      width: '400px',  // Configura el ancho del diálogo según tus necesidades
      // Ottras opciones de configuración si es necesario
    });
  
    dialogRef.afterClosed().subscribe(result => {
      // Puedes realizar acciones después de que se cierra el diálogo si es necesario
      console.log('Diálogo cerrado', result);
    });
  }
  
}
