// Dashboard.jsx or Dashboard.js
import * as React from 'react';
import './Dashboard.css'; // Assuming you have CSS for layout

const Dashboard = ({ children }: {children: React.ReactNode} ) => {
  return (
    <div className="dashboard-container">
      <div className="sidebar">
        {/* Sidebar content */}
        <ul>
          <li>Dashboard</li>
          <li>Analytics</li>
          <li>Reports</li>
          <li>Settings</li>
        </ul>
      </div>
      <div className="main-content">
        {/* Main content */}
        {children}
      </div>
    </div>
  );
};

export default Dashboard;
