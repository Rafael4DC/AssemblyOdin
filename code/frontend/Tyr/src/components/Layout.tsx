import * as React from 'react';
import Dashboard from './Dashboard';
import './Layout.css';

/**
 * Props for the Layout component
 *
 * @param children - the children components
 */
interface LayoutProps {
    children: React.ReactNode;
}

/**
 * Layout for the dashboard
 */
const Layout = ({children}: LayoutProps) => {
    return (
        <div className="dashboard-container">
            <div className="sidebar">
                <Dashboard/>
            </div>
            <div className="main-content">
                {children}
            </div>
        </div>
    );
};

export default Layout;