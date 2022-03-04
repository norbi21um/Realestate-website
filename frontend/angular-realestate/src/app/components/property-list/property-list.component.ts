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
    this.searchMode = this.route.snapshot.paramMap.has('keyword');

    if (this.searchMode) {
      this.handleSearchProperties();
    } else {
      this.handleListProperties();
    }
  }

  handleSearchProperties() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword');

    this.propertyService.searchProperties(theKeyword).subscribe((data) => {
      this.properties = data;
    });
  }

  handleListProperties() {
    this.propertyService.getPropertyList().subscribe((data) => {
      this.properties = data;
    });
  }
}
