import { Routes } from '@angular/router';
import path from 'path';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { DetailComponent } from './detail/detail.component';
 import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ListproductComponent } from './listproduct/listproduct.component';
export const routes: Routes = [
    {
        path: '',
       component: HomeComponent,
   }, 
   {
        path:'loginform',
        component: LoginComponent
   },
   {
    path:'register',
    component: RegisterComponent
    },
    {
    path: 'products',
    component: ListproductComponent
    },
    {
        path: 'cart',
        loadComponent: () => import('./cart/cart.component').then(m => m.CartComponent)
    },
    {
        path: 'orders',
        loadComponent: () => import('./order/order.component').then(m => m.OrderComponent)
    },
    {
        path: 'detailOrder/:id',
        loadComponent: () => import('./order-detail/order-detail.component').then(m => m.OrderDetailComponent)
    },
    {
        path: 'detail/:id',
        component: DetailComponent
    },
    
    // {
    //     path: 'addProduct',
    //     loadComponent: () => import('./addProduct/add.component').then(m => m.AddComponent)
    // }
];
