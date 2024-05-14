import { useEffect, useState } from 'react';
import {CurricularUnitService} from "../services/CurricularUnitService";
import {CurricularUnit} from "../model/CurricularUnit";


const CurricularUnits: CurricularUnit[] = [
    {
        id: 1,
        name: 'Code VI',
        description: 'An advanced programming course covering various software engineering principles.',
        classes: [
            {
                id: 101,
                teacher: {username: 'Tomás Santos'},
                //date: new Date('10/10/2024'),
                summary: 'Covered basic syntax and script execution.',
                curricularUnit: null,
                students: [
                    {email:"ManuelTobias@gmail.com", username: 'Manuel Tobias'},
                    {email:"JakelinoZé@gmail.com", username: 'Jakelino Zé'},
                    {email:"MadsaTobias@gmail.com", username: 'MadsaTobias'},
                    {email:"JakafddasfdelinoZé@gmail.com", username: 'Jakafddasfdelino Zé'},
                ],
            },
            {
                id: 102,
                teacher: {username:'Tomás Santos'},
                //date: new Date('15/10/2024'),
                summary: 'Covered basic syntax and script execution.',
                curricularUnit: null,
                students: [
                    {email:"ManuelTobias@gmail.com", username: 'Manuel Tobias'},
                    {email:"JakelinoZé@gmail.com", username: 'Jakelino Zé'},
                ],
            },
        ],
    },
    {
        id: 2,
        name: 'Python Game Maker',
        description: 'Learn game development fundamentals using Python and Pygame.',
        classes: [
            {
                id: 201,
                teacher: {username:'Ana Silva'},
                //date: new Date('11/10/2024'),
                summary: 'Covered basic syntax and script execution.',
                curricularUnit: null,
                students: [
                    {email:"OrmondaLuis@gmail.com", username: 'Ormonda Luis'},
                    {email:"AlinaCosta@gmail.com", username: 'Alina Costa'}
                ],
            },
        ],
    },
];

const useCurricularUnits = () => {
    const [curricularUnits, setCurricularUnits] = useState<CurricularUnit[] | null>(null);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        CurricularUnitService.getAll()
            .then(data => {
                setCurricularUnits(CurricularUnits);
            })
            .catch(err => {
                setError(err);
            });
    }, []);

    return {
        curricularUnits,
        error,
        handleSaveCurricularUnit: async (curricularUnit: CurricularUnit) => {
            setError(null);
            try {
                if (curricularUnit.id) {
                    return await CurricularUnitService.update(curricularUnit);
                } else {
                    return await CurricularUnitService.save(curricularUnit);
                }
            } catch (err) {
                setError(err);
            }
        },
        handleDeleteCurricularUnit: async (id: number) => {
            setError(null);
            try {
                await CurricularUnitService.delete(id);
            } catch (err) {
                setError(err);
            }
        }
    };
};

export default useCurricularUnits;