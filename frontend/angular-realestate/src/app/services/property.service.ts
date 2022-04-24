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

  /**
   * Általános ingatlan listázás
   */
  getPropertyListPaginate(
    thePage: number,
    thePageSize: number,
    sortBy: string
  ): Observable<GetResponse> {
    const searchUrl = `${this.baseUrl}?size=${thePageSize}&page=${thePage}&sortBy=${sortBy}`;
    return this.httpClient.get<GetResponse>(searchUrl);
  }

  //Pagination tesztelése
  //getPropertyList(sortBy: string): Observable<Property[]> {
  //const searchUrl = `${this.baseUrl}?sortBy=${sortBy}`;
  //return this.getProperties(searchUrl);
  //}

  /**
   * Keresés
   */
  searchProperties(
    thePage: number,
    thePageSize: number,
    theKeyword: string,
    theDistrict: string,
    sortBy: string
  ): Observable<GetResponse> {
    const searchUrl = `${this.baseUrl}/searchByKeyword?size=${thePageSize}&page=${thePage}&district=${theDistrict}&address=${theKeyword}&sortBy=${sortBy}`;
    return this.httpClient.get<GetResponse>(searchUrl);
  }

  /**
   * Ingatlan ajánlások listázása
   */
  getRecommendedProperties(theDistrict: string): Observable<Property[]> {
    const searchUrl = `${this.baseUrl}/recommendation?district=${theDistrict}`;

    return this.getProperties(searchUrl);
  }

  /**
   * Általános ingatlan listázás a megadott URL alapján
   */
  private getProperties(url): Observable<Property[]> {
    return this.httpClient
      .get<GetResponseWithoutPaginationResponse>(url)
      .pipe(map((response) => response.properties));
  }

  getProperty(thePopertyId: number): Observable<Property> {
    // URL építés a property id alapján
    const propertyUrl = `${this.baseUrl}/${thePopertyId}`;

    return this.httpClient.get<Property>(propertyUrl);
  }

  deletePropertyById(id: number) {
    const propertyUrl = `${this.baseUrl}/delete?id=${id}`;
    this.httpClient.delete(propertyUrl).subscribe({
      next: (data) => {
        //this.status = 'Delete successful';
      },
      error: (error) => {
        //this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }
}

interface GetResponseWithoutPaginationResponse {
  properties: Property[];
}

interface GetResponse {
  content: Property[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
