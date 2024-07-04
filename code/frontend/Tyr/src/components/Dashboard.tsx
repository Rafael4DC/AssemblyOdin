import * as React from 'react';
import {Link} from 'react-router-dom';
import "./Dashboard.css";
import {Button} from "react-bootstrap";
import {WebUris} from "../utils/WebUris";
import HOME = WebUris.HOME;
import PROFILE = WebUris.PROFILE;
import CATEGORIES = WebUris.CATEGORIES;
import TEACHER_CLASS_MANAGER = WebUris.MANAGE_CLASSES;
import VOC_CLASS = WebUris.VOC_CLASS;
import CREATE_TECH = WebUris.CREATE_TECH;
import MANAGE_USERS = WebUris.MANAGE_USERS;
import CURRICULAR_UNIT_MANAGER = WebUris.CURRICULAR_UNIT_MANAGER;
import useGetSession from "../hooks/useGetSession";
import CREATE_SECTION = WebUris.CREATE_SECTION;
import SECTION = WebUris.SECTION;

/**
 * Dashboard component
 */
const Dashboard: React.FC = () => {
    const [activeLink, setActiveLink] = React.useState<string>('');
    const {userInfo} = useGetSession();
    const role = userInfo?.role;

    const linkClass = (path: string) =>
        activeLink === path ? "link clickedLink" : "link";

    if(userInfo == null) return null;

    return (
        <div className="text-center">
            <h1>
                <Link to={HOME} className={linkClass(HOME)} onClick={() => setActiveLink(HOME)}>
                    Assembly
                </Link>
            </h1>
            <ul className="list-unstyled">
                <li>
                    <Link to={PROFILE} className={linkClass(PROFILE)} onClick={() => setActiveLink(PROFILE)}>
                        Profile
                    </Link>
                </li>
                <li>
                    <Link to={CATEGORIES} className={linkClass(CATEGORIES)} onClick={() => setActiveLink(CATEGORIES)}>
                        Departments
                    </Link>
                </li>
                {/*{role == 'STUDENT' && (*/}
                    <li>
                        <Link to={VOC_CLASS} className={linkClass(VOC_CLASS)} onClick={() => setActiveLink(VOC_CLASS)}>
                            Create a Voc Class
                        </Link>
                    </li>
                {/*)}*/}
                {role.name == 'TEACHER' && (
                    <>
                        <h3>Teachers</h3>
                        <li>
                            <Link to={TEACHER_CLASS_MANAGER} className={linkClass(TEACHER_CLASS_MANAGER)}
                                  onClick={() => setActiveLink(TEACHER_CLASS_MANAGER)}>
                                Manage Classes
                            </Link>
                        </li>
                        <li>
                            <Link to={SECTION} className={linkClass(SECTION)}
                                  onClick={() => setActiveLink(SECTION)}>
                                Section
                            </Link>
                        </li>
                        <li>
                            <Link to={CREATE_SECTION} className={linkClass(CREATE_SECTION)}
                                  onClick={() => setActiveLink(CREATE_SECTION)}>
                                Create Section
                            </Link>
                        </li>
                        <li>
                            <Link to={CREATE_TECH} className={linkClass(CREATE_TECH)}
                                  onClick={() => setActiveLink(CREATE_TECH)}>
                                Create Tech
                            </Link>
                        </li>
                    </>
                )}
                {role.name == 'ADMIN' && (
                    <>
                        <h3>Admins</h3>
                        <li>
                            <Link to={MANAGE_USERS} className={linkClass(MANAGE_USERS)}
                                  onClick={() => setActiveLink(MANAGE_USERS)}>
                            Manage Users
                            </Link>
                        </li>
                        <li>
                            <Link to={CURRICULAR_UNIT_MANAGER} className={linkClass(CURRICULAR_UNIT_MANAGER)} onClick={() => setActiveLink(CURRICULAR_UNIT_MANAGER)}>
                                Curricular Unit Manager
                            </Link>
                        </li>
                    </>
                )}

                {userInfo == null && (<li>
                    <Button
                        variant="contained"
                        onClick={() => window.location.href = "http://localhost:8080/oauth2/authorization/office365"}
                    >
                        Sign In
                    </Button>
                </li>)}
            </ul>
        </div>
    );
};

export default Dashboard;
