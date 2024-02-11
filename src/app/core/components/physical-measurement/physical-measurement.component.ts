import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PhysicalMeasurementFormComponent } from 'src/app/core/components/physical-measurement-form/physical-measurement-form.component';
import { PhysicalMeasurement } from 'src/app/core/models/physical-measurement';
import { PhysicalMeasurementRequest } from 'src/app/core/models/physical-measurement-request';
import { PhysicalMeasurementApiService } from 'src/app/core/services/physical-measurement-api.service';

@Component({
  selector: 'app-physical-measurement',
  templateUrl: './physical-measurement.component.html',
  styleUrls: ['./physical-measurement.component.scss']
})
export class PhysicalMeasurementComponent {
  displayedColumns: string[] = ['name', 'unit', 'actions'];
  tableData = new MatTableDataSource<PhysicalMeasurement>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort; 

  constructor(private physicalMeasurementApiService : PhysicalMeasurementApiService, private physicalMeasurementDialog: MatDialog) { }

  ngOnInit(): void {
    this.tableData.paginator = this.paginator;
    this.tableData.sort = this.sort;
    this.fetchPhysicalMeasurement();
  }

  fetchPhysicalMeasurement(): void {
    this.physicalMeasurementApiService.getPhysicalMeasurements().subscribe(
      (physicalMeasurements: PhysicalMeasurement[]) => {
        this.tableData.data = physicalMeasurements;
      },
      (error) => {
        console.error('Error fetching device models:', error);
      }
    );
  }

  addPhysicalMeasurement(): void {
    const physicalMeasurementRequest: PhysicalMeasurementRequest = {
      physicalMeasurementId: null,
      name: '',
      unit: ''
    };
    const isEditMode = false
    const dialogRef = this.physicalMeasurementDialog.open(PhysicalMeasurementFormComponent, {
      width: '400px',
      data: { physicalMeasurementRequest, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchPhysicalMeasurement();
    });
}

editPhysicalMeasurement(physicalMeasurement: PhysicalMeasurement): void {
    const physicalMeasurementRequest: PhysicalMeasurementRequest = {
      physicalMeasurementId: physicalMeasurement.physicalMeasurementId,
      name: physicalMeasurement.name,
      unit: physicalMeasurement.unit
    };
    const isEditMode = true
    const dialogRef = this.physicalMeasurementDialog.open(PhysicalMeasurementFormComponent, {
      width: '400px',
      data: { physicalMeasurementRequest, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchPhysicalMeasurement();
    });
}

deletePhysicalMeasurement(physicalMeasurement: PhysicalMeasurement): void {
  this.physicalMeasurementApiService.deletePhysicalMeasurement(physicalMeasurement.physicalMeasurementId)
    .subscribe(
      () => {
        this.fetchPhysicalMeasurement();
      },
      (error) => {
        console.error('Error deleting device model:', error);
      }
    );
  }
}
