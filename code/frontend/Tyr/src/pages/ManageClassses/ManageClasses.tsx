import {Form} from 'react-bootstrap';
import * as React from 'react';
import TechClassManager from './TechClassManager';
import VocClassManager from './VocClassManager';
import {ClassFilterOptions, useClassManager} from '../../hooks/useClassManager';

/**
 * Page to manage classes
 */
const TeacherClassManager: React.FC = () => {
    const {techClasses, vocClasses, filter, setFilter} = useClassManager();

    return (
        <div>
            <h3>My Classes Manager</h3>
            <Form.Select
                aria-label="Filter classes"
                className="mb-3"
                value={filter}
                onChange={(e) => setFilter(e.target.value as ClassFilterOptions)}
            >
                {Object.values(ClassFilterOptions).map(option => (
                    <option key={option} value={option}>{option}</option>
                ))}
            </Form.Select>
            <TechClassManager classes={techClasses}/>
            <VocClassManager classes={vocClasses}/>
        </div>
    );
};

export default TeacherClassManager;
