import * as React from 'react';
import { useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import TextModal from '../Shared/TextModal';
import { Tech } from '../../model/Tech';



enum FilterOptions {
  Happened = 'Happened',
  ToHappen = 'To Happen',
  Upcoming = 'Upcoming (Next Week)'
}

const maxRows = 5;
const fixedRowHeight = '60px';
const tableMaxHeight = maxRows * parseInt(fixedRowHeight, 10);

const scrollableTableStyle: React.CSSProperties = {
  maxHeight: `${tableMaxHeight}px`,
  overflowY: 'auto',
};

interface TecTableProps {
  techs: Tech[];
}

const TecTable: React.FC<TecTableProps> = ({ techs }) => {
  const [filter, setFilter] = useState(FilterOptions.Happened);
  const [modalShow, setModalShow] = useState(false);
  const [currentSummary, setCurrentSummary] = useState('');

  const renderCourseRow = (tech: Tech) => (
    <tr key={tech.curricularUnit.name + tech.date} style={{ height: fixedRowHeight }}>
      <td>{tech.curricularUnit.name}</td>
      <td>{tech.teacher.username}</td>
      <td>{tech.date.value$kotlinx_datetime}</td>
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
        <tr style={{ height: fixedRowHeight }}>
          <th>Name</th>
          <th>Teacher</th>
          <th>Date</th>
          <th>Attendance</th>
          <th>Summary</th>
        </tr>
        </thead>
        <tbody>
        {techs.map(renderCourseRow)}
        </tbody>
      </Table>
      </div>
      <TextModal
        title="curricularUnit Summary"
        content={currentSummary}
        show={modalShow}
        handleClose={() => setModalShow(false)}
      />
    </div>
  );
}

export default TecTable;