import {ChangeEvent, useState} from 'react';
import {Tech} from "../../services/tech/models/Tech";

/**
 * Hook to manage the tech form
 *
 * @param initTech the initial tech
 * @returns the state and functions to manage the tech form
 */
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

    return {
        selectedTech,
        setSelectedTech,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange
    };
};

export default useTechForm;
