import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { productItemApi } from "../app/types/productType";

 @Injectable({providedIn: 'root'})
export class productService {
    private apiConfig = {
            headers: this.createHeaders(),
        }
        constructor(private http: HttpClient){ }
    
        private createHeaders(): HttpHeaders {
            return new HttpHeaders({
                'Content-Type': 'application/json'
            });
        }

    getProducts(page: number, size: number, keyword: string, categoryID: number): Observable<any>{
        const param = new HttpParams()
        .set('keyword', keyword.toString())
        .set('categoryID', categoryID.toString())
        .set('page', page.toString())
        .set('limit', size.toString());
        return this.http.get<any>(`http://localhost:8080/api/product/?${ param }`);
    }

    getProductsByIds(ids: number[]): Observable<any> {
        const params = new HttpParams().set('ids', ids.join(','));
        return this.http.get<any>(`http://localhost:8080/api/product/by-ids?${params}`);
    }

    getOne(id: number): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/product/${id}`);
    }
    addProduct(prod: productItemApi): Observable<any>{
        return this.http.post<any>(`https://ninedev-api.vercel.app/blogs`, prod);
    }

    deteleProd(id: number) : Observable<any>{
        return this.http.delete<any>(`https://ninedev-api.vercel.app/blogs/${id}`);
    }
}