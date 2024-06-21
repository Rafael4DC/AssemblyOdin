import * as React from 'react';
import {AppRoutes} from './utils/Router';
import Dashboard from "./components/Dashboard";


/**
 * Application component
 */
export function App() {
    return (
        <Dashboard>
            <AppRoutes/>
        </Dashboard>
    );
}

