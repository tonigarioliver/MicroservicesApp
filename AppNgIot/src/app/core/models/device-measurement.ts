import { Device } from "src/app/core/models/device";
import { MeasurementType } from "src/app/core/models/measurement-type";

export interface DeviceMeasurement {
  deviceMeasurementId: number;
  device: Device;
  topic: string;
  name: string;
  unit: string;
  measurementType: MeasurementType;
}
