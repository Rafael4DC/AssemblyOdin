import {createBrowserRouter, Route, Routes} from 'react-router-dom';
import Layout from '../components/Layout';
import Home from '../pages/Home/Home';
import * as React from 'react';
import Profile from '../pages/Profile/Profile';
import CurricularUnitManager from '../pages/CurricularUnitManager/CurricularUnitManager';
import CurricularUnitManagerFocused from '../pages/CurricularUnitManagerFocused/CurricularUnitManagerFocused';
import CurricularUnits from '../pages/CurricularUnits/CurricularUnits';
import TeacherClassManager from '../pages/ManageClassses/ManageClasses';
import ManageUsers from '../pages/ManageUsers/ManageUsers';
import VocClass from '../pages/VocClass/VocClass';
import {WebUris} from "./WebUris";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import CURRICULAR_UNITS = WebUris.CURRICULAR_UNITS;
import TEACHER_CLASS_MANAGER = WebUris.TEACHER_CLASS_MANAGER;
import VOC_CLASS = WebUris.VOC_CLASS;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import FOCUSED = WebUris.FOCUSED;
import NOTFOUND = WebUris.NAN;
import CURRICULAR_UNIT_MANAGER = WebUris.CURRICULAR_UNIT_MANAGER;

export function Router() {
    return (
        <Routes>
            <Route path={HOME} element={<Home/>} />
            <Route path={PROFILE} element={<Profile/>} />
            <Route path={CURRICULAR_UNITS} element={<CurricularUnits/>} />
            <Route path={TEACHER_CLASS_MANAGER} element={<TeacherClassManager/>} />
            <Route path={VOC_CLASS} element={<VocClass/>} />
            <Route path={MANAGE_USERS} element={<ManageUsers/>} />
            <Route path={CURRICULAR_UNIT_MANAGER} element={<CurricularUnitManager/>} />
            <Route path={FOCUSED} element={<CurricularUnitManagerFocused/>} />
            <Route path={NOTFOUND} element={<NAN/>} />
        </Routes>
    )
}

function NAN() {
    return (
        <div>
            <h1>This is currently not working</h1>
        </div>
    );
}