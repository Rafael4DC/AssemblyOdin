import {Button, Table} from 'react-bootstrap';
import * as React from 'react';
import {toDateTimeStr} from '../../../utils/Utils';
import {useTechClassManager} from "../../../hooks/Refactor/useTechClassManager";
import {TechsAttendance} from "../../../hooks/Refactor/useClassManager";
import ManageTechModal from "../../../components/ManageClass/ManageTechModal";

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
        handleSummaryChange,
        handleDateChange,
        setShowEditModal,
    } = useTechClassManager(classes);

    const renderClassRow = (cls: TechsAttendance) => (
        <tr key={cls.tech.id}>
            <td>{toDateTimeStr(cls.tech.started)}</td>
            <td>{cls.tech.section.name}</td>
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
                        <tr>
                            <td colSpan={4}>No classes found</td>
                        </tr>}
                    </tbody>
                </Table>
            </div>
            <ManageTechModal
                showEditModal={showEditModal}
                setShowEditModal={setShowEditModal}
                selectedClass={selectedClass}
                handleSummaryChange={handleSummaryChange}
                handleDateChange={handleDateChange}
                handleSaveClass={handleSaveClass}
                loading={loading}
            />
        </div>
    );
};

export default TechClassManager;