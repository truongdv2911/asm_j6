import { NgIf } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm, } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../service/userService';
import { userLogin } from '../types/userLogin';
import { HttpHeaders } from '@angular/common/http';
import { LoginResponse } from '../types/LoginResponse';
import { TokenService } from '../../service/token.service';
import { UserResponse } from '../types/userResponse';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, NgIf, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @ViewChild('formLogin') formLogin!: NgForm;
  phone: string;
  password: string;
  isLogin: boolean;

  constructor(private userService: UserService,private router: Router, private tokenService: TokenService) {
    this.phone = '';
    this.password = '';
    this.isLogin = true;
  }
  login(){
    const userLogin: userLogin = {
      "sdt": this.phone,
      "password": this.password
    };
    this.userService.login(userLogin).subscribe({
      next: (response: LoginResponse) => {
        const {token} = response;
        if(this.isLogin){
          this.tokenService.setToken(token,this.isLogin);
          this.userService.detail(token).subscribe({
            next: (userResponse: any) => {
              const user: UserResponse = {
                ...userResponse,
                date_of_birth: new Date(userResponse.date_of_birth),
              };
              console.log(user);
              this.userService.saveUserToLocalStorage(user);
            },
            error: (error) => {
              alert(`error: ${error.error.message}`);
            }
          })
        }
        
        const {messsage} = response;
          this.router.navigate(['/']);
      },
      error: (error) => {
        alert(`error: ${error.error.message}`);
      }
    })
  }
}
