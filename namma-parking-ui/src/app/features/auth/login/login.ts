import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth';
import { FormsModule } from '@angular/forms';

import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
userName = '';
  password = '';

      errorMessage = '';


constructor(private authService: AuthService, private router: Router) {}

ngOnInit(): void {

    // If already logged in, redirect to dashboard
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }


 login() {
    this.errorMessage = '';

    const request = {
      userName: this.userName,
      password: this.password,
      
    };

    this.authService.login(request).subscribe({
  next: (token) => {

    localStorage.setItem('token', token);

    this.authService.saveToken(token);
    this.router.navigate(['/dashboard']);


    console.log(token);
  },
        error: (error) => {

         
          console.error('Login Error:', error);

          this.errorMessage = 'Invalid username or password';
        }
});

 
  }
  
}
