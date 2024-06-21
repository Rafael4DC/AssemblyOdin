import {Route, Routes} from 'react-router-dom';
import Home from '../pages/Home/Home';
import * as React from 'react';
import Profile from '../pages/Profile/Profile';
import CurricularUnitManagerFocused from '../pages/CurricularUnitManagerFocused/CurricularUnitManagerFocused';
import ManageClasses from '../pages/ManageClassses/ManageClasses';
import ManageUsers from '../pages/ManageUsers/ManageUsers';
import {WebUris} from "./WebUris";
import Category from "../pages/Departments/Department";
import DepartmentsManager from "../pages/DepartmentsManager/DepartmentsManager";
import CreateVoc from "../pages/CreateClass/CreateVoc";
import CreateTech from "../pages/CreateClass/CreateTech";
import CreateSection from "../pages/Section/CreateSection";
import ViewSections from "../pages/Section/ViewSections";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import DEPARTMENTS = WebUris.DEPARTMENTS;
import MANAGE_CLASSES = WebUris.MANAGE_CLASSES;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import FOCUSED = WebUris.FOCUSED;
import NOTFOUND = WebUris.NAN;
import CREATE_SECTION = WebUris.CREATE_SECTION;
import DEPARTMENT_MANAGER = WebUris.DEPARTMENT_MANAGER;
import SECTION = WebUris.SECTION;
import CREATE_VOC = WebUris.CREATE_VOC;
import CREATE_TECH = WebUris.CREATE_TECH;

/**
 * Router component
 */
export function AppRoutes() {
    return (
        <Routes>
            <Route path={HOME} element={<Home/>}/>
            <Route path={PROFILE} element={<Profile/>}/>
            <Route path={DEPARTMENTS} element={<Category/>}/>
            <Route path={MANAGE_CLASSES} element={<ManageClasses/>}/>
            <Route path={SECTION} element={<ViewSections/>}/>
            <Route path={CREATE_SECTION} element={<CreateSection/>}/>
            <Route path={CREATE_VOC} element={<CreateVoc/>}/>
            <Route path={CREATE_TECH} element={<CreateTech/>}/>
            <Route path={MANAGE_USERS} element={<ManageUsers/>}/>
            <Route path={DEPARTMENT_MANAGER} element={<DepartmentsManager/>}/>
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