import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Property } from '../common/property';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/user';

  constructor(private httpClient: HttpClient) {}

  /* sima user lista nélkü
  getUser(theUserId: number): Observable<User> {
    // URL építés a user id alapján
    const userUrl = `${this.baseUrl}?id=${theUserId}`;

    return this.httpClient.get<User>(userUrl);
  }*/

  getUserTest(theUserId: number): Observable<GetResponseUser> {
    // URL építés a user id alapján
    const userUrl = `${this.baseUrl}?id=${theUserId}`;

    return this.httpClient.get<GetResponseUser>(userUrl);
  }

  deleteUserById(id: number) {
    const deleteUserUrl = `http://localhost:8080/api/deleteUser?id=${id}`;
    this.httpClient.delete(deleteUserUrl).subscribe({
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

///EEEEEZZZZZ NAGYON RONDA, ITT KI KELL VALAMIT TALÁLNI!!!!
interface GetResponseUser {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  phoneNumber: string;
  proterties: Property[];
}
