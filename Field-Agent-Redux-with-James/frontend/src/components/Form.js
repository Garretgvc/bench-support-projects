import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Errors from "./Errors";
import FormField from "./FormField";



function Form({ fields = [], onSave, recordType, onSuccess = null, initialValues = null }) {

    const [values, setValues] = useState(null);
    const [errors, setErrors] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {
        if (initialValues) {
            setValues({...initialValues});
        } else if (fields.length > 0) {
            const newValues = {};
            fields.forEach(f => newValues[f.name] = '');
            setValues(newValues);
        }
    }, [fields, initialValues]);

    const handleChange = (event) => {
        const newValues = { ...values };
        newValues[event.target.name] = event.target.value;
        setValues(newValues);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        onSave(values)
            .then(result => {
                switch (result.type) {
                    case 'success':
                        if (onSuccess) {
                            onSuccess(result.payload);
                        } else {
                            navigate('..');
                        }
                        break;
                    case 'invalid':
                        setErrors(result.messages)
                        break;
                    default:
                        console.log(`Unexpected result type: ${result.type}`);
                }
            });
    };

    if (!values) {
        return null;
    }

    return (
        <form onSubmit={handleSubmit}>
            <Errors errors={errors} />
            {fields.map(f => (
                <FormField key={f.name} name={f.name} label={f.label} value={values[f.name]} onChange={handleChange} />
            ))}
            <div>
                <button className="btn btn-success">{initialValues ? 'Update': 'Add'} {recordType}</button>
                <Link to='..' className="btn btn-warning ms-2">Cancel</Link>
            </div>
        </form>
    );
}

export default Form;