import {Role} from "./Role";
import {UserInputModel} from "./UserInputModel";

/**
 * User model
 *
 * @param id the id of the user
 * @param email the email of the user
 * @param username the username of the user
 * @param role the role of the user
 * @param credits the credits of the user
 */
export interface User {
    id?: number;
    email?: string;
    username?: string;
    role?: Role;
    credits?: number;
}

export function userToInput(user: User): UserInputModel {
    return {
        id: user.id,
        email: user.email,
        username: user.username,
        role: user.role.id,
        credits: user.credits,
    }
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