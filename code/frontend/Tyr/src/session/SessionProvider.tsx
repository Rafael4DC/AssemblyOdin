import {Session, SessionManager} from "./Session";
import * as React from "react";
import {createContext, useEffect, useState} from "react";
import {UserService} from "../services/user/UserService";
import {Failure, Success} from "../services/_utils/Either";

export const SessionContext = createContext<SessionManager>({
        sessionData: null,
        set: () => {
        },
        clear: () => {
        }
    }
);


export function SessionProvider({children}: { children: React.ReactNode }) {
    const [sessionData, setSessionData] = useState<Session | null>(null);

    useEffect(() => {
        UserService.getSession()
            .then(data => {
                if (data instanceof Success) {
                    setSessionData({
                        username: data.value.username,
                        email: data.value.email,
                        role: data.value.role.name
                    });
                } else if (data instanceof Failure) {
                    console.error('Error fetching data:', data.value);
                }
            })
            .catch(err => {
                console.error('Error fetching data:', err);
            });
    }, []);

    function set(credentials: Session) {
        setSessionData(credentials);
    }

    function clear() {
        setSessionData(null);
    }

    return (
        <SessionContext.Provider value={{sessionData, set, clear}}>
            {children}
        </SessionContext.Provider>
    );
}