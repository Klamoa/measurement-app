import {MeasurementEntryForGetDto} from './measurement-entry-for-get-dto';

export class MeasurementForGetDto {
  public name: string = '';
  public unit: string = '';
  public entries: MeasurementEntryForGetDto[] = [];
}
