export interface LoginRequest {
    username: string;
    password: string;
}

export interface UserSession {
    username: string;
    password: string;
}

export interface LoginResponse {
    token: string;
}