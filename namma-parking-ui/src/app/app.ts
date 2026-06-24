import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Menu } from './shared/menu/menu';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
    standalone: true,
  imports: [RouterOutlet,Menu],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('namma-parking-ui');
constructor(private router: Router) {}

  isLoginPage(): boolean {
  return this.router.url === '/login';
}
}
