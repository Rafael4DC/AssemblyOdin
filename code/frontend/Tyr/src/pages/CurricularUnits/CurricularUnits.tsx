import * as React from 'react';
import {useState} from 'react';
import {Table} from 'react-bootstrap';
import {CurricularUnit} from '../../model/CurricularUnit';
import useCurricularUnits from "../../hooks/useCurricularUnits";


const classes = [
    {
        id: 1,
        teacher: {username: 'Tomás Santos'},
        date: new Date('10/10/2024'),
        summary: 'Covered basic syntax and script execution.'
    },
    {
        id: 2,
        teacher: {username: 'Tomás Santos'},
        date: new Date('5/10/2024'),
        summary: 'Covered basic syntax and script execution.'
    },
]

enum FilterOptions {
    Legacy = 'Legacy',
    Ongoing = 'Ongoing',
    ToStart = 'Upcoming'
}

interface DetailViewProps {
    course: CurricularUnit;
}

const DetailView: React.FC<DetailViewProps> = ({course}) => {
    if (!course) return null;

    return (
        <div style={{border: '1px solid grey', padding: '20px', marginTop: '20px'}}>
            <h4>{course.name}</h4>
            <p>{course.description}</p>
            <h5>Currently Scheduled Classes</h5>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Teacher</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {classes.map((cls, index) => (
                    <tr key={index}>
                        <td>{cls.teacher.username}</td>
                        <td>{cls.date.toLocaleDateString()}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </div>
    );
};

function CurricularUnits() {
    const {curricularUnits, error} = useCurricularUnits();

    const [filter, setFilter] = useState(FilterOptions.Ongoing);
    const [selectedCourse, setSelectedCourse] = useState<CurricularUnit | null>(null);

    const getUniqueTeachers = (course: CurricularUnit) => {
        const teacherSet = new Set(/*course.*/classes.map(cls => cls.teacher.username));
        return Array.from(teacherSet).join(', ');
    };

    const getUniqueWeekdays = (course: CurricularUnit) => {
        const dateSet = new Set(
            /*course.*/classes.map(cls => {
                const date = new Date(cls.date);
                return date.toLocaleString('en-US', {weekday: 'long'});
            })
        );
        return Array.from(dateSet).join(', ');
    };

    const renderCourseRow = (curricularUnit: CurricularUnit, index: number) => (
        <tr key={index} onClick={() => setSelectedCourse(curricularUnit)}>
            <td>{curricularUnit.name}</td>
            <td>{getUniqueTeachers(curricularUnit)}</td>
            <td>{getUniqueWeekdays(curricularUnit)}</td>
        </tr>
    );

    return (
        <div>
            <h3>Curricular Units</h3>
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

            <Table striped bordered hover responsive>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Teachers</th>
                    <th>Dates</th>
                </tr>
                </thead>
                {curricularUnits ?
                    < tbody>
                    {curricularUnits.map(renderCourseRow)}
                    </tbody>
                    : <div className="text-center my-5">
                        <div className="spinner-border" role="status"></div>
                    </div>
                }
            </Table>

            <DetailView course={selectedCourse}/>
        </div>
    );
}

export default CurricularUnits;
