import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Heading from "./Heading";
import Form from "./Form";
import { getAgency } from "../services/agencyService";
import { getLocation, editLocation } from "../services/locationServices";


function EditLocation() {
    const [agency, setAgency] = useState(null);
    const [location, setLocation] = useState(null);

    const { agencyId, locationId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        getAgency(agencyId).then(setAgency);
    }, [agencyId]);

    useEffect(() => {
        getLocation(locationId).then(setLocation);
    }, [locationId]);


    const handleSave = (values) => {
        return editLocation(values);
    };

    const handleSuccess = () => {
        navigate(`/agencies/${agencyId}`)
    };

    if (!agency || !location) {
        return null;
    }

    return (
        <>
            <Heading>{`Edit Location - ${agency.longName} (${agency.shortName})`}</Heading>
            <Form fields={[
                { name: 'name', label: 'Name' },
                { name: 'address', label: 'Address' },
                { name: 'city', label: 'City' },
                { name: 'region', label: 'Region' },
                { name: 'countryCode', label: 'Country Code' },
                { name: 'postalCode', label: 'Postal Code' }
            ]}
                onSave={handleSave}
                recordType='Location'
                onSuccess={handleSuccess}
                initialValues={location}
            />
        </>
    );
}

export default EditLocation;