import * as React from 'react';
import {createRoot} from 'react-dom/client';
import {App} from './App';
import {BrowserRouter as Router} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import {SessionProvider} from "./session/SessionProvider";
import {createTheme, ThemeProvider} from "@mui/material";
import CssBaseline from "@mui/material/CssBaseline";

declare module '@mui/material/styles' {
    interface Palette {
        azulEscuro: Palette['primary'];
        magenta: Palette['primary'];
        cyan: Palette['primary'];
        amarelo: Palette['primary'];
        verde: Palette['primary'];
        laranja: Palette['primary'];
        roxo: Palette['primary'];
    }

    interface PaletteOptions {
        azulEscuro: PaletteOptions['primary'];
        magenta: PaletteOptions['primary'];
        cyan: PaletteOptions['primary'];
        amarelo: PaletteOptions['primary'];
        verde: PaletteOptions['primary'];
        laranja: PaletteOptions['primary'];
        roxo: PaletteOptions['primary'];
    }
}

/**
 * Render the application
 */
const root = createRoot(document.getElementById('main-div'));
const theme = createTheme({
    palette: {
        azulEscuro: {
            main: '#100935',
        },
        magenta: {
            main: '#c12381',
        },
        cyan: {
            main: '#00A3E0',
        },
        amarelo: {
            main: '#FFC837',
        },
        verde: {
            main: '#2CC84D',
        },
        laranja: {
            main: '#FA8728',
        },
        roxo: {
            main: '#502350',
        },
        background: {
            default: '#1b1348',
        },
        text: {
            primary: '#fff',
        },
    },
    typography: {
        fontFamily: 'Poppins, sans-serif',
        h1: {
            fontFamily: 'Poppins, sans-serif',
            fontWeight: 600,
        },
        h2: {
            fontFamily: 'Poppins, sans-serif',
            fontWeight: 600,
        },
        h3: {
            fontFamily: 'Poppins, sans-serif',
            fontWeight: 600,
        },
        h4: {
            fontFamily: 'Poppins, sans-serif',
            fontWeight: 600,
        },
        h5: {
            fontFamily: 'Poppins, sans-serif',
            fontWeight: 600,
        },
        h6: {
            fontFamily: 'Poppins, sans-serif',
            fontWeight: 600,
        },
        body1: {
            fontWeight: 400,
        },
        body2: {
            fontWeight: 300,
        },
    },
});

root.render(
    <Router>
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <SessionProvider>
                <App/>
            </SessionProvider>
        </ThemeProvider>
    </Router>
);
