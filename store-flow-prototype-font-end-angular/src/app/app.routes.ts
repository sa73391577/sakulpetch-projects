import { Routes } from '@angular/router';
import { Login } from './features/login/login.component';
import { Dashboard } from './features/dashboard/dashboard.component';
import { AdminLayout } from './shared/components/admin-layout/admin-layout';
import { authGuard } from './core/guards/auth-guard';
import { SignupComponent } from './features/signup/signup.component';

export const routes: Routes = [

    { path: 'login', component: Login },
    { path: 'signup/form', component: SignupComponent },
    {
        path: '',
        component: AdminLayout,
        canActivate: [authGuard],
        children: [
            {
                path: 'dashboard',
                loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.Dashboard),
                title: 'menu.dashboard'
            },
            {
                path: 'user-management',
                loadComponent: () => import('./features/user-management/user-management.component').then(m => m.UserManagementComponent),
                title: 'menu.user-management'
            },
            { path: '**', redirectTo: 'dashboard', pathMatch: 'full' }
        ]
    },
    { path: 'error_403', redirectTo: 'login' },
    { path: '**', redirectTo: 'login' }

];
