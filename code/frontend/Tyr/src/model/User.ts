export interface User {
<<<<<<<< HEAD:code/frontend/Tyr/src/model/GetUserInfoOutputModel.ts
  id: number;
  username: string;
  role: string;
  email: string;
  credits: number;
========
  id?: number;
  name?: string;
  role?: string;
  email?: string;
  credits?: number;
>>>>>>>> 0e2d353c7b009be5726e59389efe675e3774473d:code/frontend/Tyr/src/model/User.ts
}


export enum RoleOptions {
  Admin = 'Admin',
  Teacher = 'Teacher',
  Student = 'Student'
}