import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Heading from "./Heading";
import Form from "./Form";
import { editAgency, getAgency } from "../services/agencyService";


function EditAgency() {
    const [agency, setAgency] = useState(null);
    const { agencyId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        getAgency(agencyId).then(setAgency);
    }, [agencyId]);


    const handleSave = (values) => {
        return editAgency(values);
    };

    const handleSuccess = () => {
        navigate(`/agencies/${agencyId}`)
    };

    if(!agency) {
        return null;
    }

    return (
        <>
            <Heading>Edit Agency</Heading>
            <Form fields={[
                { name: 'shortName', label: 'Short Name' },
                { name: 'longName', label: 'Long Name' }
            ]}
                onSave={handleSave}
                recordType='Agency'
                onSuccess={handleSuccess}
                initialValues={agency}
            />
        </>
    );
}

export default EditAgency;