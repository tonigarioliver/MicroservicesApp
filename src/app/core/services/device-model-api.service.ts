import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { DeviceModel } from 'src/app/core/models/device-model';

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

  // Add more methods for other HTTP methods (PUT, DELETE, etc.) if needed
}
