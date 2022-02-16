import { Component, OnInit } from '@angular/core';
import { Property } from 'src/app/common/property';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {

  properties: Property[];

  constructor(private propertyService: PropertyService) { }

  ngOnInit(): void {
    this.listProperties();
  }
  listProperties() {
    this.propertyService.getPropertyList().subscribe(
      data => {
        this.properties = data;
      }
    )
  }
  

}
