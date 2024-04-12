import * as React from 'react';
import { useState } from 'react';
import './CurricularUnitManager.css';
import { Outlet, useNavigate } from 'react-router-dom';


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
    ],
  },
  {
    id: 'cu2',
    title: 'Python Game Maker',
    description: 'Creating games with Python',
    classes: [
      {
        teacher: 'Jane Smith',
        date: '2022-09-05',
      },
      {
        teacher: 'John Smith',
        date: '2022-10-05',
      },
    ],
  },
];

function CurricularUnitManager() {
  const [selectedCU, setSelectedCU] = useState(null);
  const navigate = useNavigate(); // Hook for navigation

  const handleSelectCU = (cu: {
    id: string;
    title: string;
    description: string;
    classes: { teacher: string; date: string; }[];
  }) => {
    setSelectedCU(cu);
    navigate(`/CurricularUnitManager/Focused`, { state: { selectedCU: cu } });
  };

  const handleRemoveClass = (classToRemove: { date: any; }) => {
    if (selectedCU) {
      const updatedClasses = selectedCU.classes.filter((c: { date: any; }) => c.date !== classToRemove.date);
      setSelectedCU({ ...selectedCU, classes: updatedClasses });
    }
  };

  return (
    <div className="curricularUnitManager">
      <div className="content">
        <div className="searchSection">
          <input type="text" placeholder="Search CUs" />
          <div className="cuList">
            {mockCUs.map((cu) => (
              <div key={cu.id} onClick={() => handleSelectCU(cu)}>
                {cu.title}
              </div>
            ))}
          </div>
          <button>Add Class</button>
        </div>
        <div className="cuDetails">
          {selectedCU ? (
            <>
              <h2>{selectedCU.title} <span>Edit</span></h2>
              <p>{selectedCU.description} <span>Edit</span></p>
              <div className="scheduledClasses">
                <h3>Currently Scheduled Classes</h3>
                {selectedCU.classes.map((c: { date: any; teacher?: any; }) => (
                  <div key={c.date}>
                    <div>Teacher: {c.teacher}</div>
                    <div>Date: {c.date}</div>
                    <button onClick={() => handleRemoveClass(c)}>Remove This Class</button>
                  </div>
                ))}
              </div>
            </>
          ) : (
            <p>Select a class to see details</p>
          )}
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default CurricularUnitManager;
