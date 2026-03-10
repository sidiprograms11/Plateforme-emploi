import { Routes } from '@angular/router';
import { AdminGuard } from './guards/admin.guard';

export const routes: Routes = [

  // ROOT
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  {
    path: 'login',
    loadComponent: () =>
      import('./login/login.component')
        .then(m => m.LoginComponent)
  },

  {
    path: 'register',
    loadComponent: () =>
      import('./register/register.component')
        .then(m => m.RegisterComponent)
  },

  // ===============================
  // USER AREA
  // ===============================
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./features/user/user-layout/user-layout')
        .then(m => m.UserLayout),

    children: [

      {
        path: 'coffre',
        loadComponent: () =>
          import('./features/user/coffre/coffre')
            .then(m => m.CoffreComponent)
      },

      {
        path: 'offers',
        loadComponent: () =>
          import('./features/offers/offers.component')
            .then(m => m.OffersComponent)
      },

      {
        path: 'upload',
        loadComponent: () =>
          import('./features/user/uploadfile/uploadfile')
            .then(m => m.UploadComponent)
      },

      {
        path: '',
        redirectTo: 'coffre',
        pathMatch: 'full'
      }

    ]
  },

  // ===============================
  // ADMIN
  // ===============================
  {
    path: 'admin',
    canActivate: [AdminGuard],
    loadComponent: () =>
      import('./features/admin/admin-layout/admin-layout.component')
        .then(m => m.AdminLayoutComponent),

    children: [

      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/admin/dashboard/admin-dashboard.component')
            .then(m => m.AdminDashboardComponent)
      },

      {
        path: 'identity',
        loadComponent: () =>
          import('./features/admin/identity/identity-list.component')
            .then(m => m.IdentityListComponent)
      },

      {
        path: 'offers',
        loadComponent: () =>
          import('./features/admin/offers/admin-offers.component')
            .then(m => m.AdminOffersComponent)
      },

      {
        path: 'offers/create',
        loadComponent: () =>
          import('./features/admin/offers/create-offer.component')
            .then(m => m.CreateOfferComponent)
      },

      {
        path: 'offers/edit/:id',
        loadComponent: () =>
          import('./features/admin/offers/edit-offer.component')
            .then(m => m.EditOfferComponent)
      },

      {
        path: 'offers/:id/applications',
        loadComponent: () =>
          import('./features/admin/applications/offer-applications.component')
            .then(m => m.OfferApplicationsComponent)
      },

      {
        path: 'users',
        loadComponent: () =>
          import('./features/admin/users/admin-users.component')
            .then(m => m.AdminUsersComponent)
      },

      {
        path: 'monitoring',
        loadComponent: () =>
          import('./features/admin/documents-monitor/admin-documents-monitor.component')
            .then(m => m.AdminDocumentsMonitorComponent)
      },

      {
        path: 'logs',
        loadComponent: () =>
          import('./features/admin/logs/admin-logs.component')
            .then(m => m.AdminLogsComponent)
      },

      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }

    ]
  },

  // FALLBACK
  { path: '**', redirectTo: 'login' }

];