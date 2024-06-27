import {apiRequest} from "../../axios/apiRequest";
import {CreationUserResult, DeleteUserResult, GetAllUsersResult, GetUserLogsResult, GetUserResult} from "./UserResult";
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
        return apiRequest('get', `${basePath}/session`);
    }

    /**
     * Get the current session information with logs
     *
     * @returns the current session information with logs
     */
    export async function getSessionWithLogs(): Promise<GetUserLogsResult> {
        return apiRequest('get', `${basePath}/logs`);
    }

    /**
     * Get user by id
     *
     * @param id the id of the user
     *
     * @returns the user
     */
    export async function getById(id: string): Promise<GetUserResult> {
        return apiRequest('get', `${basePath}/${id}`);
    }

    /**
     * Get all users
     *
     * @returns all users
     */
    export async function getAll(): Promise<GetAllUsersResult> {
        return apiRequest('get', basePath);
    }

    /**
     * Save a user
     *
     * @param userRequest the user to save
     *
     * @returns the result of the creation
     */
    export async function save(userRequest: UserInputModel): Promise<CreationUserResult> {
        return apiRequest('post', `${basePath}/save`, userRequest);
    }

    /**
     * Update a user
     *
     * @param userRequest the user to update
     *
     * @returns the result of the update
     */
    export async function update(userRequest: UserInputModel): Promise<CreationUserResult> {
        return apiRequest('put', `${basePath}/update`, userRequest);
    }

    /**
     * Delete a user by id
     *
     * @param id the id of the user
     *
     * @returns the result of the deletion
     */
    export async function deleteById(id: number): Promise<DeleteUserResult> {
        return apiRequest('delete', `${basePath}/${id}`);
    }

    /**
     * Get all Students
     *
     * @returns all students
     */
    export async function getStudents(): Promise<GetAllUsersResult> {
        return apiRequest('get', `${basePath}/students`);
    }
}
