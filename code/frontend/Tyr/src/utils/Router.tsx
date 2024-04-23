import { createBrowserRouter } from 'react-router-dom';
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

export const router = createBrowserRouter([
  {
    'path': '/', 'element': <Layout />, 'children': [
      {
        'path': '/',
        'element': <Home />,
      },
      {
        'path': '/profile',
        'element': <Profile />,
      },
      {
        'path': '/CU',
        'element': <CurricularUnits />,
      },
      {
        'path': '/TeacherClassManager',
        'element': <TeacherClassManager />,
      },
      {
        'path': '/VocClass',
        'element': <VocClass />,
      },
      {
        'path': '/ManageUsers',
        'element': <ManageUsers />,
      },
      {
        'path': '/CurricularUnitManager',
        'element': <CurricularUnitManager />,
        'children': [
          {
            'path': 'Focused',
            'element': <CurricularUnitManagerFocused />,
          },
        ],
      },
      {
        'path': '/NAN',
        'element': <NAN />,
      },
    ],
  },
]);


function NAN() {
  return (
    <div>
      <h1>This is currently not working</h1>
    </div>
  );
}