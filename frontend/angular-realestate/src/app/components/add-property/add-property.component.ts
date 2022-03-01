import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Property } from 'src/app/common/property';
import { PropertyItem } from 'src/app/common/property-item';
import { User } from 'src/app/common/user';
import { AddNewPropertyService } from 'src/app/services/add-new-property.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-add-property',
  templateUrl: './add-property.component.html',
  styleUrls: ['./add-property.component.css']
})
export class AddPropertyComponent implements OnInit {

  addNewFormGroup: FormGroup;


  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private addPropertyService: AddNewPropertyService,
              private http:HttpClient,
              private tokenStorageService: TokenStorageService) { }

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
                               [])
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

  //Image-nek nincs getterje, mert nem kell validálni.
  //Ha nem töltik ki, akkor a default.jpg kép fog oda kerülni

  onSubmit(){
    if (this.addNewFormGroup.invalid) {
      this.addNewFormGroup.markAllAsTouched();
      return;
    }

    let propertyItem:PropertyItem = new PropertyItem;
    
    propertyItem.address = this.addNewFormGroup.get('property.address').value;
    propertyItem.price = this.addNewFormGroup.get('property.price').value;
    propertyItem.area = this.addNewFormGroup.get('property.area').value;
    propertyItem.description = this.addNewFormGroup.get('property.description').value;

    //Alapértéket ad a kének ha nem adnak kép URL-t
    if(this.addNewFormGroup.get('property.imageUrl').value == ""){
      propertyItem.imageUrl = "default.jpg";
    } else{
      propertyItem.imageUrl = this.addNewFormGroup.get('property.imageUrl').value;
    }

    //User létrehozása és az ID kinyerése, amit felhasználok a hírdetés feladáshoz
    let user:User = JSON.parse(sessionStorage.getItem("auth-user"));
    propertyItem.userId = user.id;

    
    this.addPropertyService.createProperty(propertyItem).subscribe({
      next: response => {
        alert(`New property was created`);
        this.resetFields();
      },
      error: err => {
        alert(`There was an error: ${err.message}`);
      }
    });

  }

  resetFields() {
    
    this.addNewFormGroup.reset();

    this.router.navigateByUrl("/properties");
  }

}

