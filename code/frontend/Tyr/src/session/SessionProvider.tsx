import {Session, SessionManager} from "./Session";
import * as React from "react";
import {createContext, useState} from "react";

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