import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CategoryItemApi } from "../app/types/categoryType";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class imgsService {
    private apiConfig = {
        headers: this.createHeaders(),
    }
    constructor(private http: HttpClient){ }

    private createHeaders(): HttpHeaders {
        return new HttpHeaders({
            'Content-Type': 'application/json'
        });
    }

    getImgsByProduct(idProduct: number): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/product/img/${idProduct}`);
    }
}