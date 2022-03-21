import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Property } from 'src/app/common/property';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css'],
})
export class PropertyListComponent implements OnInit {
  // districtId:number;

  searchMode: boolean;

  districtMode: boolean;

  properties: Property[];

  constructor(
    private propertyService: PropertyService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.listProperties();
  }
  listProperties() {
    //Megnézem, hogy van-e keyword és ha van akkor search módban vagyok
    if (this.route.snapshot.paramMap.has('keyword')) {
      console.log('porperty-list van keyword');
      this.searchMode = true;
    } else if (this.route.snapshot.paramMap.has('district')) {
      console.log('porperty-list van district');
      this.districtMode = true;
    }

    if (this.searchMode) {
      this.handleSearchProperties();
    } else if (this.districtMode) {
      this.handleOnlyDistrictSearchProperties();
    } else {
      //Ha nincs keresési módban ki listázza az összes ingatlant
      this.handleListProperties();
    }
  }

  handleSearchProperties() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword');
    const theDistrict: string = this.route.snapshot.paramMap.get('district');
    this.districtMode = false;
    this.searchMode = false;

    this.propertyService
      .searchProperties(theKeyword, theDistrict)
      .subscribe((data) => {
        this.properties = data;
      });
  }

  handleOnlyDistrictSearchProperties() {
    const theDistrict: string = this.route.snapshot.paramMap.get('district');
    this.districtMode = false;
    this.searchMode = false;

    this.propertyService.searchProperties('', theDistrict).subscribe((data) => {
      this.properties = data;
    });
  }

  handleListProperties() {
    this.propertyService.getPropertyList().subscribe((data) => {
      this.properties = data;
    });
  }
}
