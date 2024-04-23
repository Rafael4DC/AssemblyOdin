import * as React from 'react';
import { Outlet } from 'react-router-dom';
import Dashboard from './Dashboard';
import './Layout.css'; // Make sure the path is correct for your project structure

const Layout = () => {
  return (
    <div className="dashboard-container">
      <div className="sidebar">
        <Dashboard />
      </div>
      <div className="main-content">
        <Outlet />
      </div>
    </div>
  );
};

export default Layout;