/**
 * User model
 *
 * @param id the id of the user
 * @param email the email of the user
 * @param username the username of the user
 * @param role the role of the user
 * @param credits the credits of the user
 *
 * @param attendance if the user attended the class
 */
export interface User {
    id?: number;
    email?: string;
    username?: string;
    role?: string;
    credits?: number;

    attendance?: boolean;
}

/**
 * Role options
 *
 * @param Admin the admin role
 * @param Teacher the teacher role
 * @param Student the student role
 */
export enum RoleOptions {
    Admin = 'Admin',
    Teacher = 'Teacher',
    Student = 'Student'
}