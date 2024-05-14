import * as React from 'react';
import {Router} from './utils/Router';
import Layout from "./components/Layout";


export function App() {
  return (
    <Layout>
      <Router/>
    </Layout>
  );
}

