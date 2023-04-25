import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Heading from './Heading';
import { getAgencies } from '../services/agencyService';

function Agencies() {
  const [agencies, setAgencies] = useState([]);

  useEffect(() => {
    getAgencies().then(setAgencies);
  }, []);

  return (
    <>
      <Heading>Agencies</Heading>
      <Link to="/agencies/add" className="btn btn-success">Add Agency</Link>
      <table className="table">
        <thead>
          <tr>
            <th>Short Name</th>
            <th>Long Name</th>
            <th>&nbsp;</th>
          </tr>
        </thead>
        <tbody>
          {agencies.map(a => (
            <tr key={a.agencyId}>
              <td>{a.shortName}</td>
              <td>{a.longName}</td>
              <td>
                <Link to={`/agencies/${a.agencyId}`} className="btn btn-primary btn-sm">View</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}

export default Agencies;