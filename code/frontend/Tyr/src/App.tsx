 import * as React from 'react'
import {
  createBrowserRouter, Link, Outlet, RouterProvider, useParams,
} from 'react-router-dom'
 import Layout from './components/Layout';
 import Home from './pages/Home/Home';
 import { router } from './utils/Router';


export function App() {
  return (
    <RouterProvider router={router} />
  )
}

