import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { District } from 'src/app/common/district';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  districts: District[] = [];

  constructor(
    private router: Router,
    private propertyService: PropertyService
  ) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };
  }

  ngOnInit(): void {
    this.propertyService.getDistricts().subscribe((data) => {
      this.districts = data;
    });
  }

  doSearch(addressValue: string, districtValue: string) {
    if (addressValue == '') {
      this.router.navigateByUrl(`/search/${districtValue}`);
    } else {
      this.router.navigateByUrl(`/search/${addressValue}/${districtValue}`);
    }
  }
}
