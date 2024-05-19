import {Route, Routes} from 'react-router-dom';
import Home from '../pages/Home/Home';
import * as React from 'react';
import Profile from '../pages/Profile/Profile';
import CurricularUnitManager from '../pages/CurricularUnitManager/CurricularUnitManager';
import CurricularUnitManagerFocused from '../pages/CurricularUnitManagerFocused/CurricularUnitManagerFocused';
import ManageClasses from '../pages/ManageClassses/ManageClasses';
import ManageUsers from '../pages/ManageUsers/ManageUsers';
import VocClass from '../pages/CreateClass/CreateVoc';
import {WebUris} from "./WebUris";
import Category from "../pages/Categories/Category";
import CreateTech from "../pages/CreateClass/CreateTech";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import CATEGORIES = WebUris.CATEGORIES;
import MANAGE_CLASSES = WebUris.MANAGE_CLASSES;
import VOC_CLASS = WebUris.VOC_CLASS;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import FOCUSED = WebUris.FOCUSED;
import NOTFOUND = WebUris.NAN;
import CURRICULAR_UNIT_MANAGER = WebUris.CURRICULAR_UNIT_MANAGER;
import CREATE_TECH = WebUris.CREATE_TECH;

/**
 * Router component
 */
export function Router() {
    return (
        <Routes>
            <Route path={HOME} element={<Home/>}/>
            <Route path={PROFILE} element={<Profile/>}/>
            <Route path={CATEGORIES} element={<Category/>}/>
            <Route path={MANAGE_CLASSES} element={<ManageClasses/>}/>
            <Route path={VOC_CLASS} element={<VocClass/>}/>
            <Route path={CREATE_TECH} element={<CreateTech/>}/>
            <Route path={MANAGE_USERS} element={<ManageUsers/>}/>
            <Route path={CURRICULAR_UNIT_MANAGER} element={<CurricularUnitManager/>}/>
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