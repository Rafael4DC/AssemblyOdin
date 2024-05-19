import * as React from 'react';
import {useState} from 'react';
import {Table} from 'react-bootstrap';
import {Voc} from "../../model/Voc";
import {getDuration} from "../../utils/Utils";
import {FilterOptions, fixedRowHeight, scrollableTableStyle} from "./TecTable";

/**
 * Props for the VocTable component
 *
 * @param courses - the voc courses
 */
interface VocTableProps {
    courses: Voc[];
}

/**
 * Table to show the voc courses
 */
const VocTable: React.FC<VocTableProps> = ({courses}) => {
    const [filter, setFilter] = useState(FilterOptions.Happened);

    const renderCourseRow = (course: Voc) => (
        <tr key={course.description + course.started} style={{height: fixedRowHeight}}>
            <td>{course.description}</td>
            <td>{getDuration(course.started, course.ended)}</td>
            <td>{course.approved ? '✓' : '—'}</td>
        </tr>
    );

    return (
        <div>
            <h3>Voc Courses</h3>
            <select
                className="form-select mb-3"
                value={filter}
                onChange={(e) => setFilter(e.target.value as FilterOptions)}
            >
                {Object.values(FilterOptions).map((option) => (
                    <option key={option} value={option}>
                        {option}
                    </option>
                ))}
            </select>
            <div style={scrollableTableStyle}>
                <Table striped bordered hover responsive>
                    <thead>
                    <tr style={{height: fixedRowHeight}}>
                        <th>Name</th>
                        <th>Length</th>
                        <th>Approved</th>
                    </tr>
                    </thead>
                    <tbody>
                    {courses.map(renderCourseRow)}
                    </tbody>
                </Table>
            </div>
        </div>
    );
}

export default VocTable;
