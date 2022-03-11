import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/user';

  constructor(private httpClient: HttpClient) {}

  getUser(theUserId: number): Observable<User> {
    // URL építés a user id alapján
    const userUrl = `${this.baseUrl}?id=${theUserId}`;

    return this.httpClient.get<User>('http://localhost:8080/api/user?id=12');
  }
}
