import {Button, Table} from 'react-bootstrap';
import * as React from 'react';
import {useState} from 'react';
import {Voc} from '../../../services/voc/models/Voc';
import {VocService} from "../../../services/voc/VocService";
import {getDuration, toDateTimeStr} from "../../../utils/Utils";

/**
 * Props for the VocClassManager component
 *
 * @param classes - the list of classes
 */
interface VocClassManagerProps {
    classes: Voc[];
}

/**
 * Component to manage voc classes
 */
const VocClassManager: React.FC<VocClassManagerProps> = ({classes}) => {
    const [selectedVoc, setSelectedVoc] = useState<Voc | null>(null);
    const [showApprovalModal, setShowApprovalModal] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleApproveChange = (approved: boolean) => {
        if (selectedVoc) {
            setSelectedVoc({...selectedVoc, approved});
        }
    };

    const handleSaveVoc = async () => {
        if (selectedVoc) {
            setLoading(true);
            try {
                await VocService.update({
                    id: selectedVoc.id,
                    description: selectedVoc.description,
                    approved: selectedVoc.approved,
                    section: selectedVoc.section?.id,
                    started: selectedVoc.started,
                    ended: selectedVoc.ended,
                });
                setShowApprovalModal(false);
            } catch (error) {
                console.error('Error saving Voc:', error);
            } finally {
                setLoading(false);
            }
        }
    };

    const renderVocRow = (voc: Voc) => (
        <tr key={voc.id}>
            <td>{voc.description}</td>
            <td>{voc.section?.name}</td>
            <td>{toDateTimeStr(voc.started)}</td>
            <td>{voc.user.username}</td>
            <td>{getDuration(voc.started, voc.ended)}</td>
            <td>{voc.approved ? '✓' : '—'}</td>
            <td>
                <Button
                    variant="primary"
                    onClick={() => {
                        setSelectedVoc(voc);
                        setShowApprovalModal(true);
                    }}
                >
                    Manage
                </Button>
            </td>
        </tr>
    );

    return (
        <div>
            <h3>Voc Classes Manager</h3>
            <div style={{maxHeight: '250px', overflowY: 'auto'}}>
                <Table striped bordered hover responsive>
                    <thead>
                    <tr>
                        <th>Description</th>
                        <th>Section</th>
                        <th>Start Time</th>
                        <th>Student</th>
                        <th>Duration</th>
                        <th>Approved</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {classes.length ?
                        classes.map(renderVocRow)
                        :
                        <p>No classes found</p>}
                    </tbody>
                </Table>
            </div>
            {/*<VocApprovalModal
                show={showApprovalModal}
                onHide={() => setShowApprovalModal(false)}
                voc={selectedVoc}
                onApproveChange={handleApproveChange}
                onSave={handleSaveVoc}
                loading={loading}
            />*/}
        </div>
    );
};

export default VocClassManager;
