import {inject, Injectable} from '@angular/core';
import {MeasurementForCreationDto} from '../domain/measurement-for-creation-dto';
import {HttpClient} from '@angular/common/http';
import {MeasurementForGetDto} from '../domain/measurement-for-get-dto';
import {EnvService} from './env-service';

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {
  private http = inject(HttpClient);
  private envService = inject(EnvService);

  public createMeasurements(measurements: MeasurementForCreationDto[]) {
    console.log(this.envService.httpCollectorServiceBaseUrl);
    return this.http.post(`${this.envService.httpCollectorServiceBaseUrl}/measurements`, measurements);
  }

  public getAllSensors() {
    console.log(this.envService.measurementServiceBaseUrl);
    return this.http.get<[string]>(`${this.envService.measurementServiceBaseUrl}/measurements/sensors`);
  }

  public getMeasurementsForSensorId(sensorId: string) {
    console.log(this.envService.measurementServiceBaseUrl);
    return this.http.get<[MeasurementForGetDto]>(`${this.envService.measurementServiceBaseUrl}/measurements/${sensorId}`)
  }
}
