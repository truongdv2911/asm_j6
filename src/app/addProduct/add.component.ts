import { Component, Input } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, NgIf } from '@angular/common';
import { productService } from '../../service/productService';
import { productItemApi } from '../types/productType';

// @Component({
//   selector: 'app-add-product',
//   standalone: true,
//   imports: [NgIf, CommonModule, FormsModule, ReactiveFormsModule],
//   templateUrl: './add.component.html',
//   styleUrl: './add.component.css',
// })
// export class AddComponent {
//   product1 = new FormGroup({
//     title: new FormControl('',Validators.required),
//     price: new FormControl('',Validators.required),
//     imgUrl: new FormControl('',Validators.required)
//   })

//   get title(){
//     return this.product1.get('title');
//   }
//   get price(){
//     return this.product1.get('price');
//   }
//   get imgUrl(){
//     return this.product1.get('imgUrl');
//   }
//   constructor(private productService: productService, private router: Router){

//   }
//   handleAdd(){
//     if(this.title?.hasError('required') || this.price?.hasError('required'))return
//     let productAdd : productItemApi = {
//         id: Math.random(),
//         title: String(this.title?.value),
//         body: String(this.price?.value),
//         author: String(this.imgUrl?.value)
//     }
//     this.productService.addProduct(productAdd).subscribe(({data} : any) => {
//       if(data.id){
//           this.router.navigate(['/']);
//       }
//     });
//   }
// }