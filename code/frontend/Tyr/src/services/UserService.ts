import { RoleOptions, User } from '../model/GetUserInfoOutputModel';

export namespace UserService {

  export async function getUserInfo(): Promise<User> {
    console.log('Getting user info');
    return {
      id:1,
      name: 'Example Name',
      email: 'example@email.com',
      role: RoleOptions.Admin,
      credits: 100,
    };
  }
}
