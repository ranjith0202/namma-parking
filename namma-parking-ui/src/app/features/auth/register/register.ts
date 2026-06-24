import { Component } from '@angular/core';
import { UserService } from '../../../core/services/user';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterationComponent {

  roleOptions = [
  { id: 1, name: 'ADMIN' },
  { id: 2, name: 'USER' }
];

onRoleChange(event: Event, roleId: number): void {
  const checked = (event.target as HTMLInputElement).checked;

  if (checked) {
    this.user.rolesIds.push(roleId);
  } else {
    this.user.rolesIds = this.user.rolesIds.filter(id => id !== roleId);
  }
}
   user = {
    userName: '',
    password: '',
    email: '',
    rolesIds: [] as number[]
  };

  successMessage = '';
  errorMessage = '';

    constructor(private userService: UserService) {}
  register(): void {

    this.successMessage = '';
    this.errorMessage = '';

    this.userService.registerUser(this.user)
      .subscribe({
        next: () => {
          this.successMessage = 'User Registered Successfully';

          this.user = {
            userName: '',
            password: '',
            email: '',
            rolesIds: [] as number[]
          };
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Registration Failed';
        }
      });
  }
}
