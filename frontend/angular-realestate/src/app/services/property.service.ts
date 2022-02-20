import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Property } from '../common/property';
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private baseUrl = 'http://localhost:8080/properties'

  constructor(private httpClient: HttpClient) { }

  getPropertyList():Observable<Property[]>{
    return this.httpClient.get<GetResponse>(this.baseUrl).pipe(
      map(response => response.properties)
    )
  }

  getProperty(thePopertyId: number): Observable<Property> {
    // URL építés a property id alapján
    const propertyUrl = `${this.baseUrl}/${thePopertyId}`;

    return this.httpClient.get<Property>(propertyUrl);
  }
}

interface GetResponse {
  
    properties: Property[];
  
}