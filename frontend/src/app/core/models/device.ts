import { DeviceModel } from "src/app/core/models/device-model"

export interface Device {
  deviceId: number;
  deviceModel: DeviceModel;
  manufactureDate: Date;
  price: number;
  manufactureCode: string;
}
