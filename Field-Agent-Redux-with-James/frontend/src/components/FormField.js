function FormField({name,label, value, onChange, type='text'}) {
    return (
        <div className="row mb-3">
            <label htmlFor={name} className="col-sm-2 col-form-label">{label}:</label>
            <div className="col-sm-10">
                <input type={type} className="form-control" id={name} name={name}
                    value={value} onChange={onChange} />
            </div>
        </div>
    );
}

export default FormField;