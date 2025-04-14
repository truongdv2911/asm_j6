import { Component, OnInit } from '@angular/core';
import { productItemApi } from '../types/productType';
import { productService } from '../../service/productService';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { CurrencyPipe } from '../layout/pipes/CurrentcyPipes.pipe';
import { RouterLink } from '@angular/router';
import { categoryService } from '../../service/category.service';
import { Category, CategoryItemApi } from '../types/categoryType';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listproduct',
  imports: [NgClass, NgIf, CurrencyPipe, NgFor, RouterLink, FormsModule],
  templateUrl: './listproduct.component.html',
  styleUrl: './listproduct.component.css'
})
export class ListproductComponent implements OnInit{
products: productItemApi[]=[];
filteItems: productItemApi[]=[];
  currentPage: number = 1;
  pageSize: number = 12;
  totalPages: number = 0;
  pages: number[] = [];
  visiblePages: number[] = [];
  keyword: string = '';
  categoryID: number = 0;
  categoris: CategoryItemApi[] = [];

  constructor(private productService: productService, private categoryService: categoryService){
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
  getCategory(){
    this.categoryService.getAllCate().subscribe({
      next: (res: any) => {
        console.log(res);
        this.categoris  = res;
        console.log(this.categoris);
      },
      error: (error: any) => {
        console.error('Error fetching categories:', error);
      }
    })
  }
  onChangeCategory(): void {
    this.getPageProducts(this.currentPage, this.pageSize, this.keyword, this.categoryID);
  }
  ngOnInit(): void {
    this.getPageProducts(this.currentPage, this.pageSize, this.keyword, this.categoryID);
    this.getCategory();
    console.log(this.categoryID);
  }
}
