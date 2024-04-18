import * as React from 'react';
import { Link } from 'react-router-dom';
import "./Dashboard.css";

const Dashboard: React.FC = () => {
  const [activeLink, setActiveLink] = React.useState<string>('');

  // This logic is awfull, fix later its late and im tired TODO()
  const linkClass = (path: string) =>
    activeLink === path ? "link clickedLink" : "link";

  return (
    <div className="text-center">
      <h1><Link to="/" className={linkClass('/')} onClick={() => setActiveLink('/')}>Assembly</Link></h1>
      <ul className="list-unstyled">

        <li><Link to="/profile" className={linkClass('/profile')}
                  onClick={() => setActiveLink('/profile')}>Profile</Link></li>
        <li><Link to="/CU" className={linkClass('/CU')} onClick={() => setActiveLink('/CU')}>Curricular Units</Link>
        </li>

        <h3>Teachers</h3>
        <li><Link to="/TeacherClassManager" className={linkClass('/TeacherClassManager')}
                  onClick={() => setActiveLink('/TeacherClassManager')}>Manage Classes</Link></li>
        <li><Link to="/VocClass" className={linkClass('/VocClass')} onClick={() => setActiveLink('/VocClass')}>Create a
          Voc Class</Link></li>

        <h3>Admins</h3>
        <li><Link to="/ManageUsers" className={linkClass('/ManageUsers')} onClick={() => setActiveLink('/ManageUsers')}>Manage
          Users</Link></li>
        <li><Link to="/CurricularUnitManager" className={linkClass('/CurricularUnitManager')}
                  onClick={() => setActiveLink('/CurricularUnitManager')}>Curricular Unit Manager</Link></li>

        <li><Link to="/404" className={linkClass('/404')} onClick={() => setActiveLink('/404')}>404</Link></li>
      </ul>
    </div>
  );
};

export default Dashboard;
