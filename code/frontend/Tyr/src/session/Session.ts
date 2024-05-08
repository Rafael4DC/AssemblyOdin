import {createContext} from "node:vm";
import {useContext} from "react";
import {SessionContext} from "./SessionProvider";


export interface Session {
    username: string;
    email: string;
}

export interface SessionManager {
    sessionData: Session | null;
    set: (credentials: Session) => void;
    clear: () => void;
}

export function useSessionData() {
    return useContext(SessionContext).sessionData;
}

export function useSessionManager() {
    return useContext(SessionContext);
}



