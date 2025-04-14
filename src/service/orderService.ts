import { Injectable } from "@angular/core";
import { OrderType } from "../app/types/orderType";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class OrderService {
    private apiConfig = {
        headers: this.createHeaders(),
    }
    private createHeaders(): HttpHeaders {
            return new HttpHeaders({
                'Content-Type': 'application/json'
            });
        }
    constructor(private http: HttpClient){}

    createOrder(order: OrderType): Observable<any> {
        return this.http.post<any>(`http://localhost:8080/api/order`,order, this.apiConfig);
    }

    getOrders(userId: number): Observable<any> {
        return this.http.get<any>(`http://localhost:8080/api/order/user/${userId}`);
    }
}