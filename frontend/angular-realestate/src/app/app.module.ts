import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PropertyListComponent } from './components/property-list/property-list.component';
import { HttpClientModule } from '@angular/common/http'
import { PropertyService } from './services/property.service';

@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent
  ],
  imports: [
    BrowserModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [PropertyService],
  bootstrap: [AppComponent]
})
export class AppModule { }
