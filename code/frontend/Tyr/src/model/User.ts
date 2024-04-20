export interface User {
  id?: number;
  username?: string;
  role?: string;
  email?: string;
  credits?: number;
}


export enum RoleOptions {
  Admin = 'Admin',
  Teacher = 'Teacher',
  Student = 'Student'
}