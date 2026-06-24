import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { Dashboard } from './features/auth/dashboard/dashboard';
import { RegisterationComponent } from './features/auth/register/register';
import { UsersList } from './features/users-list/users-list';

export const routes: Routes = [ {
    path: '',
    component: Login
  },
  {
    path: 'login',
    component: Login
  },
{
    path: 'dashboard',
    component: Dashboard
  },
{
    path: 'users/register',
    component: RegisterationComponent
  },
{
    path: 'users/list',
    component: UsersList
  }

];
