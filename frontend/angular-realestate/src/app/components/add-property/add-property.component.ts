import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-property',
  templateUrl: './add-property.component.html',
  styleUrls: ['./add-property.component.css']
})
export class AddPropertyComponent implements OnInit {

  addNewFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router) { }

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

  onSubmit(){
    //todo
  }

}

