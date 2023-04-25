import { add,edit } from "./serviceHelper";

export function getAgencies() {
    return fetch('http://localhost:8080/api/agency')
        .then(response => response.json())
}

export function getAgency(agencyId) {
    return fetch(`http://localhost:8080/api/agency/${agencyId}`)
        .then(response => response.json())
}

export function addAgency(agency) {
    return add(agency, 'http://localhost:8080/api/agency', 'agencyId')
}

export function editAgency(agency) {
    return edit(agency, `http://localhost:8080/api/agency/${agency.agencyId}`, )
}