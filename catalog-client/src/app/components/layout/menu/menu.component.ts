// menu.component.ts
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';

interface Notification {
  id: number;
  message: string;
  time: string;
  read: boolean;
}

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, MdbCollapseModule],
  template: `
    <nav class="main-nav" [class.dark-mode]="isDarkMode">
      <div class="nav-container">
        <div class="nav-content">
          <!-- Logo and Brand -->
          <div class="nav-brand">
            <a class="brand-logo" href="/">Catalog</a>

            <!-- Desktop Navigation -->
            <div class="nav-links desktop-only">
              <a href="admin/products" class="nav-link">
                <i class="fas fa-box-open"></i>
                Products
              </a>
              <a href="admin/categories" class="nav-link">
                <i class="fas fa-tags"></i>
                Categories
              </a>
            </div>
          </div>

          <!-- Right side icons -->
          <div class="nav-actions">
            <!-- Theme Toggle -->
            <button (click)="toggleTheme()" class="action-button">
              <i class="fas" [class.fa-sun]="!isDarkMode" [class.fa-moon]="isDarkMode"></i>
            </button>

            <!-- Notifications -->
            <div class="dropdown">
              <button (click)="toggleNotifications()" class="action-button">
                <i class="fas fa-bell"></i>
                <span *ngIf="unreadNotifications > 0" class="notification-badge">
                  {{unreadNotifications}}
                </span>
              </button>

              <!-- Notifications Dropdown -->
              <div *ngIf="showNotifications" class="dropdown-menu notifications-menu">
                <div class="dropdown-header">
                  <h3>Notifications</h3>
                </div>
                <div class="notifications-list">
                  <div *ngFor="let notification of notifications"
                       class="notification-item"
                       [class.unread]="!notification.read">
                    <p class="notification-message">{{notification.message}}</p>
                    <span class="notification-time">{{notification.time}}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Profile Dropdown -->
            <div class="dropdown">
              <button (click)="toggleProfileMenu()" class="profile-button">
                <img class="profile-image"
                     src="https://ui-avatars.com/api/?name=User&background=0D8ABC&color=fff"
                     alt="Profile">
                <span class="profile-name desktop-only">User Name</span>
              </button>

              <!-- Profile Dropdown Menu -->
              <div *ngIf="showProfileMenu" class="dropdown-menu profile-menu">
                <a href="/profile" class="dropdown-item">
                  <i class="fas fa-user"></i>
                  Profile
                </a>
                <a href="/settings" class="dropdown-item">
                  <i class="fas fa-cog"></i>
                  Settings
                </a>
                <button (click)="logout()" class="dropdown-item logout-item">
                  <i class="fas fa-sign-out-alt"></i>
                  Logout
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  `,
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  private router = inject(Router);

  isDarkMode = false;
  showNotifications = false;
  showProfileMenu = false;
  unreadNotifications = 3;

  notifications: Notification[] = [
    {
      id: 1,
      message: 'New order received #1234',
      time: '5 minutes ago',
      read: false
    },
    {
      id: 2,
      message: 'Your report is ready',
      time: '1 hour ago',
      read: false
    },
    {
      id: 3,
      message: 'Welcome to the new system!',
      time: '1 day ago',
      read: false
    }
  ];

  ngOnInit() {
    this.isDarkMode = localStorage.getItem('darkMode') === 'true';
    this.updateTheme();
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    localStorage.setItem('darkMode', this.isDarkMode.toString());
    this.updateTheme();
  }

  updateTheme() {
    if (this.isDarkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
    this.showProfileMenu = false;
  }

  toggleProfileMenu() {
    this.showProfileMenu = !this.showProfileMenu;
    this.showNotifications = false;
  }

  logout() {
    // Implement your logout logic here
    console.log('Logging out...');
    this.router.navigate(['/login']);
  }
}
