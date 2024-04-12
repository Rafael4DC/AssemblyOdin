import * as React from 'react';
import { Link } from 'react-router-dom';
import "./Dashboard.css"; // Ensure this path is correct

const Dashboard: React.FC = () => {
  const [activeLink, setActiveLink] = React.useState<string>('');

  // This logic is awfull, fix later its late and im tired TODO()
  const linkClass = (path: string) =>
    activeLink === path ? "link clickedLink" : "link";

  return (
    <div className="text-center">
      <h1><Link to="/" className={linkClass('/')} onClick={() => setActiveLink('/')}>Assembly</Link></h1>
      <ul className="list-unstyled">
        <li><Link to="/NAN" className={linkClass('/NAN')} onClick={() => setActiveLink('/NAN')}>Curricular Units</Link></li>
        <li><Link to="/profile" className={linkClass('/profile')} onClick={() => setActiveLink('/profile')}>Profile</Link></li>

        <h3>Students</h3>
        <li><Link to="/NAN" className={linkClass('/NAN-students')} onClick={() => setActiveLink('/NAN-students')}>Class Schedule</Link></li>
        <li><Link to="/NAN" className={linkClass('/NAN-voc')} onClick={() => setActiveLink('/NAN-voc')}>Voc Class Declarer</Link></li>

        <h3>Teachers</h3>
        <li><Link to="/NAN" className={linkClass('/NAN-teachers')} onClick={() => setActiveLink('/NAN-teachers')}>Manage Classes</Link></li>

        <h3>Admins</h3>
        <li><Link to="/NAN" className={linkClass('/NAN-admins')} onClick={() => setActiveLink('/NAN-admins')}>Manage Users</Link></li>
        <li><Link to="/CurricularUnitManager" className={linkClass('/CurricularUnitManager')} onClick={() => setActiveLink('/CurricularUnitManager')}>Curricular Unit Manager</Link></li>

        <li><Link to="/404" className={linkClass('/404')} onClick={() => setActiveLink('/404')}>404</Link></li>
      </ul>
    </div>
  );
};

export default Dashboard;
