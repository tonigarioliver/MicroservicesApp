
export interface DeviceRequest {
  deviceId: number | null;
  manufactureCode: string;
  deviceModelId: number;
  manufactureDate: Date;
  price: number | null;
}
