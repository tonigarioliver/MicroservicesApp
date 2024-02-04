import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceModelCreateRequest } from 'src/app/core/models/device-model-create-request';

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

  postCreateDeviceModelRequest(deviceModelCreateRequest: DeviceModelCreateRequest): Observable<DeviceModel> {
    return this.http.post<{ deviceModel: DeviceModel }>(this.serviceUrl, deviceModelCreateRequest)
      .pipe(
        map(response => response.deviceModel),
      )
  }
  deleteDeviceModel(deviceModelId: number): Observable<void> {
    const url = `${this.serviceUrl}/${deviceModelId}`
    return this.http.delete<void>(url)
  }

}
