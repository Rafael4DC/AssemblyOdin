import { createBrowserRouter } from 'react-router-dom';
import Layout from '../components/Layout';
import Home from '../pages/Home/Home';
import * as React from 'react';
import Profile from '../pages/Profile/Profile';
import CurricularUnitManager from '../pages/CurricularUnitManager/CurricularUnitManager';
import CurricularUnitManagerFocused from '../pages/CurricularUnitManagerFocused/CurricularUnitManagerFocused';

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
      <h1>This is a WIP</h1>
    </div>
  );
}