import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MeasurementType } from 'src/app/core/models/measurement-type';

@Injectable({
  providedIn: 'root'
})
export class MeasurementTypeApiService {
  private baseUrl = 'http://localhost:8001/api/v1/device-measurement-type'

  constructor(private http: HttpClient) { }

  // Obtener tipos de medidas
  getMeasurementTypes(): Observable<MeasurementType[]> {
    return this.http.get<{ measurementTypes: MeasurementType[] }>(this.baseUrl)
      .pipe(
        map(response => response.measurementTypes)
      );
  }

  // Crear un nuevo tipo de medida
  createMeasurementType(measurementType: MeasurementType): Observable<MeasurementType> {
    const url = `${this.baseUrl}/measurement-types`;
    return this.http.post<{ measurementType: MeasurementType }>(url, measurementType)
      .pipe(
        map(response => response.measurementType)
      );
  }

  // Actualizar un tipo de medida existente
  updateMeasurementType(measurementType: MeasurementType): Observable<MeasurementType> {
    const url = `${this.baseUrl}/${measurementType.measurementTypeId}`;
    return this.http.put<{ measurementType: MeasurementType }>(url, measurementType)
      .pipe(
        map(response => response.measurementType)
      );
  }

  // Eliminar un tipo de medida
  deleteMeasurementType(measurementTypeId: number): Observable<void> {
    const url = `${this.baseUrl}/${measurementTypeId}`;
    return this.http.delete<void>(url);
  }

  // Otros métodos para obtener, crear, actualizar o eliminar recursos pueden seguir el mismo patrón
}
