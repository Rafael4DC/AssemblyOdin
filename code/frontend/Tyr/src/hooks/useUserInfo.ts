import { useEffect, useState } from 'react';
import { UserService } from '../services/UserService';
import { User } from '../model/User';
import {Tech} from "../model/Tech";
import {Voc} from "../model/Voc";
import {TechService} from "../services/TechService";
import {VocService} from "../services/VocService";

const useUserInfo = () => {
  const [userInfo, setUserInfo] = useState<User | null>(null);
  const [userTechs, setUserTechs] = useState<Tech[]>([]);
  const [userVocs, setUserVocs] = useState<Voc[]>([]);
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

  useEffect(() => {
      TechService.getTechsByUser()
      .then(data => {
          setUserTechs(data);
      })
      .catch(err => {
          setError(err);
      });
  }, []);

    useEffect(() => {
        VocService.getVocsByUser()
        .then(data => {
            setUserVocs(data);
        })
        .catch(err => {
            setError(err);
        });
    }, []);

  return { userInfo, userTechs, userVocs, error };
};

export default useUserInfo;
