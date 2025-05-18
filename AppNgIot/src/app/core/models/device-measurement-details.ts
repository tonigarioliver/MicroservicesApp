import { Device } from "src/app/core/models/device";
import { DeviceMeasurementPayload } from "src/app/core/models/device-measurement-payload";
import { MeasurementType } from "src/app/core/models/measurement-type";

export interface DeviceMeasurementDetails {
  deviceMeasurementId: number;
  device: Device;
  topic: string;
  name: string;
  unit: string;
  measurementType: MeasurementType;
  measures: DeviceMeasurementPayload[]
}
