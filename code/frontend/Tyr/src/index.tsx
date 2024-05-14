import * as React from 'react';
import {createRoot} from 'react-dom/client';
import {App} from './App';
import {BrowserRouter as Router} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

//import { App } from './example-input-and-counter/App';
//import { App } from './example-counter/App';
// import { App } from './example-context/App';
//import {App} from './example-router/App'
//import {App} from './example-periodic-fetcher/App'
//import {App} from './example-context/App'
// import { App } from './example-sse/App'
const root = createRoot(document.getElementById('main-div'));
root.render(
    <Router>
        <App/>
    </Router>
);
