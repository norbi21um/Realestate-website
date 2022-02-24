import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router) { }

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      login: this.formBuilder.group({
        email: new FormControl('', 
                              [Validators.required, 
                               Validators.minLength(2)]),

        password:  new FormControl('', 
                              [Validators.required])
      })
    });
  }

  get email(){
    return this.loginFormGroup.get('login.email');
  }

  get password(){
    return this.loginFormGroup.get('login.password');
  }

  onSubmit(){
    
  }

}
