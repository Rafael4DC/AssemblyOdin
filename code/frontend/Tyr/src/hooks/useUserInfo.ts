import { useEffect, useState } from 'react';
import { UserService } from '../services/User/UserService';
import { User } from '../services/User/models/GetUserInfoOutputModel';

const useUserInfo = () => {
  const [userInfo, setUserInfo] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    setTimeout(() => {
    UserService.getUserInfo()
      .then(data => {
        setUserInfo(data);
        setIsLoading(false);
      })
      .catch(err => {
        setError(err);
        setIsLoading(false);
      });
    }
    , 1000);
  });

  return { userInfo, isLoading, error };
};

export default useUserInfo;
