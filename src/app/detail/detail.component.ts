import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CurrencyPipe } from '../layout/pipes/CurrentcyPipes.pipe';
import { NgFor } from '@angular/common';
import { productItemApi } from '../types/productType';
import { productService } from '../../service/productService';
import { imgsService } from '../../service/imgsByProduct.service';
import { ImageType } from '../types/imageType';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-cart',
  imports: [FormsModule, ReactiveFormsModule, CurrencyPipe,NgFor, FormsModule],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css'
})
export class DetailComponent implements OnInit{
  id = '';
  imgs: string[] = [];
  detailProduct: productItemApi = {
  } as productItemApi
  constructor(private router: ActivatedRoute,
      private productSer: productService,
      private imgsService: imgsService,
      private cartService: CartService){
    this.id = String(router.snapshot.paramMap.get('id'));
  }

  handleAddToCart(){
    if(this.detailProduct){
      this.cartService.addToCart(this.detailProduct.id, this.quantity);
      console.log(this.cartService.getCart());
      alert('Thêm vào giỏ hàng thành công!');
    }else{
      alert('Vui lòng chọn sản phẩm!');
    }
  }

  quantity: number = 1;
  increaseQuality() {
    this.quantity++;
  }
  decreaseQuality() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  ngOnInit(): void {
    if(this.id){
      this.getDetail();
      this.getImg();
    }
  }
  getDetail(){
    this.productSer.getOne(+this.id).subscribe((data:any) =>{
      this.detailProduct.id = data.id,
      this.detailProduct.name= data.name,
      this.detailProduct.price= data.price,
      this.detailProduct.description= data.description,
      this.detailProduct.category_id= data.category_id,
      this.detailProduct.url= `http://localhost:8080/api/product/images/${data.thumnail}`
  })
  }

  changeImg(img: string){
    this.detailProduct.url = img;
    console.log(img);
  }

  getImg(){
    this.imgsService.getImgsByProduct(+this.id).subscribe({
    next: (data: any[]) => {
        this.imgs = data.map(img => `http://localhost:8080/api/product/images/${img.url_img}`);
      console.log(this.imgs);
    },
    error: (error) => { 
      console.error('Error fetching images:', error);
    }
  }
  )}
}