import { Component, OnInit } from '@angular/core';
import { Rental } from 'src/app/interfaces/rental.interface';
import { RentalsService } from '../../services/rentals.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public rental: Rental | undefined;

  constructor(private rentalsService: RentalsService) { }

  ngOnInit(): void {
    this.rentalsService
      .detail('2')
      .subscribe((rental: Rental) => this.rental = rental);
  }

}
