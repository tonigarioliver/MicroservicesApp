import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Device } from 'src/app/core/models/device';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceRequest } from 'src/app/core/models/device-request';

@Injectable({
  providedIn: 'root'
})
export class DeviceApiService {

  private serviceUrl = 'http://localhost:8001/api/v1/devices'; // Replace with your API URL

  constructor(private http: HttpClient) {}

  getDevices(): Observable<Device[]> {
    return this.http.get<{ deviceModels: Device[] }>(this.serviceUrl)
      .pipe(map(response => response.deviceModels ))
  }

  createDeviceRequest(deviceRequest: DeviceRequest): Observable<Device> {
    return this.http.post<{ device: Device }>(this.serviceUrl, deviceRequest)
      .pipe(
        map(response => response.device),
      )
  }
  updateDeviceRequest(deviceRequest: DeviceRequest): Observable<Device> {
    const url = `${this.serviceUrl}/${deviceRequest.deviceId}`
    return this.http.put<{ device: Device }>(url, deviceRequest)
      .pipe(
        map(response => response.device),
      )
  }
  deleteDevice(deviceId: number): Observable<void> {
    const url = `${this.serviceUrl}/${deviceId}`
    return this.http.delete<void>(url)
  }
}
