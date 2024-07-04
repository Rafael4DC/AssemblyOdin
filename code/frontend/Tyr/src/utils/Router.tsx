import {Route, Routes} from 'react-router-dom';
import Home from '../pages/Home/Home';
import * as React from 'react';
import ManageClass from '../pages/Voc/ManageVoc';
import ManageUsers from '../pages/ManageUsers/ManageUsers';
import {WebUris} from "./WebUris";
import Departments from "../pages/Departments/Department";
import ManageDepartments from "../pages/Departments/ManageDepartments";
import CreateVoc from "../pages/Voc/CreateVoc";
import CreateTech from "../pages/Tech/CreateTech";
import CreateSection from "../pages/Section/CreateSection";
import ViewSections from "../pages/Section/ViewSections";
import Profile from "../pages/Profile/Profile";
import Timetable from "../pages/TimeTable/TimeTable";
import ManageSections from "../pages/Section/ManageSections";
import ManageTech from "../pages/Tech/ManageTech";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import DEPARTMENTS = WebUris.DEPARTMENTS;
import MANAGE_CLASS = WebUris.MANAGE_VOC;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import NOTFOUND = WebUris.NAN;
import CREATE_SECTION = WebUris.CREATE_SECTION;
import DEPARTMENT_MANAGER = WebUris.MANAGE_DEPARTMENT;
import SECTION = WebUris.SECTION;
import CREATE_VOC = WebUris.CREATE_VOC;
import CREATE_TECH = WebUris.CREATE_TECH;
import MANAGE_SECTIONS = WebUris.MANAGE_SECTIONS;
import MANAGE_TECH = WebUris.MANAGE_TECH;
import TIMETABLE = WebUris.TIMETABLE;

/**
 * Router component
 */
export function AppRoutes() {
    return (
        <Routes>
            <Route path={HOME} element={<Home/>}/>

            <Route path={PROFILE} element={<Profile/>}/>
            <Route path={TIMETABLE} element={<Timetable/>}/>
            <Route path={DEPARTMENTS} element={<Departments/>}/>
            <Route path={CREATE_VOC} element={<CreateVoc/>}/>

            <Route path={SECTION} element={<ViewSections/>}/>
            <Route path={CREATE_SECTION} element={<CreateSection/>}/>
            <Route path={CREATE_TECH} element={<CreateTech/>}/>
            <Route path={MANAGE_CLASS} element={<ManageClass/>}/>
            <Route path={MANAGE_TECH} element={<ManageTech/>}/>

            <Route path={MANAGE_SECTIONS} element={<ManageSections/>}/>
            <Route path={MANAGE_USERS} element={<ManageUsers/>}/>
            <Route path={DEPARTMENT_MANAGER} element={<ManageDepartments/>}/>

            <Route path={NOTFOUND} element={<NAN/>}/>
        </Routes>
    )
}

/**
 * Not found component
 */
function NAN() {
    return (
        <div>
            <h1>This is currently not working</h1>
        </div>
    );
}