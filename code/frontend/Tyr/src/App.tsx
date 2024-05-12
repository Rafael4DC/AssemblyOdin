import * as React from 'react';
import { RouterProvider } from 'react-router-dom';
import {Router, router} from './utils/Router';
import Layout from "./components/Layout";


export function App() {
  return (
    <Layout>
      <Router />
    </Layout>
  );
}

