import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ErrorComponent } from './components/error/error.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

const materialModule = [
  MatButtonModule,
  MatIconModule,
  MatToolbarModule,
]

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ...materialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
