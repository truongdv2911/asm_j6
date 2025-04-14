import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CurrencyPipe } from '../layout/pipes/CurrentcyPipes.pipe';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { productItemApi } from '../types/productType';
import { productService } from '../../service/productService';

@Component({
  selector: 'app-home',
  imports: [FormsModule,CurrencyPipe, NgFor, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit, OnDestroy{
  products: productItemApi[]=[];
  currentPage: number = 1;
  pageSize: number = 8;
  totalPages: number = 0;
  pages: number[] = [];
  visiblePages: number[] = [];
  keyword: string = '';
  categoryID: number = 0;
  slides: string[] = [
    'assets/img/slideShow4.jpg',
    'assets/img/sledeShow3.jpg',
    'assets/img/slideShow1.jpg',
    'assets/img/slideShow2.jpg',
  ];
  currentIndex: number = 0;
  srcImg: string ='assets/img/slideShow4.jpg';
  intervalID: any = null;
  slideShow() {
    this.currentIndex = (this.currentIndex + 1) % this.slides.length;
    this.srcImg = this.slides[this.currentIndex];
  }

  startSlide(){
      if(!this.intervalID){
        this.intervalID = setInterval(() => {
          this.slideShow();
        }, 3000);
      }
  }
  stopSlide(){
    if(this.intervalID){
      clearInterval(this.intervalID);
    this.intervalID=null;
    }
  }
  constructor(private productService: productService){
  }
  onChangePage(page: number): void {
    this.currentPage = page;
    this.getPageProducts(this.currentPage, this.pageSize, this.keyword, this.categoryID);
  }

  generateVisiblePages(currentPage: number, totalPages: number): number[] {
    const visiblePages: number[] = [];
    const maxVisible = 5;
    const startPage = Math.max(1, currentPage - Math.floor(maxVisible / 2));
    const endPage = Math.min(totalPages, startPage + maxVisible);

    for (let i = startPage; i < endPage; i++) {
      visiblePages.push(i);
    }
    return visiblePages;
  }
  getPageProducts(page: number, limit: number, keyword: string,categoryID: number): void {
    this.productService.getProducts(page -1, limit, keyword, categoryID).subscribe({
      next: (res: any) => {
        res.products.forEach((product: productItemApi) => 
          product.url = `http://localhost:8080/api/product/images/${product.thumnail}`
        );
        this.products = res.products;
        this.totalPages = res.totalPages;
        this.visiblePages = this.generateVisiblePages(this.currentPage, this.totalPages);
      },
      error: (error: any) => {
        console.error('Error fetching products:', error);
      }
    });
  }

  ngOnInit(): void {
    this.getPageProducts(this.currentPage, this.pageSize, this.keyword, this.categoryID);
    // this.startSlide();
  }
  ngOnDestroy(): void {
    this.stopSlide();
  }
} 


