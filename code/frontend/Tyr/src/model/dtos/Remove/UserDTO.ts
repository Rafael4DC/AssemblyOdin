import { User } from '../../User';

export interface UserDTO {
  id: number;
  username: string;
  email: string;
}

export function UserDTOtoUser(userDTO: UserDTO): User {
  return {
    id: userDTO.id,
    username: userDTO.username,
    role: null,
    email: userDTO.email,
    credits: null
  };
}

export function UserDTOListToUserList(userDTOs: UserDTO[]): User[] {
  return userDTOs.map(UserDTOtoUser);
}
