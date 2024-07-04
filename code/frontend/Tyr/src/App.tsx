import * as React from 'react';
import {AppRoutes} from './utils/Router';
import Layout from "./components/Layout";


/**
 * Application component
 */
export function App() {
    return (
        <Layout>
            <AppRoutes/>
        </Layout>
    );
}

