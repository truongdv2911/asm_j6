import { Component, OnInit } from '@angular/core';
import { productService } from '../../service/productService';
import { TokenService } from '../../service/token.service';
import { productItemApi } from '../types/productType';
import { ActivatedRoute } from '@angular/router';
import { OrderDetailService } from '../../service/orderDetailService';
import { OrderResponse } from '../types/orderResponse';
import { OrderDetailType } from '../types/orderDetailType';
import { NgFor } from '@angular/common';
import { error } from 'node:console';
import { CurrencyPipe } from '../layout/pipes/CurrentcyPipes.pipe';

@Component({
  selector: 'app-order-detail',
  imports: [NgFor, CurrencyPipe],
  templateUrl: './order-detail.component.html',
  styleUrl: './order-detail.component.css'
})
export class OrderDetailComponent implements OnInit{
    id = '';
    constructor(private orderDetailService: OrderDetailService,
                private tokenService: TokenService, 
                private productService: productService,
                private router: ActivatedRoute)
     {
     }
    products: productItemApi[] = [];
    orderDetail: OrderDetailType[]  = [];

    ngOnInit(): void {
        this.id = String(this.router.snapshot.paramMap.get('id'));
        console.log(this.id)
        this.orderDetailService.getOrderDetail(+this.id).subscribe({
            next: (data) => {
                this.orderDetail = data.map((order: OrderDetailType) => {
                    return {
                        ...order,
                    };
                });
                const idProducts: number[] = this.orderDetail.map(id =>  id.productID);
                this.productService.getProductsByIds(idProducts).subscribe({
                    next: (data) => {
                    this.products = idProducts.map((id) => {
                    const product = data.find((item: productItemApi) => item.id === id);
                    if (product) {
                        product.url = `http://localhost:8080/api/product/images/${product.thumnail}`
                    }
                    return product;
                });
            },
            error: (error) => {
                alert(`error: ${error.error.message}`);
            }
    })
            },
            error: (error)=>{
                alert(`error: ${error.error.message}`);
            }
        });
  }
}
