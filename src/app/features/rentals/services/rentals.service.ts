import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rental } from 'src/app/features/rentals/interfaces/rental.interface';
 

@Injectable({
  providedIn: 'root'
})
export class RentalsService {

  private pathService = 'api/rentals';

  constructor(private httpClient: HttpClient) { }

  public detail(id: string): Observable<Rental> {
    return this.httpClient.get<Rental>(`${this.pathService}/${id}`);
  }
}
