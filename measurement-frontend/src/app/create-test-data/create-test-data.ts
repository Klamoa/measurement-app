import {Component, inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MeasurementForCreationDto} from '../domain/measurement-for-creation-dto';
import {MeasurementService} from '../service/measurement-service';

@Component({
  selector: 'app-test-data',
  imports: [
    FormsModule
  ],
  templateUrl: './create-test-data.html'
})
export class CreateTestData implements OnInit {
  public measurements: MeasurementForCreationDto[] = [];

  private measurementService = inject(MeasurementService);

  ngOnInit(): void {
    this.measurements.push(new MeasurementForCreationDto());
  }

  addMeasurement() {
    this.measurements.push(new MeasurementForCreationDto());
  }

  removeMeasurement(index: number) {
    this.measurements.splice(index, 1);
  }

  public createMeasurement(): void {
    this.measurements.forEach(measurement => measurement.value = Math.random() * 100.0);
    console.log(this.measurements);

    this.measurementService.createMeasurements(this.measurements)
      .subscribe({
        next: value => console.log("Sent data to backend successfully!"),
        error: err => alert(err.message)
      });
  }
}
