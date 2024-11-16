import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, FormsModule],
  styleUrls: ['./principal.component.scss']
})
export class PrincipalComponent implements OnInit {
  isDarkMode: boolean = false;
  isSidebarCollapsed: boolean = false;
  searchQuery: string = '';
  unreadNotifications: number = 0;
  userName: string = 'Admin';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.loadUserPreferences();
    this.loadNotifications();
  }

  toggleTheme(): void {
    this.isDarkMode = !this.isDarkMode;
    localStorage.setItem('darkMode', JSON.stringify(this.isDarkMode));
  }

  toggleSidebar(): void {
    this.isSidebarCollapsed = !this.isSidebarCollapsed
    localStorage.setItem('sidebarCollapsed', JSON.stringify(this.isSidebarCollapsed));
  }

  performSearch(): void {
    if (this.searchQuery.trim()) {
      // TODO - Implement search functionality
      console.log('Searching for:', this.searchQuery);
    }
  }

  toggleNotifications(): void {
    // TODO - Implement notifications panel
    console.log('Toggling notifications panel');
  }

  logout(): void {
    // TODO - Call API to logout user
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  private loadUserPreferences(): void {
    // TODO - Load user preferences from API
    const darkMode = localStorage.getItem('darkMode');
    const sidebarCollapsed = localStorage.getItem('sidebarCollapsed');

    if (darkMode) {
      this.isDarkMode = JSON.parse(darkMode);
    }

    if (sidebarCollapsed) {
      this.isSidebarCollapsed = JSON.parse(sidebarCollapsed);
    }
  }

  private loadNotifications(): void {
    // TODO - Load notifications from API
    this.unreadNotifications = 0;
  }
}
