import * as React from "react";
import {useEffect, useState} from "react";
import { Container, Table } from 'react-bootstrap';
import {SectionService} from "../../services/section/SectionService";
import {Section} from "../../services/section/models/Section";

/**
 * Page to view all sections and their users
 */
const ViewSections = () => {
    const [sections, setSections] = useState<Section[]>([]);

    useEffect(() => {
        const fetchSections = async () => {
            try {
                const data = await SectionService.getAll();
                setSections(data.sections);
            } catch (error) {
                console.error('Error fetching sections:', error);
            }
        };

        fetchSections();
    }, []);

    return (
        <Container>
            <h1>All Sections</h1>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>Section Name</th>
                    <th>Summary</th>
                    <th>Module</th>
                    <th>Students</th>
                </tr>
                </thead>
                <tbody>
                {sections.map(section => (
                    <tr key={section.id}>
                        <td>{section.name}</td>
                        <td>{section.summary}</td>
                        <td>{section.module?.name}</td>
                        <td>
                            {section.students.map(student => (
                                <div key={student.id}>{student.username}</div>
                            ))}
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default ViewSections;
