import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Observable } from "rxjs";
import { productItemApi } from "../app/types/productType";
import { userApi } from "../app/types/userType";
import { userLogin } from "../app/types/userLogin";
import { UserResponse } from "../app/types/userResponse";

 @Injectable({providedIn: 'root'})
export class UserService {
    private apiConfig = {
        headers: this.createHeaders(),
    }
    constructor(private http: HttpClient){ }

    private createHeaders(): HttpHeaders {
        return new HttpHeaders({
            'Content-Type': 'application/json'
        });
    }

    login(userLogin: userLogin): Observable<any>{
        return this.http.post<any>(`http://localhost:8080/api/users/login`,userLogin, this.apiConfig);
    }
    register(userRegister: userApi):Observable<any>{
        return this.http.post<any>(`http://localhost:8080/api/users/register`, userRegister,this.apiConfig);
    }
    detail(token: string):Observable<any>{
        return this.http.post<any>(`http://localhost:8080/api/users/detail`, token, this.apiConfig);
    }

    saveUserToLocalStorage(user?: UserResponse): void {
        try{
            if(user == null || user == undefined){
                return;
            }
            localStorage.setItem('userResponse', JSON.stringify(user));
        }catch (error) {
            console.error('Error saving user to local storage:', error);
        }
    }

    getUserFromLocalStorage(): UserResponse | null {
        try {
            if (typeof window !== 'undefined' && typeof localStorage !== 'undefined') {
                const user = localStorage.getItem('userResponse');
                if (user) {
                    return JSON.parse(user) as UserResponse;
                }   
            }
        } catch (error) {
            console.error('Error retrieving user from local storage:', error);
        }
        return null;
    }
    removeUserFromLocalStorage(): void {
        try {
            localStorage.removeItem('userResponse');
        }catch (error) {
            console.error('Error removing user from local storage:', error);
        }
    }
}