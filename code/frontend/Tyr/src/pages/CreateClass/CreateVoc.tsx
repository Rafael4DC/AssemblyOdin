import * as React from "react";
import {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import useVocs from "../../hooks/useVocs";
import {Voc} from "../../model/Voc";
import useCategories from "../../hooks/useCategories";
import {useNavigate} from "react-router-dom";
import {WebUris} from "../../utils/WebUris";
import PROFILE = WebUris.PROFILE;

/**
 * Page to create a voc class
 */
const CreateVocClass = () => {
    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [vocData, setVocData] = useState<Voc>({
        description: "",
        started: "",
        ended: "",
        approved: false,
        module: {id: 1}
    });

    const {handleSaveVocClass, error} = useVocs();
    const {categories} = useCategories();

    const handleSubmit = async (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        setIsSubmitting(true);
        await handleSaveVocClass(vocData);
        if (error == null) navigate(PROFILE)
        else setIsSubmitting(false);
    };

    const handleCurriUnitChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            module: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            date: e.target.value ? new Date(e.target.value) : new Date(),
        }));
    };

    return (
        <Container>
            <h1>Create VOC Class</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        type="text"
                        required
                        value={vocData.description}
                        onChange={(e) => setVocData({...vocData, description: e.target.value})}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Start</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        required
                        value={vocData.started.toLocaleString()}
                        onChange={(e) =>
                            setVocData((prevVocData) => ({...prevVocData, started: e.target.value}))}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>End</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        required
                        value={vocData.ended.toLocaleString()}
                        onChange={(e) =>
                            setVocData((prevVocData) => ({...prevVocData, ended: e.target.value}))}
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Curricular Unit</Form.Label>
                    <Form.Select
                        required
                        value={vocData.module.id.toString()}
                        onChange={handleCurriUnitChange}
                    >
                        <option>Choose The Curricular Unit</option>
                        {categories && categories.map(unit => (
                            <option key={unit.id} value={unit.id}>{unit.name}</option>
                        ))}
                    </Form.Select>
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Check
                        type="checkbox"
                        label="Approved"
                        checked={vocData.approved}
                        onChange={(e) => setVocData({...vocData, approved: e.target.checked})}
                    />
                </Form.Group>

                <Button variant="primary" type="submit" disabled={isSubmitting}>
                    {isSubmitting ? 'Submitting...' : 'Create VOC Class'}
                </Button>
            </Form>
        </Container>
    );
};

export default CreateVocClass;
