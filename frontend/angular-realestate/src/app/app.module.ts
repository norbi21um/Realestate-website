import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PropertyListComponent } from './components/property-list/property-list.component';
import { HttpClientModule } from '@angular/common/http'
import { PropertyService } from './services/property.service';
import { Routes, RouterModule } from '@angular/router';
import { PropertyDetailsComponent } from './components/property-details/property-details.component';

const routes: Routes = [
  {path: 'properties/:id', component: PropertyDetailsComponent},
  {path: 'properties', component: PropertyListComponent},
  {path: '', redirectTo: '/properties', pathMatch: 'full'},
  {path: '**', redirectTo: '/properties', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent,
    PropertyDetailsComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [PropertyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
