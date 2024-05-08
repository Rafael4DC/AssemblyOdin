import { useEffect, useState } from 'react';
import { UserService } from '../services/UserService';
import { User } from '../model/User';

const useUserInfo = (id: number) => {
  const [userInfo, setUserInfo] = useState<User | null>(null);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    UserService.getSession()
      .then(data => {
        setUserInfo(data);
      })
      .catch(err => {
        setError(err);
      });
  }, []);

  return { userInfo, error };
};

export default useUserInfo;
