import {Component, inject, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MeasurementService} from '../service/measurement-service';

@Component({
  selector: 'app-measurements',
  imports: [
    RouterLink
  ],
  templateUrl: './measurements.html'
})
export class Measurements implements OnInit {
  public sensorIds: string[] = [];

  private measurementService = inject(MeasurementService);

  ngOnInit(): void {
    this.measurementService.getAllSensors()
      .subscribe({
        next: sensorIds => this.sensorIds = sensorIds,
        error: err => alert(err.message)
      });
  }
}
