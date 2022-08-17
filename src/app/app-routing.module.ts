import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ErrorComponent } from './components/error/error.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  {
    path: 'rentals',
    loadChildren: () => import('./features/rentals/rentals.module').then(m => m.RentalsModule)
  },
  {
    path: '',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  { path: 'error', component: ErrorComponent },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
