import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateComponent } from './components/create/create.component';
import { DetailComponent } from './components/detail/detail.component';
import { ListComponent } from './components/list/list.component';
import { UpdateComponent } from './components/update/update.component';


const routes: Routes = [
  { title: 'Rentals', path: '', component: ListComponent },
  { title: 'Rentals - detail', path: 'detail/:id', component: DetailComponent },
  { title: 'Rentals - update', path: 'update/:id', component: UpdateComponent },
  { title: 'Rentals - create', path: 'create', component: CreateComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RentalRoutingModule { }
