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
  maxViews: number = 0;

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
      this.user.optimalTimeToPost = data.optimalTimeToPost;
      this.user.monthlyReach = data.monthlyReach;

      //Max nézettség megtalálása
      for (var val of this.user.properties) {
        if (val.numberOfClicks > this.maxViews) {
          this.maxViews = val.numberOfClicks;
        }
      }

      this.user.properties.sort((n1, n2) => {
        if (n1.numberOfClicks < n2.numberOfClicks) {
          return 1;
        }

        if (n1.numberOfClicks > n2.numberOfClicks) {
          return -1;
        }

        return 0;
      });
      console.log(this.maxViews);
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

  changePassword(oldPassword: string, newPassword: string) {
    console.log(`Password change: ${oldPassword} -> ${newPassword}`);
    this.userService.changePassword(oldPassword, newPassword);
    this.logout();
  }

  changeUsername(newUsername: string, password: string) {
    console.log(`Username change: ${newUsername} -> ${password}`);
    this.userService.changeUsername(newUsername, password);
    this.logout();
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
