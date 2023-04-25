import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import Heading from './Heading';
import { getAgency } from '../services/agencyService';



function AgencyDetail() {
    const [agency, setAgency] = useState(null);
    const { agencyId } = useParams();

    useEffect(() => {
        getAgency(agencyId).then(setAgency);
    }, [agencyId]);

    // if no agency exits, don't display anything
    if (!agency) {
        return null;
    }

    return (
        <>
            <Heading>{`Agency Detail - ${agency.longName} (${agency.shortName})`}</Heading>
            <div>
                <Link to="/agencies" className='btn btn-warning'>Return to Agencies</Link>
                <Link to='edit' className='btn btn-primary ms-2'>Edit</Link>
            </div>


            <h3>Locations</h3>
            <Link to="addlocation" className='btn btn-success'>Add Location</Link>
            {agency.locations.length > 0 ? (
                <table className="table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Address</th>
                            <th>City</th>
                            <th>Region</th>
                            <th>Country Code</th>
                            <th>Postal Code</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        {agency.locations.map(l => (
                            <tr key={l.locationId}>
                                <td>{l.name}</td>
                                <td>{l.address}</td>
                                <td>{l.city}</td>
                                <td>{l.region}</td>
                                <td>{l.countryCode}</td>
                                <td>{l.postalCode}</td>
                                <td>
                                    <Link to={`editlocation/${l.locationId}`} className='btn btn-primary btn-sm'>Edit</Link>
                                    <button className='btn btn-danger btn-sm ms-1'>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p><em>There are no Locations for this agency.</em></p>
            )}

            <h3>Agents</h3>
            <Link to="addagent" className='btn btn-success'>Add Agent</Link>
            {agency.agents.length > 0 ? (
                <table className="table">
                    <thead>
                        <tr>
                            <th>Identifier</th>
                            <th>Activation Date</th>
                            <th>Active</th>
                            <th>Agent</th>
                            <th>Security Clearance</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        {agency.agents.map(a => (
                            <tr key={a.agent.agentId}>
                                <td>{a.identifier}</td>
                                <td>{a.activationDate}</td>
                                <td>{a.active}</td>
                                <td>{a.agent.lastName}, {a.agent.firstName} {a.agent.middleName} (DOB: {a.agent.dob})</td>
                                <td>{a.securityClearance.name}</td>
                                <td>
                                    <Link to={`editagent/${a.agent.agentId}`} className='btn btn-primary btn-sm'>Edit</Link>
                                    <button className='btn btn-danger btn-sm ms-1'>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            ) : (
                <p><em>There are no agents assigned to this agency.</em></p>
            )}
        </>
    );
}

export default AgencyDetail;