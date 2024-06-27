import {Route, Routes} from 'react-router-dom';
import Home from '../pages/Home/Home';
import * as React from 'react';
import CurricularUnitManagerFocused from '../pages/_refactor/CurricularUnitManagerFocused/CurricularUnitManagerFocused';
import ManageClass from '../pages/_refactor/ManageClassses/ManageClasses';
import ManageUsers from '../pages/_refactor/ManageUsers/ManageUsers';
import {WebUris} from "./WebUris";
import Departments from "../pages/Departments/Department";
import ManageDepartments from "../pages/DepartmentsManager/DepartmentsManager";
import CreateVoc from "../pages/CreateClass/CreateVoc";
import CreateTech from "../pages/CreateClass/CreateTech";
import CreateSection from "../pages/Section/CreateSection";
import ViewSections from "../pages/Section/ViewSections";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import DEPARTMENTS = WebUris.DEPARTMENTS;
import MANAGE_CLASS = WebUris.MANAGE_CLASS;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import FOCUSED = WebUris.FOCUSED;
import NOTFOUND = WebUris.NAN;
import CREATE_SECTION = WebUris.CREATE_SECTION;
import DEPARTMENT_MANAGER = WebUris.DEPARTMENT_MANAGER;
import SECTION = WebUris.SECTION;
import CREATE_VOC = WebUris.CREATE_VOC;
import CREATE_TECH = WebUris.CREATE_TECH;
import Profile from "../pages/Profile/Profile";
import TIMETABLE = WebUris.TIMETABLE;
import Timetable from "../pages/TimeTable/TimeTable";

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

            <Route path={MANAGE_USERS} element={<ManageUsers/>}/>
            <Route path={DEPARTMENT_MANAGER} element={<ManageDepartments/>}/>

            <Route path={FOCUSED} element={<CurricularUnitManagerFocused/>}/>
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