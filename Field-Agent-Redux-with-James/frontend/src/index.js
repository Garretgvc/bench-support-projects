// from external libraries
import React from 'react';
import ReactDOM from 'react-dom/client';

// from internal componenets
import App from './App';

// from css-base imports
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
