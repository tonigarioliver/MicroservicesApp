import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { PhysicalMeasurement } from 'src/app/core/models/physical-measurement';
import { PhysicalMeasurementRequest } from 'src/app/core/models/physical-measurement-request';

@Injectable({
  providedIn: 'root'
})
export class PhysicalMeasurementApiService {

  private serviceUrl = 'http://localhost:8080/iotDeviceService/api/v1/physical-measurements'; // Replace with your API URL

  constructor(private http: HttpClient) { }

  getPhysicalMeasurements(): Observable<PhysicalMeasurement[]> {
    return this.http.get<{ physicalMeasurements: PhysicalMeasurement[] }>(this.serviceUrl)
      .pipe(map(response => response.physicalMeasurements))
  }

  createPhysicalMeasurement(physicalMeasurementRequest: PhysicalMeasurementRequest): Observable<PhysicalMeasurement> {
    return this.http.post<{ physicalMeasurements: PhysicalMeasurement }>(this.serviceUrl, physicalMeasurementRequest)
      .pipe(
        map(response => response.physicalMeasurements),
      )
  }
  updatePhsyicalMeasurement(physicalMeasurementRequest: PhysicalMeasurementRequest): Observable<PhysicalMeasurement> {
    const url = `${this.serviceUrl}/${physicalMeasurementRequest.physicalMeasurementId}`
    return this.http.put<{ physicalMeasurements: PhysicalMeasurement }>(url, physicalMeasurementRequest)
      .pipe(
        map(response => response.physicalMeasurements),
      )
  }
  deletePhysicalMeasurement(physicalMeasurementId: number): Observable<void> {
    const url = `${this.serviceUrl}/${physicalMeasurementId}`
    return this.http.delete<void>(url)
  }
}
