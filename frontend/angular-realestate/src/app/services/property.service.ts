import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Property } from '../common/property';
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private baseUrl = 'http://localhost:8080/api/properties'

  constructor(private httpClient: HttpClient) { }

  getPropertyList():Observable<Property[]>{
    return this.httpClient.get<GetResponse>(this.baseUrl).pipe(
      map(response => response._embedded.properties)
    )
  }
}

interface GetResponse {
  _embedded: {
    properties: Property[];
  }
}