import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import Agencies from './components/Agencies';
import Agents from './components/Agents';
import Home from './components/Home';
import NotFound from './components/NotFound';
import SecurityClearances from './components/SecurityClearances';
import Menu from './components/Menu';
import AgencyDetail from './components/AgencyDetail';
import AddAgency from './components/AddAgency';
import AddLocation from './components/AddLocation';
import EditAgency from './components/EditAgency';
import EditLocation from './components/EditLocation';
import UnexpectedError from './components/UnexpectedError';

function App() {
  return (
    <Router>
      <Menu />
      <Routes>
        <Route path="/" element={<Home />} />
        {/*  nested routes:
          where /agencies is the main route and /:agencyID and add are different routes within /agencies  */}
        <Route path="/agencies"> 
          <Route index element={<Agencies />} />

          <Route path=":agencyId">
            <Route index element={<AgencyDetail />} />
            <Route path='edit' element={<EditAgency/>}/>
            <Route path='addlocation' element={<AddLocation/>}/>
            <Route path='editlocation/:locationId' element={<EditLocation/>}/>
          </Route>
          <Route path="add" element={<AddAgency/>}/>
        </Route>
        <Route path="/agents" element={<Agents />} />
        <Route path="/securityclearances" element={<SecurityClearances />} />
        <Route path='/unexpectederror' element={<UnexpectedError/>}/>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
}

export default App;
