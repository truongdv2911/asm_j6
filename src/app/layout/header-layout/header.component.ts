import {Component, NgModule, OnInit} from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { productItemApi } from '../../types/productType';
import { productService } from '../../../service/productService';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../service/userService';
import { UserResponse } from '../../types/userResponse';
import { TokenService } from '../../../service/token.service';
import { CartService } from '../../../service/cart.service';

@Component({
    selector: 'header-layout',
    imports:[RouterLink, FormsModule, NgIf],
    templateUrl: './header.component.html',
    styleUrl: './header.component.css'
})

export class HeaderComponent implements OnInit{
  userResponse?: UserResponse | null
  isDropdownOpen = false;
  constructor(private productService: productService,
              private userService: UserService,
              private tokenService: TokenService,
              private cartService: CartService,
              private router: Router
  ){}
  ngOnInit(): void {
    this.userResponse = this.userService.getUserFromLocalStorage();
  }
  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
  logout() {
    this.tokenService.removeToken();
    this.userService.removeUserFromLocalStorage();
    this.cartService.clearCart();
    this.router.navigate(['/loginform']);
    alert('Logout clicked');
  }
}
