import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CategoryItemApi } from "../app/types/categoryType";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class categoryService {
    private apiConfig = {
        headers: this.createHeaders(),
    }
    constructor(private http: HttpClient){ }

    private createHeaders(): HttpHeaders {
        return new HttpHeaders({
            'Content-Type': 'application/json'
        });
    }

    getAllCate(): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/category`);
    }

    getOneCate(id: number): Observable<any>{
        return this.http.get<any>(`http://localhost:8080/api/category/${id}`);
    }
}