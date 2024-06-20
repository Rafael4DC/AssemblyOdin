import * as React from 'react';
import {useState} from 'react';
import {Form, Table} from 'react-bootstrap';
import DetailView from './DetailView';
import useDepartments from "../../hooks/useDepartments";
import {Department} from "../../services/department/models/Department";


enum FilterOptions {
    Legacy = 'Legacy',
    Ongoing = 'Ongoing',
    ToStart = 'Upcoming'
}


function CurricularUnitManager() {
    const {
        departments,
        handleSaveDepartments,
        handleDeleteDepartments,
        error
    } = useDepartments();

    const [filter, setFilter] = useState(FilterOptions.Ongoing);
    const [selectedCourse, setSelectedCourse] = useState<Department | null>(null);

    const saveCourse = (updatedCourse: Department) => {
        handleSaveDepartments(updatedCourse)
        setSelectedCourse(updatedCourse);
        console.log(selectedCourse);
    };

    const renderCourseRow = (curricularUnit: Department) => (
        <tr key={curricularUnit.name} onClick={() => setSelectedCourse(curricularUnit)}>
            <td>{curricularUnit.name}</td>
            {/*<td>{getUniqueTeachers(curricularUnit)}</td>
      <td>{getUniqueWeekdays(curricularUnit)}</td>*/}
        </tr>
    );

    const getUniqueTeachers = (curricularUnit: Department) => {
        /*const teacherSet = new Set(curricularUnit.classes.map(cls => cls.teacher.username));
        return Array.from(teacherSet).join(', ');*/
    };

    const getUniqueWeekdays = (curricularUnit: Department) => {
        /*const dateSet = new Set(
            curricularUnit.classes.map(cls => {
            const date = cls.date.toLocaleString();
            return "Not implemented yet";
          })
        );
        return Array.from(dateSet).join(', ');*/
    };

    return (
        <div>
            <h3>Tec Courses Manager</h3>
            <Form.Select
                aria-label="Filter tech"
                className="mb-3"
                value={filter}
                onChange={(e) => setFilter(e.target.value as FilterOptions)}
            >
                {Object.values(FilterOptions).map(option => (
                    <option key={option} value={option}>{option}</option>
                ))}
            </Form.Select>
            <div style={{maxHeight: '250px', overflowY: 'auto'}}>

                <Table striped bordered hover responsive>

                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Teachers</th>
                        <th>Dates</th>
                    </tr>
                    </thead>
                    {departments ?
                        < tbody>
                        {departments.map(renderCourseRow)}
                        </tbody>
                        : <div className="text-center my-5">
                            <div className="spinner-border" role="status"></div>
                        </div>
                    }
                </Table>
            </div>

            {
                selectedCourse && (
                    <DetailView course={selectedCourse} onSave={saveCourse}/>
                )}
        </div>
    );
}

export default CurricularUnitManager;
