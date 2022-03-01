import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PropertyListComponent } from './components/property-list/property-list.component';
import { HttpClientModule } from '@angular/common/http';
import { PropertyService } from './services/property.service';
import { Routes, RouterModule } from '@angular/router';
import { PropertyDetailsComponent } from './components/property-details/property-details.component';
import { AddPropertyComponent } from './components/add-property/add-property.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { FormsModule } from '@angular/forms';
import { ProfileComponent } from './components/profile/profile.component';
import {
  AuthInterceptor,
  authInterceptorProviders,
} from './helpers/auth.interceptor';
import { AuthGuard } from './helpers/auth.guard';

const routes: Routes = [
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'signup', component: SignupComponent },
  { path: 'signin', component: LoginComponent },
  { path: 'addnew', component: AddPropertyComponent, canActivate: [AuthGuard] },
  { path: 'properties/:id', component: PropertyDetailsComponent },
  { path: 'properties', component: PropertyListComponent },
  { path: '', redirectTo: '/properties', pathMatch: 'full' },
  { path: '**', redirectTo: '/properties', pathMatch: 'full' },
];

@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent,
    PropertyDetailsComponent,
    AddPropertyComponent,
    LoginComponent,
    SignupComponent,
    ProfileComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [authInterceptorProviders, AuthGuard],
  bootstrap: [AppComponent],
})
export class AppModule {}
