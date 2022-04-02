import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Property } from 'src/app/common/property';
import { User } from 'src/app/common/user';
import { PropertyService } from 'src/app/services/property.service';
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
    private route: ActivatedRoute,
    private propertyService: PropertyService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      //this.getUserData();
      this.getUserData();
    });
  }

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

  deleteProperty(id: number) {
    if (confirm('Are you sure you want to delete this property?')) {
      this.propertyService.deletePropertyById(id);
      //TODO: Ez nem frissíti az adatokat, ami gáz
      //this.getUserData();
      window.location.reload(); //Ez egy rossz, de ideiglenes megoldás
    }
  }

  deleteAccount() {
    if (confirm('Are you sure you want to delete your account?')) {
      this.userService.deleteUserById();
      this.logout();
    }
  }
  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
