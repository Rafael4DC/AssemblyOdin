import { GetUserInfoOutputModel } from './models/GetUserInfoOutputModel';

export namespace UserService {

  export async function getUserInfo(): Promise<GetUserInfoOutputModel> {
    console.log("Getting user info");
    return {
      name: "Example Name",
      email: "example@email.com",
      role: "Example Role",
      credits: 100
    };
  }
}
