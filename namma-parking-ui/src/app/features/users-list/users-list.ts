import { Component,OnInit  } from '@angular/core';
import { UserService } from '../../core/services/user';
import { UserResponse } from '../../core/models/user-response';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-users-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users-list.html',
  styleUrl: './users-list.css',
})
export class UsersList implements OnInit{

   constructor(private userService: UserService) {}
    users: UserResponse[] = [];

ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (response) => {
        this.users = response.data;
      },
      error: (error) => {
        console.error('Error fetching users', error);
      }
    });
  }
}
