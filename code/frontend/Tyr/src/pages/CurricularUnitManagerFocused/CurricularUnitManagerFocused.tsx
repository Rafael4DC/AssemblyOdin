import * as React from 'react';
import { useState } from 'react';
import { useLocation } from 'react-router-dom';

// Extended mock data to include students for each class
const mockCUs = [
  {
    id: 'cu1',
    title: 'CODE VI',
    description: 'Advanced coding class',
    classes: [
      {
        id: 'class1',
        teacher: 'Tomas Santos',
        date: '31/02/1990',
        status: 'Pending',
        students: [
          { name: 'Manuel Tobias', attendance: true },
          { name: 'Jakelino Zé', attendance: false },
          { name: 'Ormonda Luis', attendance: false },
        ],
      },
      // ... other classes
    ],
  },
  // ... other CUs
];

function CurricularUnitManagerFocused() {
  const location = useLocation();
  const { selectedCU } = location.state || {};
  const [selectedClass, setSelectedClass] = useState(selectedCU.classes[0]);

  const handleDeleteClass = (classId: string) => {
    // Implement class deletion logic here
    console.log('Delete class:', classId);
  };

  const handleDeleteAllClasses = () => {
    // Implement logic to delete all classes in a trimester here
    console.log('Delete all classes in trimester');
  };

  return (
    <div className="curricularUnitManager">
      <div className="content">
        <div className="cuDetails">
          <h2>{selectedCU.title}</h2>
          <p>{selectedCU.description}</p>
          <div className="classDetails">
            <h3>Currently Focused Class</h3>
            <div>Teacher: {selectedClass.teacher}</div>
            <div>Date: {selectedClass.date}</div>
            <div>Status: {selectedClass.status}</div>
            <button onClick={() => handleDeleteClass(selectedClass.id)}>Delete Class</button>
            <button onClick={handleDeleteAllClasses}>Delete All Classes in Trimester for this timeslot</button>
            <div className="studentList">
              <h4>Students</h4>
              {selectedClass.students.map((student: {
                name: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal;
                attendance: any;
              }, index: React.Key) => (
                <div key={index}>
                  {student.name} {student.attendance ? '✓' : '✗'}
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CurricularUnitManagerFocused;
