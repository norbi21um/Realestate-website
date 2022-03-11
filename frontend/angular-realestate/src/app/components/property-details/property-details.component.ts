import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Property } from 'src/app/common/property';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css'],
})
export class PropertyDetailsComponent implements OnInit {
  property: Property = new Property();

  ///Csak ideiglenes tesztelésnek, majd a későbbiekben pontosabb oda tartozó Property-k kerülnek
  properties: Property[] = [];

  constructor(
    private propertyService: PropertyService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    //Betölti az aktuális ingatlant
    this.route.paramMap.subscribe(() => {
      this.handlePropertyDetails();
    });
  }

  handleRelatedProperties(district: string) {
    this.propertyService
      .getRecommendedProperties(this.property.district)
      .subscribe((data) => {
        this.properties = data;
      });
  }

  handlePropertyDetails() {
    // id string-et átkonvertálja "+" jel segítségével számmá
    const thePopertyId: number = +this.route.snapshot.paramMap.get('id');

    this.propertyService.getProperty(thePopertyId).subscribe((data) => {
      this.property = data;
      //Betölti az ajánlott ingatlanokat
      this.handleRelatedProperties(this.property.district);
    });
  }
}
