import { Component, OnInit } from '@angular/core';
import { OrderType } from '../types/orderType';
import { productItemApi } from '../types/productType';
import { CartService } from '../../service/cart.service';
import { productService } from '../../service/productService';
import { OrderService } from '../../service/orderService';
import { NgFor } from '@angular/common';
import { OrderResponse } from '../types/orderResponse';
import { TokenService } from '../../service/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order',
  imports: [NgFor],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit{
  orders : OrderResponse[] = [];
  userId: number = 0;
  constructor(private orderService: OrderService,
    private tokenService: TokenService,
    private router: Router,
  ){

  }
  ngOnInit(): void {
    this.userId = this.tokenService.getUserId() || 0;
    this.orderService.getOrders(this.userId).subscribe({
      next: (data) => {
        this.orders = data.map((order: OrderResponse) => {
          return {
            ...order
          };
        });
      },error: (error) => {
        alert(`error: ${error.error.message}`);
      } 
    });
  }
  formatDate(timestamp: number): string {
    const date = new Date(timestamp);
    return date.toLocaleString('vi-VN');
  }

  formatCurrency(amount: number): string {
    return amount.toLocaleString('vi-VN') + '₫';
  }
  viewOrderDetail(order: any) {
    this.router.navigate(['/detailOrder', order.id]);
  }
}

