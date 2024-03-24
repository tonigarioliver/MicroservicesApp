import { Device } from "src/app/core/models/device";
import { DeviceMeasurementPayload } from "src/app/core/models/device-measurement-payload";
import { MeasurementType } from "src/app/core/models/measurement-type";

export interface DeviceMeasurement {
  deviceMeasurementId: number;
  device: Device;
  name: string;
  unit: string;
  measurementType: MeasurementType;
  deviceMeasurementPayloads: DeviceMeasurementPayload[]
}
