import {Route, Routes} from 'react-router-dom';
import Home from '../pages/Home/Home';
import * as React from 'react';
import Profile from '../pages/Profile/Profile';
import CurricularUnitManagerFocused from '../pages/CurricularUnitManagerFocused/CurricularUnitManagerFocused';
import ManageClasses from '../pages/ManageClassses/ManageClasses';
import ManageUsers from '../pages/ManageUsers/ManageUsers';
import {WebUris} from "./WebUris";
import Category from "../pages/Departments/Department";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import CATEGORIES = WebUris.CATEGORIES;
import MANAGE_CLASSES = WebUris.MANAGE_CLASSES;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import FOCUSED = WebUris.FOCUSED;
import NOTFOUND = WebUris.NAN;
import CREATE_SECTION = WebUris.CREATE_SECTION;
import CURRICULAR_UNIT_MANAGER = WebUris.CURRICULAR_UNIT_MANAGER;
import SECTION = WebUris.SECTION;
import DepartmentsManager from "../pages/DepartmentsManager/DepartmentsManager";
import VOC_CLASS = WebUris.VOC_CLASS;
import CreateVoc from "../pages/CreateClass/CreateVoc";
import CreateTech from "../pages/CreateClass/CreateTech";
import CREATE_TECH = WebUris.CREATE_TECH;
import CreateSection from "../pages/Section/CreateSection";
import ViewSections from "../pages/Section/ViewSections";

/**
 * Router component
 */
export function AppRoutes() {
    return (
        <Routes>
            <Route path={HOME} element={<Home/>}/>
            <Route path={PROFILE} element={<Profile/>}/>
            <Route path={CATEGORIES} element={<Category/>}/>
            <Route path={MANAGE_CLASSES} element={<ManageClasses/>}/>
            <Route path={SECTION} element={<ViewSections/>}/>
            <Route path={CREATE_SECTION} element={<CreateSection/>}/>
            <Route path={VOC_CLASS} element={<CreateVoc/>}/>
            <Route path={CREATE_TECH} element={<CreateTech/>}/>
            <Route path={MANAGE_USERS} element={<ManageUsers/>}/>
            <Route path={CURRICULAR_UNIT_MANAGER} element={<DepartmentsManager/>}/>
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