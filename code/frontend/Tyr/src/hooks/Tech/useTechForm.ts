import * as React from 'react';
import {ChangeEvent, useState} from 'react';
import {Tech} from "../../services/tech/models/Tech";

const useTechForm = (initTech?: Tech) => {
    const [selectedTech, setSelectedTech] = useState<Tech | null>(initTech || null);

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setSelectedTech((prevTech) => ({
            ...prevTech,
            [name]: value,
        }));
    };

    const handleSectionChange = (e: ChangeEvent<HTMLInputElement>) => {
        setSelectedTech((prevTech) => ({
            ...prevTech,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: ChangeEvent<HTMLInputElement>) => {
        const date = e.target.value;
        setSelectedTech((prevTech) => ({
            ...prevTech,
            started: `${date}T${prevTech.started.split('T')[1]}`,
            ended: `${date}T${prevTech.ended.split('T')[1]}`,
        }));
    };

    const handleTimeChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, key: 'started' | 'ended') => {
        const time = e.target.value;
        setSelectedTech((prevTech) => ({
            ...prevTech,
            [key]: `${prevTech[key].split('T')[0]}T${time}`,
        }));
    };

    const handleMultiChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const {name, value} = e.target;
        setSelectedTech(prevTech => ({
            ...prevTech,
            [name]: value
        }));
    };


    return {
        selectedTech,
        setSelectedTech,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleMultiChange
    };
};

export default useTechForm;
