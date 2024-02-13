
export interface DeviceRequest {
  deviceId: number | null;
  manufactureCode: string;
  deviceModelId: number;
  manufactureDate: string; // Aquí asumo que la fecha se recibe como una cadena en formato ISO 8601
  price: number | null;
}
