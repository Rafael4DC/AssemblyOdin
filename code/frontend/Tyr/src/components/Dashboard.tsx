import * as React from 'react';
import { Link } from 'react-router-dom';

const Dashboard: React.FC = () => {
  return (
    <div>
      <h2><Link to="/">Assembly</Link></h2>
      <ul>
        <li><Link to="/">Curricular Units</Link></li>
        <li><Link to="/profile">Profile</Link></li>

        <h3>Students</h3>
        <li><Link to="/path1">Class Schedule</Link></li>
        <li><Link to="/path2">Voc Class Declarer</Link></li>

        <h3>Teachers</h3>
        <li><Link to="/path3">Manage Classes</Link></li>

        <h3>Admins</h3>
        <li><Link to="/path1">Class Schedule</Link></li>
        <li><Link to="/path2">Voc Class Declarer</Link></li>
        <br />
        <br />
        <li><Link to="/afdfa">404</Link></li>
      </ul>
    </div>
  );
};

export default Dashboard;
