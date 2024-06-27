import * as React from 'react';
import { useNavigate } from 'react-router-dom';
import PROFILE = WebUris.PROFILE;
import useSections from "./useSections";
import useStudents from "./useStudents";
import useVocs from "./Refactor/useVocs";
import {Voc} from "../services/voc/models/Voc";
import useUserInfo from "./useUserInfo";
import {Success} from "../services/_utils/Either";
import {WebUris} from "../utils/WebUris";
import {useEffect, useState} from "react";

type CreateVocClassState =
    | { type: 'loading' }
    | { type: 'success'; message: string }
    | { type: 'error'; message: string };

const useCreateVocClass = () => {
    const navigate = useNavigate();
    const [state, setState] = useState<CreateVocClassState>({ type: 'loading' });
    const { userInfo } = useUserInfo();
    const role = userInfo?.role.name;

    const [vocData, setVocData] = useState<Voc>({
        description: "",
        started: "",
        ended: "",
        approved: false,
        section: { id: 1 },
    });

    const { handleSaveVocClass, error } = useVocs();
    const { sections } = useSections();
    const { students } = useStudents();

    useEffect(() => {
        if (state.type === 'loading' && sections !== null && students !== null) {
            setState({ type: 'success', message: '' });
        } else if (error) {
            setState({ type: 'error', message: handleError(error) });
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
        setState({ type: 'loading' });
        const res = await handleSaveVocClass({
            description: vocData.description,
            started: vocData.started,
            ended: vocData.ended,
            approved: vocData.approved,
            user: vocData.user?.id,
            section: vocData.section.id,
        });
        if (res instanceof Success) {
            navigate(PROFILE);
        } else {
            const errorMessage = handleError(res);
            setState({ type: 'error', message: errorMessage });
        }
    };

    const handleSectionChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            section: { id: Number(e.target.value) },
        }));
    };

    const handleDateChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>, key: "started" | "ended") => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            [key]: e.target.value,
        }));
    };

    const handleStudentChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setVocData((prevVocData) => ({
            ...prevVocData,
            user: { id: Number(e.target.value) },
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
        handleSectionChange,
        handleDateChange,
        handleStudentChange,
    };
};

export default useCreateVocClass;
