import * as React from 'react';
import {ChangeEvent, useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import useSections from "../Section/useSections";
import useStudents from "../User/useStudents";
import useVocs from "../Refactor/useVocs";
import {Voc} from "../../services/voc/models/Voc";
import useUserInfo from "../User/useUserInfo";
import {Success} from "../../services/_utils/Either";
import {WebUris} from "../../utils/WebUris";
import PROFILE = WebUris.PROFILE;

type CreateVocClassState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useCreateVoc = () => {
    const navigate = useNavigate();
    const [state, setState] = useState<CreateVocClassState>({type: 'loading'});
    const {userInfo} = useUserInfo();
    const role = userInfo?.role.name;

    const [vocData, setVocData] = useState<Voc>({
        description: "",
        started: new Date().toISOString().split('T')[0] + 'T10:00',
        ended: new Date().toISOString().split('T')[0] + 'T11:00',
        user: {id: 0},
        approved: false,
        section: {id: 0},
    });

    const {handleSaveVocClass, error} = useVocs();
    const {sections} = useSections();
    const {students} = useStudents();

    useEffect(() => {
        if (state.type === 'loading' && sections !== null && students !== null) {
            setState({type: 'success', message: ''});
        } else if (error) {
            setState({type: 'error', message: handleError(error)});
        }
    }, [error, state.type, sections, students]);

    const handleError = (error: any) => {
        if (error instanceof Error) {
            return error.message;
        } else {
            return 'An unexpected error occurred';
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setState({type: 'loading'});

        const res = await handleSaveVocClass(vocData);
        if (res instanceof Success) {
            navigate(PROFILE);
        } else {
            const errorMessage = handleError(res);
            setState({type: 'error', message: errorMessage});
        }
    };

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setVocData((prevVocData) => ({
            ...prevVocData,
            [name]: value,
        }));
    };


    const handleSectionChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            section: {id: Number(e.target.value)},
        }));
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        const date = e.target.value;
        setVocData((prevVocData) => ({
            ...prevVocData,
            started: `${date}T${prevVocData.started.split('T')[1]}`,
            ended: `${date}T${prevVocData.ended.split('T')[1]}`,
        }));
    };

    const handleTimeChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>, key: "started" | "ended") => {
        const time = e.target.value;
        setVocData((prevVocData) => ({
            ...prevVocData,
            [key]: `${prevVocData[key].split('T')[0]}T${time}`,
        }));
    };

    const handleStudentChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            user: {id: Number(e.target.value)},
        }));
    };

    return {
        vocData,
        setVocData,
        state,
        role,
        sections,
        students,
        handleSubmit,
        handleInputChange,
        handleSectionChange,
        handleDateChange,
        handleTimeChange,
        handleStudentChange,
    };
};

export default useCreateVoc;
