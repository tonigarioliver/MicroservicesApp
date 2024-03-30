import { Injectable } from '@angular/core';
import { HttpClient,} from '@angular/common/http';
import { Observable, } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceModelRequest } from 'src/app/core/models/device-model-request';

@Injectable({
  providedIn: 'root'
})
export class DeviceModelApiService {
  private serviceUrl = 'http://localhost:8001/api/v1/device-model'; // Replace with your API URL

  constructor(private http: HttpClient) {}

  getDeviceModels(): Observable<DeviceModel[]> {
    return this.http.get<{ deviceModels: DeviceModel[] }>(this.serviceUrl)
      .pipe(map(response => response.deviceModels ))
  }

  createDeviceModelRequest(deviceModelRequest: DeviceModelRequest): Observable<DeviceModel> {
    return this.http.post<{ deviceModel: DeviceModel }>(this.serviceUrl, deviceModelRequest)
      .pipe(
        map(response => response.deviceModel),
      )
  }
  updateDeviceModelRequest(deviceModelRequest: DeviceModelRequest): Observable<DeviceModel> {
    const url = `${this.serviceUrl}/${deviceModelRequest.deviceModelId}`
    return this.http.put<{ deviceModel: DeviceModel }>(url, deviceModelRequest)
      .pipe(
        map(response => response.deviceModel),
      )
  }
  deleteDeviceModel(deviceModelId: number): Observable<void> {
    const url = `${this.serviceUrl}/${deviceModelId}`
    return this.http.delete<void>(url)
  }

}
