import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  constructor(private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
  }

  ngOnInit(): void {}

  doSearch(addressValue: string, districtValue: string) {
    if (addressValue == '') {
      this.router.navigateByUrl(`/search/${districtValue}`);
    } else {
      this.router.navigateByUrl(`/search/${addressValue}/${districtValue}`);
    }
  }
}
