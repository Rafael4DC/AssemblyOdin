import { makeApiRequest } from "../../axios/apiRequest";
import {
    CreationUserResult,
    DeleteUserResult,
    GetAllUsersResult,
    GetUserResult
} from "./UserResult";
import {UserInputModel} from "./models/UserInputModel";

/**
 * Service to manage users
 */
export namespace UserService {

    const basePath = '/users';

    /**
     * Get the current session information
     *
     * @returns the current session information
     */
    export async function getSession(): Promise<GetUserResult> {
        return makeApiRequest('get', `${basePath}/session`);
    }

    /**
     * Get user by id
     *
     * @param id the id of the user
     *
     * @returns the user
     */
    export async function getById(id: string): Promise<GetUserResult> {
        return makeApiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all users
     *
     * @returns all users
     */
    export async function getAll(): Promise<GetAllUsersResult> {
        return makeApiRequest('get', basePath);
    }

    /**
     * Save a user
     *
     * @param userRequest the user to save
     *
     * @returns the result of the creation
     */
    export async function save(userRequest: UserInputModel): Promise<CreationUserResult> {
        return makeApiRequest('post', `${basePath}/save`, userRequest);
    }

    /**
     * Update a user
     *
     * @param userRequest the user to update
     *
     * @returns the result of the update
     */
    export async function update(userRequest: UserInputModel): Promise<CreationUserResult> {
        return makeApiRequest('put', `${basePath}/update`, userRequest);
    }

    /**
     * Delete a user by id
     *
     * @param id the id of the user
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteUserResult> {
        return makeApiRequest('delete', `${basePath}/${id}`);
    }

    /**
     * Get all Students
     *
     * @returns all students
     */
    export async function getStudents(): Promise<GetAllUsersResult> {
        return makeApiRequest('get', `${basePath}/students`);
    }
}
