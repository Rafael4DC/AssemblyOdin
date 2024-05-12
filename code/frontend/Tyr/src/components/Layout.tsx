import * as React from 'react';
import Dashboard from './Dashboard';
import './Layout.css'; // Make sure the path is correct for your project structure

interface LayoutProps {
    children: React.ReactNode;
}

const Layout = ({children}: LayoutProps) => {
  return (
    <div className="dashboard-container">
      <div className="sidebar">
        <Dashboard />
      </div>
      <div className="main-content">
          {children}
      </div>
    </div>
  );
};

export default Layout;