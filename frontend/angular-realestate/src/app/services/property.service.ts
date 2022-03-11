import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Property } from '../common/property';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class PropertyService {
  private baseUrl = 'http://localhost:8080/api/properties';

  constructor(private httpClient: HttpClient) {}

  getPropertyList(): Observable<Property[]> {
    return this.getProperties(this.baseUrl);
  }

  /**
   * Keresés
   */
  searchProperties(
    theKeyword: string,
    theDistrict: string
  ): Observable<Property[]> {
    const searchUrl = `${this.baseUrl}/searchByKeyword?district=${theDistrict}&address=${theKeyword}`;

    return this.getProperties(searchUrl);
  }

  getRecommendedProperties(theDistrict: string): Observable<Property[]> {
    const searchUrl = `${this.baseUrl}/recommendation?district=${theDistrict}`;

    return this.getProperties(searchUrl);
  }

  /**
   * Általános ingatlan listázás a megadott URL alapján
   */
  private getProperties(url): Observable<Property[]> {
    return this.httpClient
      .get<GetResponse>(url)
      .pipe(map((response) => response.properties));
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
