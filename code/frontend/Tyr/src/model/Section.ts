import {Module} from "./Module";
import {User} from "./User";


export interface Section {
    id?: number;
    name?: string;
    summary?: string;
    module?: Module;
    students?: User[];
}

export interface SectionRequest {
    id?: number;
    name?: string;
    summary?: string;
    module?: number;
    students?: number[];
}