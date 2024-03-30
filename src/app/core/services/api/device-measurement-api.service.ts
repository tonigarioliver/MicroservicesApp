import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceMeasurementRequest } from 'src/app/core/models/device-measurement-request';
import { DeviceMeasurementDetails } from 'src/app/core/models/device-measurement-details';

@Injectable({
  providedIn: 'root'
})
export class DeviceMeasurementApiService {

  private baseUrl = 'http://localhost:8001/api/v1/device-measurements'; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  // Obtener medidas de dispositivos
  getDeviceMeasurements(): Observable<DeviceMeasurement[]> {
    return this.http.get<{ deviceMeasurements: DeviceMeasurement[] }>(this.baseUrl)
      .pipe(
        map(response => response.deviceMeasurements)
      );
  }

  // Crear una nueva medición de dispositivo
  createDeviceMeasurement(request: DeviceMeasurementRequest): Observable<DeviceMeasurement> {
    return this.http.post<{ deviceMeasurement: DeviceMeasurement }>(this.baseUrl, request)
      .pipe(
        map(response => response.deviceMeasurement)
      );
  }

  // Actualizar una medición de dispositivo existente
  updateDeviceMeasurement(request: DeviceMeasurementRequest): Observable<DeviceMeasurement> {
    const url = `${this.baseUrl}/${request.deviceMeasurementId}`;
    return this.http.put<{ deviceMeasurement: DeviceMeasurement }>(url, request)
      .pipe(
        map(response => response.deviceMeasurement)
      );
  }

  getDeviceMeasurementDetails(deviceMeasurementId: number): Observable<DeviceMeasurementDetails> {
    const url = `${this.baseUrl}/${deviceMeasurementId}/details`;
    return this.http.get<{ deviceMeasurementDetails: DeviceMeasurementDetails }>(url)
      .pipe(
        map(response => response.deviceMeasurementDetails)
      );
  }

  // Eliminar una medición de dispositivo
  deleteDeviceMeasurement(deviceMeasurementId: number): Observable<void> {
    const url = `${this.baseUrl}/${deviceMeasurementId}`;
    return this.http.delete<void>(url);
  }

}
