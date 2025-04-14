import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";

 @Injectable({providedIn: 'root'})
export class TokenService {
private readonly TOKEN_KEY = 'access_token';
private jwtHelpers = new JwtHelperService();

    constructor(){ }

    getToken(): string | null {
        if(typeof window !== 'undefined' && window.localStorage) {
            return localStorage.getItem(this.TOKEN_KEY);
        }
        return null;
    }

    getUserId(): number {
        const token = this.getToken();
        let user = this.jwtHelpers.decodeToken(token!);
        return 'userId' in user ? parseInt(user['userId']) : 0;
    }

    setToken(token: string, remember: boolean): void {
        if(remember) {
            localStorage.setItem(this.TOKEN_KEY, token);
        }else{
            sessionStorage.setItem(this.TOKEN_KEY, token);
        }
    }

    removeToken(): void {
        localStorage.removeItem(this.TOKEN_KEY);
        sessionStorage.removeItem(this.TOKEN_KEY);
    }
}