import {useEffect, useState} from "react";
import {Department} from "../../services/department/models/Department";
import {DepartmentService} from "../../services/department/DepartmentService";
import {Failure, Success} from "../../services/_utils/Either";
import {handleError} from "../../utils/Utils";

type DepartmentsState =
    | { type: 'loading' }
    | { type: 'success'; departments: Department[], loading: boolean }
    | { type: 'error'; message: string };

const useDepartments = () => {
    const [state, setState] = useState<DepartmentsState>({type: 'loading'});

    const getDepartments = async () => {
        DepartmentService.getAll()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', departments: data.value.departments, loading: false});
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err});
            });
    };

    useEffect(() => {
        getDepartments();
    }, []);

    return {
        state,
        setState,
        getDepartments
    };
}

export default useDepartments;
