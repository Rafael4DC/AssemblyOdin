import * as React from 'react';
import {useState} from 'react';
import {Button, Table} from 'react-bootstrap';
import TextModal from '../Shared/TextModal';
import {Tech} from '../../model/Tech';
import {filterCourses, FilterOptions, toDateTimeStr} from "../../utils/Utils";

export const maxRows = 5;
export const fixedRowHeight = '60px';
export const tableMaxHeight = maxRows * parseInt(fixedRowHeight, 10);

export const scrollableTableStyle: React.CSSProperties = {
    maxHeight: `${tableMaxHeight}px`,
    overflowY: 'auto',
};

/**
 * Props for the TecTable component
 *
 * @param techs - the tech classes
 */
interface TecTableProps {
    techs: Tech[];
}

/**
 * Table to show the tech classes
 */
const TecTable: React.FC<TecTableProps> = ({techs}) => {
    const [filter, setFilter] = useState(FilterOptions.Upcoming);
    const [modalShow, setModalShow] = useState(false);
    const [currentSummary, setCurrentSummary] = useState('');

    const filteredTechs = filterCourses(techs, filter, 'date');

    const renderCourseRow = (tech: Tech) => (
        <tr key={tech.module.name + tech.date} style={{height: fixedRowHeight}}>
            <td>{tech.module.name}</td>
            <td>{tech.teacher.username}</td>
            <td>{toDateTimeStr(tech.date)}</td>
            <td>{tech.attendance ? '✓' : '—'}</td>
            <td>
                <Button variant="link" onClick={() => {
                    setCurrentSummary(tech.summary);
                    setModalShow(true);
                }}>
                    View Summary
                </Button>
            </td>
        </tr>
    );

    return (
        <div>
            <h3>Tec Courses</h3>
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
                        <th>Teacher</th>
                        <th>Date</th>
                        <th>Attendance</th>
                        <th>Summary</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredTechs.map(renderCourseRow)}
                    </tbody>
                </Table>
            </div>
            <TextModal
                title="Summary"
                content={currentSummary}
                show={modalShow}
                handleClose={() => setModalShow(false)}
            />
        </div>
    );
}


export default TecTable;