import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Property } from 'src/app/common/property';
import { PropertyItem } from 'src/app/common/property-item';
import { AddNewPropertyService } from 'src/app/services/add-new-property.service';

@Component({
  selector: 'app-add-property',
  templateUrl: './add-property.component.html',
  styleUrls: ['./add-property.component.css']
})
export class AddPropertyComponent implements OnInit {

  addNewFormGroup: FormGroup;

  propertyItem:PropertyItem = {
    address: 'AAAAAAA',
    price: 0,
    area: 0,
    imageUrl: 'AAAAA',
    description: 'AAAAAAAA',
    userId: 1
  };

  url = `http://localhost:8080/properties/createProperty`;


  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private addPropertyService: AddNewPropertyService,
              private http:HttpClient) { }

  ngOnInit(): void {
    this.addNewFormGroup = this.formBuilder.group({
      property: this.formBuilder.group({
        address: new FormControl('', 
                              [Validators.required, 
                               Validators.minLength(2)]),

        price:  new FormControl('', 
                              [Validators.required]),
                               
        area: new FormControl('',
                              [Validators.required]),
        description:  new FormControl('', 
                              [Validators.required]),
        imageUrl:  new FormControl('', 
                               [Validators.required])
      })
    });

  }

  get address() {
    return this.addNewFormGroup.get('property.address');
  }

  get price(){
    return this.addNewFormGroup.get('property.price');
  }

  get area(){
    return this.addNewFormGroup.get('property.area');
  }

  get description(){
    return this.addNewFormGroup.get('property.description');
  }

  onSubmit(){
    //todo

    let test:PropertyItem = new PropertyItem;
    
    test.address = this.addNewFormGroup.get('property.address').value;
    test.price = this.addNewFormGroup.get('property.price').value;
    test.area = this.addNewFormGroup.get('property.area').value;
    test.description = this.addNewFormGroup.get('property.description').value;
    test.imageUrl = this.addNewFormGroup.get('property.imageUrl').value;
    test.userId = 1;

    
    this.addPropertyService.createProperty(test).subscribe({
      next: response => {
        alert(`New property was created`);

      },
      error: err => {
        alert(`There was an error: ${err.message}`);
      }
    });
    /*
    this.http.post<PropertyItem>(this.url,test).subscribe(
      data => {
    })*/

  }

}

