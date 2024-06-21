import * as React from 'react';
import {createRoot} from 'react-dom/client';
import {App} from './App';
import {BrowserRouter as Router} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import {SessionProvider} from "./session/SessionProvider";

/**
 * Render the application
 */
const root = createRoot(document.getElementById('main-div'));
root.render(
    <Router>
        <SessionProvider>
            <App/>
        </SessionProvider>
    </Router>
);
