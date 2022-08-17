import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RentalRoutingModule } from './rental-routing.module';
import { ListComponent } from './components/list/list.component';
import { UpdateComponent } from './components/update/update.component';
import { CreateComponent } from './components/create/create.component';
import { DetailComponent } from './components/detail/detail.component';

@NgModule({
  declarations: [
    ListComponent,
    UpdateComponent,
    CreateComponent,
    DetailComponent
  ],
  imports: [
    CommonModule,
    RentalRoutingModule
  ]
})
export class RentalsModule { }
