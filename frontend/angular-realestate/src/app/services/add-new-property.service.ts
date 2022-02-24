import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Property } from '../common/property';
import { PropertyItem } from '../common/property-item';

@Injectable({
  providedIn: 'root'
})
export class AddNewPropertyService {

  createPropertyUrl: string = "http://localhost:8080/properties/createProperty";

  constructor(private httpClient:HttpClient) { }

  createProperty(propertyItem: PropertyItem){
    return this.httpClient.post<PropertyItem>(this.createPropertyUrl, propertyItem);
  }

}
