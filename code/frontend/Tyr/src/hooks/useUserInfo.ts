import { useState, useEffect } from 'react';
import { UserService } from '../services/User/UserService';
import { GetUserInfoOutputModel } from '../services/User/models/GetUserInfoOutputModel';

const useUserInfo = () => {
  const [userInfo, setUserInfo] = useState<GetUserInfoOutputModel | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    UserService.getUserInfo()
      .then(data => {
        setUserInfo(data);
        setIsLoading(false);
      })
      .catch(err => {
        setError(err);
        setIsLoading(false);
      });
  }, []);

  return { userInfo, isLoading, error };
};

export default useUserInfo;
