import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRequest } from '../models/user-request';

import { ApiResponse } from '../models/api-response';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  
  private apiBaseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient){}

   

  registerUser(user: any) : Observable<any> {
    
  return this.http.post(
    `${this.apiBaseUrl}`,
    user,
    { responseType: 'text' }
  );
}

getAllUsers(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.apiBaseUrl);
  }
}
