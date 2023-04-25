import { add } from "./serviceHelper";

export function addLocation(location) {
    return add(location, 'http://localhost:8080/api/location', 'locationId')
}