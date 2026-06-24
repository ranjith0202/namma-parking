import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth';


@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink,],
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class Menu implements OnInit {

  userName: string = '';

  constructor(private router: Router, private authService: AuthService,) {}

 

  ngOnInit(): void {
    this.userName = this.authService.getUserName();
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userName');

    this.router.navigate(['/login']);
  }
}


