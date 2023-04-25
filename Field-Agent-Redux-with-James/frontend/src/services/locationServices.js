import { add, edit, remove } from "./serviceHelper";

export function getLocation(locationId) {
    return fetch(`http://localhost:8080/api/location/${locationId}`)
        .then(response => response.json())
}

export function addLocation(location) {
    return add(location, 'http://localhost:8080/api/location', 'locationId')
}

export function editLocation(location) {
    return edit(location, `http://localhost:8080/api/location/${location.locationId}`)
}

export function deleteLocation(locationId) {
    return remove(`http://localhost:8080/api/location/${locationId}`)
}