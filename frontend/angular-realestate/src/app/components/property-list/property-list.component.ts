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

  //Pagination
  thePageNumber: number = 1;
  thePageSize: number = 10;
  theTotalElements: number = 0;

  searchMode: boolean;
  districtMode: boolean;
  properties: Property[] = [];

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
      //console.log('porperty-list van keyword');
      this.searchMode = true;
    } else if (this.route.snapshot.paramMap.has('district')) {
      //console.log('porperty-list van district');
      this.districtMode = true;
    }

    if (this.searchMode) {
      this.handleSearchProperties('');
    } else if (this.districtMode) {
      this.handleOnlyDistrictSearchProperties('');
    } else {
      //Ha nincs keresési módban ki listázza az összes ingatlant
      this.handleListProperties('');
    }
  }

  handleSearchProperties(sortBy: string) {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword');
    const theDistrict: string = this.route.snapshot.paramMap.get('district');
    this.districtMode = false;
    this.searchMode = false;

    this.propertyService
      .searchProperties(
        this.thePageNumber - 1,
        this.thePageSize,
        theKeyword,
        theDistrict,
        sortBy
      )
      .subscribe(this.processResult());
  }

  handleOnlyDistrictSearchProperties(sortBy: string) {
    const theDistrict: string = this.route.snapshot.paramMap.get('district');
    this.districtMode = false;
    this.searchMode = false;

    this.propertyService
      .searchProperties(
        this.thePageNumber - 1,
        this.thePageSize,
        '',
        theDistrict,
        sortBy
      )
      .subscribe(this.processResult());
  }

  handleListProperties(sortBy: string) {
    //Lehet újra kell majd settelni a page numebert 1-re ha nem működik a keresésnél
    this.propertyService
      .getPropertyListPaginate(this.thePageNumber - 1, this.thePageSize, sortBy)
      .subscribe(this.processResult());
  }

  processResult() {
    return (data) => {
      this.properties = data.content;
      this.thePageNumber = data.number + 1;
      this.thePageSize = data.size;
      this.theTotalElements = data.totalElements;
    };
  }

  /**
   * A rendezés beállító select input változáskor meghívja ezt a függvényt
   * ami a kilistázást meghívja a kiválasztott paraméterrel.
   * @param sortBy a rendezés módja
   */
  sortProperties(sortBy) {
    //Megnézem, hogy van-e keyword és ha van akkor search módban vagyok
    if (this.route.snapshot.paramMap.has('keyword')) {
      //console.log('porperty-list van keyword');
      this.searchMode = true;
    } else if (this.route.snapshot.paramMap.has('district')) {
      //console.log('porperty-list van district');
      this.districtMode = true;
    }

    if (this.searchMode) {
      //Ha kulcsszó szerint van szűrés
      console.log('searchMode');
      this.handleSearchProperties(sortBy);
    } else if (this.districtMode) {
      //Ha kerület szerint van szűrés
      console.log('districtMode');
      this.handleOnlyDistrictSearchProperties(sortBy);
    } else {
      //Ha nincs keresési módban
      console.log('simaMode');
      this.handleListProperties(sortBy);
    }
  }

  updatePageSize(pageSize: string) {
    let pageSizeNumnb = +pageSize;
    this.thePageSize = pageSizeNumnb;
    this.thePageNumber = 1;
    this.listProperties();
  }
}
