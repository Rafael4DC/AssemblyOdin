import {ChangeEvent, useState} from 'react';
import {Voc} from "../../services/voc/models/Voc";

const useVocForm = (initVoc?: Voc) => {
    const [selectedVoc, setSelectedVoc] = useState<Voc | null>(initVoc || null);

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setSelectedVoc((prevVocData) => ({
            ...prevVocData,
            [name]: value,
        }));
    };

    const handleSectionChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedVoc((prevVocData) => ({
            ...prevVocData,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: ChangeEvent<HTMLInputElement>) => {
        const date = e.target.value;
        setSelectedVoc((prevVocData) => ({
            ...prevVocData,
            started: `${date}T${prevVocData.started.split('T')[1]}`,
            ended: `${date}T${prevVocData.ended.split('T')[1]}`,
        }));
    };

    const handleTimeChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: 'started' | 'ended') => {
        const time = e.target.value;
        setSelectedVoc((prevVocData) => ({
            ...prevVocData,
            [key]: `${prevVocData[key].split('T')[0]}T${time}`,
        }));
    };

    const handleStudentChange = (e: ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setSelectedVoc((prevVocData) => ({
            ...prevVocData,
            user: {id: Number(e.target.value)},
        }));
    };

    return {
        selectedVoc,
        setSelectedVoc,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleStudentChange
    };
};

export default useVocForm;
