import { Component, Input, OnInit } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CurrencyPipe } from '../layout/pipes/CurrentcyPipes.pipe';
import { NgFor, NgIf } from '@angular/common';
import { CartService } from '../../service/cart.service';
import { productItemApi } from '../types/productType';
import { productService } from '../../service/productService';
import { OrderType } from '../types/orderType';
import { OrderService } from '../../service/orderService';
import { TokenService } from '../../service/token.service';


@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [FormsModule, CurrencyPipe, NgFor, ReactiveFormsModule, NgIf],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{

  orderForm: FormGroup;
  cartItems: {product: productItemApi, quantity: number}[] = [];
  order: OrderType = {
      id_user: 0,
      fullname: '',
      sdt: '',
      address: '',
      total: 0,
      cartItems: []
    }

    constructor(private cartService: CartService,
      private productSer: productService,
      private fb: FormBuilder,
      private orderService: OrderService,
      private tokenService: TokenService,
      private router: Router
    ){
      this.orderForm = this.fb.group({
        fullname: ['', Validators.required],
        sdt: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
        address: ['', [Validators.required, Validators.minLength(10)]],
        pay_method: ['COD']
      });
    }

  ngOnInit(): void {
    this.order.id_user = this.tokenService.getUserId() || 0;
    const items = this.cartService.getCart();
    const productIds = Array.from(items.keys());
    
    this.productSer.getProductsByIds(productIds).subscribe({
      next: (data) =>{
        this.cartItems = productIds.map((id) => {
          const product = data.find((item: productItemApi) => item.id === id);
          if (product) {
             product.url = `http://localhost:8080/api/product/images/${product.thumnail}`
             console.log(product);
          }
          return {
            product: product,
            quantity: items.get(id) || 0
          };
        });
      },
      error: (error) => {
        console.error('Error fetching products:', error);
      }
  });
  }
  removeCartItem(id: number) {
    this.cartService.removeFromCart(id);
    this.cartItems = this.cartItems.filter(item => item.product.id !== id);
  }

  get total(): any{
    let sum: any = this.cartItems.reduce((total, item) => {
      return total + item.product.price * item.quantity;
    }, 0);
    return sum;
  }
  handleOrder(){
    if(this.orderForm.valid && this.cartItems.length > 0){
      this.order = {
        ...this.order,
        ...this.orderForm.value,
      }
      this.order.total = this.total;
      this.order.cartItems = this.cartItems.map(item => {
        return {
          id_product: item.product.id,
          quantity: item.quantity
        }
      });
      this.orderService.createOrder(this.order).subscribe({
        next: (data) => {
          this.router.navigate(['/']);
          alert('Đặt hàng thành công!');
          this.cartService.clearCart();
        },
        error: (error: any) => {
          console.error('Error creating order:', error);
        }
      });
    }else{
      console.log('Form is invalid');
    }
  }
}