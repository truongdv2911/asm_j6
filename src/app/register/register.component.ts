import { NgIf } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { UserService } from '../../service/userService';
import { Router, RouterLink } from '@angular/router';
import { userApi } from '../types/userType';

@Component({
  selector: 'app-register',
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  @ViewChild('formRegister') formRegister!: NgForm;
username: string;
password: string;
phone: string;
address: string;
dateBirth: Date;
  constructor(private userService: UserService, private router: Router) {
  this.username = '';
  this.password = '';
  this.phone = '';
  this.address = '';
  this.dateBirth = new Date();
  }
  checkAge(){
    if(this.dateBirth){
      const today = new Date();
      const birthDate = new Date(this.dateBirth);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      if(age <10){
        this.formRegister.controls['dateBirth'].setErrors({ 'invalidAge': true });
      }
      else{
        this.formRegister.controls['dateBirth'].setErrors(null);
      }
    }
  }
  register() {
    const userRegister: userApi = {
      "full_name":this.username,
      "sdt": this.phone,
      "address": this.address,
      "password": this.password,
      "date_of_birth": this.dateBirth,
     "facebook_id":0,
     "google_id":0,
      "id_role":2
    };
    this.userService.register(userRegister).subscribe({
      next: (response: any) => {
          console.log(response.data);
          this.router.navigate(['/loginform']);
      },
      error: (error) => {
        alert(`error: ${error.error.message}`);
      }
    })

  }
}
