import { DeviceMeasurement } from "src/app/core/models/device-measurement";

export interface DeviceMeasurementPayload {
  deviceMeasurementId: number;
  deviceMeasurementPayloadId: number;
  deviceMeasurement: DeviceMeasurement;
  stringValue: string;
  numValue: number;
  booleanValue: boolean;
}
