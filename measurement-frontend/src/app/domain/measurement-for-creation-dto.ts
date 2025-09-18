export class MeasurementForCreationDto {
  public name: string = '';
  public date: string | null = null;
  public value: number = 0.0;
  public unit: string = '';
  public sensorId: string = '';
}
