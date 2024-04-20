import { useEffect, useState } from 'react';
import { UserService } from '../services/UserService';
import { User } from '../model/User';

const useUserInfo = () => {
  const [userInfo, setUserInfo] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    //setTimeout(() => {
    UserService.getById(1)
      .then(data => {
        setUserInfo(data);
        setIsLoading(false);
      })
      .catch(err => {
        setError(err);
        setIsLoading(false);
      });
    //}, 3000);
  }, []);

  return { userInfo, isLoading, error };
};

export default useUserInfo;
