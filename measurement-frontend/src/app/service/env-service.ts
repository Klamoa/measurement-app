import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EnvService {
  public measurementServiceBaseUrl = (window as any).__env?.measurementServiceBaseUrl || '';
  public httpCollectorServiceBaseUrl = (window as any).__env?.httpCollectorServiceBaseUrl || '';
}
