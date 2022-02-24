import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router) { }

  ngOnInit(): void {
    this.signupFormGroup = this.formBuilder.group({
      signup: this.formBuilder.group({
        firstName: new FormControl('', 
                              [Validators.required]),
        lastName: new FormControl('', 
                              [Validators.required]),
        email: new FormControl('', 
                              [Validators.required]),
        password:  new FormControl('', 
                              [Validators.required]),
        phoneNumber: new FormControl('', 
                              [Validators.required]),
      })
    });
  }
  get firstName(){
    return this.signupFormGroup.get('signup.firstName');
  }

  get lastName(){
    return this.signupFormGroup.get('signup.lastName');
  }

  get email(){
    return this.signupFormGroup.get('signup.email');
  }

  get password(){
    return this.signupFormGroup.get('signup.password');
  }

  get phoneNumber(){
    return this.signupFormGroup.get('signup.phoneNumber');
  }

  onSubmit(){
    
  }

}
