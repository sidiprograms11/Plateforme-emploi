import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <div class="layout">

      <!-- SIDEBAR -->
      <aside class="sidebar">

        <h2>Admin Panel</h2>

        <nav>
          <a routerLink="dashboard" routerLinkActive="active">📊 Dashboard</a>

          <a routerLink="identity" routerLinkActive="active">👤 Identités</a>

          <a routerLink="users" routerLinkActive="active">👥 Utilisateurs</a>

          <a routerLink="offers" routerLinkActive="active">💼 Offres</a>

          <a routerLink="logs" routerLinkActive="active">📜 Logs</a>
          <a routerLink="monitoring" routerLinkActive="active">
📄 Monitoring Documents
</a>
        </nav>

      </aside>

      <!-- CONTENT -->
      <main class="content">
        <router-outlet></router-outlet>
      </main>

    </div>
  `,
  styleUrls: ['./admin-layout.component.css']
})
export class AdminLayoutComponent {}