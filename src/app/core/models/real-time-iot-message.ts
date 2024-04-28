import { DeviceMeasurement } from "src/app/core/models/device-measurement";

export interface RealTimeWebSocketRequest {
  command: string;
  deviceMeasurement: DeviceMeasurement
}
