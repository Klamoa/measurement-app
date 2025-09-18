import { Routes } from '@angular/router';
import {CreateTestData} from './create-test-data/create-test-data';
import {Measurements} from './measurements/measurements';
import {MeasurementDetail} from './measurement-detail/measurement-detail';

export const routes: Routes = [
  {
    path: '',
    component: Measurements,
  },
  {
    path: 'details/:sensorId',
    component: MeasurementDetail
  },
  {
    path: 'create',
    component: CreateTestData,
  },
];
