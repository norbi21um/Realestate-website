import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Property } from 'src/app/common/property';
import { User } from 'src/app/common/user';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  user: User = new User();
  properties: Property[] = [];

  constructor(
    private token: TokenStorageService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      //this.getUserData();
      this.getUserData();
    });
  }

  /* Ez a lista nélküli user lekérdezést
  getUserData() {
    const user = this.token.getUser();
    this.userService.getUser(user.id).subscribe((data) => {
      this.user = data;
    });
  }*/

  getUserData() {
    const user = this.token.getUser();
    this.userService.getUserTest(user.id).subscribe((data) => {
      this.user.firstName = data.firstName;
      this.user.lastName = data.lastName;
      this.user.email = data.email;
      this.user.phoneNumber = data.phoneNumber;
      this.user.username = data.username;
      this.user.properties = data.proterties;
    });
  }
}
