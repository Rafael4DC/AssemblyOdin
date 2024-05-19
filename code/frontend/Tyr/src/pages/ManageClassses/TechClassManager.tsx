import {Button, Table} from 'react-bootstrap';
import * as React from 'react';
import {ManageTechModal} from '../../components/ManageClass/ManageTechModal';
import {toDateTimeStr} from '../../utils/Utils';
import {useTechClassManager} from "../../hooks/useTechClassManager";
import {TechsAttendance} from "../../hooks/useClassManager";

/**
 * Props for the TechClassManager component
 *
 * @param classes - the list of classes
 */
interface TechClassManagerProps {
    classes: TechsAttendance[];
}

/**
 * Component to manage tech classes
 */
const TechClassManager: React.FC<TechClassManagerProps> = ({classes}) => {
    const {
        selectedClass,
        showEditModal,
        loading,
        handleEditClassClick,
        handleSaveClass,
        handleAttendanceChange,
        handleSummaryChange,
        handleDateChange,
        setShowEditModal,
    } = useTechClassManager(classes);


    const renderClassRow = (cls: TechsAttendance) => (
        <tr key={cls.tech.id}>
            <td>{toDateTimeStr(cls.tech.date)}</td>
            <td>{cls.tech.module.name}</td>
            <td>{cls.tech.summary}</td>
            <td>
                <Button variant="outline-primary" onClick={(e) => {
                    e.stopPropagation();
                    handleEditClassClick(cls);
                }}>
                    Manage
                </Button>
            </td>
        </tr>
    );

    return (
        <div>
            <h3>Tech Classes Manager</h3>
            <div style={{maxHeight: '250px', overflowY: 'auto'}}>
                <Table striped bordered hover responsive>
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Topic</th>
                        <th>Summary</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {classes.length ?
                        classes.map(renderClassRow)
                        :
                        <p>No classes found</p>}
                    </tbody>
                </Table>
            </div>
            <ManageTechModal
                showEditModal={showEditModal}
                setShowEditModal={setShowEditModal}
                selectedClass={selectedClass}
                handleAttendanceChange={handleAttendanceChange}
                handleSummaryChange={handleSummaryChange}
                handleDateChange={handleDateChange}
                handleSaveClass={handleSaveClass}
                loading={loading}
            />
        </div>
    );
};

export default TechClassManager;