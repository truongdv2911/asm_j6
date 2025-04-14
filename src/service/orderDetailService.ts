import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class OrderDetailService {
    constructor(private http: HttpClient) { }
    getOrderDetail(idOrder: number): Observable<any> {
            return this.http.get<any>(`http://localhost:8080/api/order_detail/order/${idOrder}`);
        }
}
