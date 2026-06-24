import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
   userName: string = '';

  constructor(private router: Router, private authService: AuthService) {}
  ngOnInit(): void {
    this.userName = this.authService.getUserName();
  }
}
