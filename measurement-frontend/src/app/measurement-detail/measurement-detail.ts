import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MeasurementForGetDto} from '../domain/measurement-for-get-dto';
import {MeasurementService} from '../service/measurement-service';
import {DatePipe, DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-measurement-detail',
  imports: [
    DecimalPipe
  ],
  templateUrl: './measurement-detail.html'
})
export class MeasurementDetail implements OnInit {
  public sensorId: string = '';
  public measurements: MeasurementForGetDto[] = [];
  public loading = true;

  private route = inject(ActivatedRoute);
  private measurementService = inject(MeasurementService);

  ngOnInit() {
    this.sensorId = this.route.snapshot.paramMap.get('sensorId') ?? '';

    if (this.sensorId) {
      this.measurementService.getMeasurementsForSensorId(this.sensorId)
        .subscribe({
          next: data => {
            this.measurements = data;
            this.loading = false;
          },
          error: err => {
            alert(err.message);
            this.loading = false;
          }
        });
    } else {
      this.loading = false;
    }
  }
}
