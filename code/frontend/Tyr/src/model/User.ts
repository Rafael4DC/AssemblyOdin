export interface User {
    id?: number;
    email?: string;
    username?: string;
    role?: string;
    credits?: number;

    attendance?: boolean;
}


export enum RoleOptions {
    Admin = 'Admin',
    Teacher = 'Teacher',
    Student = 'Student'
}