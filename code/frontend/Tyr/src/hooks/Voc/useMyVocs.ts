import {useEffect, useState} from "react";
import {Failure, Success} from "../../services/_utils/Either";
import {Voc} from "../../services/voc/models/Voc";
import {VocService} from "../../services/voc/VocService";
import {handleError} from "../../utils/Utils";


const useMyVocs = () => {
    const [state, setState] = useState<MyVocsState>({type: 'loading'});

    useEffect(() => {
        VocService.getVocsByUser()
            .then(data => {
                if (data instanceof Success) {
                    setState({type: 'success', vocs: data.value.vocs});
                } else if (data instanceof Failure) {
                    setState({type: 'error', message: handleError(data.value)});
                }
            })
            .catch(err => {
                setState({type: 'error', message: err.message || err });
            });
    }, []);

    return {
        state
    };
};

type MyVocsState =
    | { type: 'loading' }
    | { type: 'success'; vocs: Voc[]}
    | { type: 'error'; message: string };


export default useMyVocs;