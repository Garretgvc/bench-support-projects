import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Heading from "./Heading";
import Form from "./Form";
import { getAgency } from "../services/agencyService";
import { addLocation } from "../services/locationServices";

function AddLocation() {
    const [agency, setAgency] = useState(null);
    const { agencyId } = useParams();

    useEffect(() => {
        getAgency(agencyId).then(setAgency)
    }, [agencyId]);

    const handleSave = (values) => {
        const locationToPost = { ...values, agencyId };
        return addLocation(locationToPost)
    };

    if (!agency) {
        return null;
    }
    return (
        <>
            <Heading>{`Add Location - ${agency.longName} (${agency.shortName})`}</Heading>
            <Form
                fields={[
                    { name: 'name', label: 'Name' },
                    { name: 'address', label: 'Address' },
                    { name: 'city', label: 'City' },
                    { name: 'region', label: 'Region' },
                    { name: 'countryCode', label: 'Country Code' },
                    { name: 'postalCode', label: 'Postal Code' }
                ]}
                onSave={handleSave}
                recordType='Location'
            />
        </>
    );
}

export default AddLocation;