import { Injectable } from "@angular/core";
import { productService } from "./productService";
//import { LocalStorageService } from "ngx-webstorage";

@Injectable({
    providedIn: 'root'
})

export class CartService {
    private cart: Map<number, number> = new Map<number, number>();

    constructor(private productSer: productService) {
        if(typeof window !== 'undefined'){
            const storeCart = localStorage.getItem('cart');
            if (storeCart) {
                this.cart = new Map<number, number>(JSON.parse(storeCart));
            }else{
                this.cart = new Map<number, number>();
            }
        }else{
            this.cart = new Map<number, number>();
        }
    }

    addToCart(productId: number, quantity: number): void {
        if(this.cart.has(productId)) {
            this.cart.set(productId, this.cart.get(productId)! + quantity);
        }else{
            this.cart.set(productId, quantity);
        }
        this.saveCartToLocalStorage();
    }

    removeFromCart(productId: number): void {
        this.cart.delete(productId);
        this.saveCartToLocalStorage();
    }

    clearCart(): void {
        this.cart.clear();
        this.saveCartToLocalStorage();
    }

    getCart(): Map<number, number> {
        return this.cart;
    }

    saveCartToLocalStorage(): void {
        localStorage.setItem('cart', JSON.stringify(Array.from(this.cart.entries())));
    }

}