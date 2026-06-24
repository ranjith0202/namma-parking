import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface LoginRequest {
  userName: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiBaseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(request: LoginRequest): Observable<any> {
   return this.http.post(
    `${this.apiBaseUrl}/login`,
    request,
    { responseType: 'text' }
  );
  }

  saveToken(token: string) {
  localStorage.setItem('token', token);

  const payload = JSON.parse(atob(token.split('.')[1]));

  localStorage.setItem('username', payload.sub);
}

getUserName(): string {
  return localStorage.getItem('username') || '';
}

logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('username');
}

isLoggedIn(): boolean {
  return !!localStorage.getItem('token');
}


registerUser(user: any) : Observable<any> {
    
  return this.http.post(
    `${this.apiBaseUrl}/register`,
    user,
    { responseType: 'text' }
  );
}
}
