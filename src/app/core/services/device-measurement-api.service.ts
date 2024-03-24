import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';

@Injectable({
  providedIn: 'root'
})
export class DeviceMeasurementApiService {

  private baseUrl = 'http://localhost:8001/api/v1/device-measurement'; // Reemplaza con la URL de tu API

  constructor(private http: HttpClient) { }

  // Obtener medidas de dispositivos
  getDeviceMeasurements(): Observable<DeviceMeasurement[]> {
    return this.http.get<{ deviceMeasurements: DeviceMeasurement[] }>(this.baseUrl)
      .pipe(
        map(response => response.deviceMeasurements)
      );
  }

  // Crear una nueva medición de dispositivo
  createDeviceMeasurement(deviceMeasurement: DeviceMeasurement): Observable<DeviceMeasurement> {
    return this.http.post<{ deviceMeasurement: DeviceMeasurement }>(this.baseUrl, deviceMeasurement)
      .pipe(
        map(response => response.deviceMeasurement)
      );
  }

  // Actualizar una medición de dispositivo existente
  updateDeviceMeasurement(deviceMeasurement: DeviceMeasurement): Observable<DeviceMeasurement> {
    const url = `${this.baseUrl}/${deviceMeasurement.deviceMeasurementId}`;
    return this.http.put<{ deviceMeasurement: DeviceMeasurement }>(url, deviceMeasurement)
      .pipe(
        map(response => response.deviceMeasurement)
      );
  }

  // Eliminar una medición de dispositivo
  deleteDeviceMeasurement(deviceMeasurementId: number): Observable<void> {
    const url = `${this.baseUrl}/${deviceMeasurementId}`;
    return this.http.delete<void>(url);
  }

}
