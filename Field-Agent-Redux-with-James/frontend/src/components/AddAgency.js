import { addAgency } from "../services/agencyService";
import { useNavigate } from "react-router-dom";
import Heading from "./Heading";
import Form from "./Form";

function AddAgency() {
    const navigate = useNavigate();
    
    const handleSave = (values) => {
        return addAgency(values);
    };

    const handleSuccess = (agency) => {
        navigate(`/agencies/${agency.agencyId}`)
    }

    return (
        <>
            <Heading>Add Agency</Heading>
            <Form fields={[
                { name: 'shortName', label: 'Short Name' },
                { name: 'longName', label: 'Long Name' },
            ]} 
            onSave={handleSave}
            recordType="Agency"
            onSuccess={handleSuccess}
            />
        </>
    );
}

export default AddAgency;