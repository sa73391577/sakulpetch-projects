import { SignUpForm } from '../models/signup.model';
import { InjectionToken } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';

export interface UserService {
    add(dataForm: SignUpForm): Observable<any>;
    update(dataForm: SignUpForm): Observable<any>;
    list(): Observable<any>;
    getByUsername(username: string | null): Observable<any>;
    deleteByUsername(sername: string | null): Observable<any>;
}

export const USER_SERVICE_TOKEN = new InjectionToken<UserService>('USER_SERVICE_TOKEN');