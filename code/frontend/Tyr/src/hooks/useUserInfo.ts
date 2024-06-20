import {useEffect, useState} from 'react';
import {UserService} from '../services/user/UserService';
import {User} from '../services/user/models/User';
import {Tech} from "../services/tech/models/Tech";
import {Voc} from "../services/voc/models/Voc";
import {TechService} from "../services/tech/TechService";
import {VocService} from "../services/voc/VocService";

/**
 * Hook to get the user info
 *
 * @returns the user info, error and handles to save and delete a user info
 */
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
                setUserTechs(data.techs);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    useEffect(() => {
        VocService.getVocsByUser()
            .then(data => {
                setUserVocs(data.vocs);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {userInfo, userTechs, userVocs, error};
};

export default useUserInfo;
