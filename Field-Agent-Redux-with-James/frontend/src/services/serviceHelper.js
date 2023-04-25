export function add(record, url, idPropertyName) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(record)
    };
    return fetch(url, init)
        .then(response => {
            if (response.status === 201 || response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected response status code: ${response.status}`)
            }
        })
        .then(data => {
            if (data[idPropertyName]) {
                //Happy Path
                return { type: 'Success', payload: data };
            } else {
                //Display Errors
                return { type: 'Invalid', messages: data };
            }
        })
        .catch(console.log)
}

export function edit(record, url) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(record)
    };
    return fetch(url, init)
        .then(response => {
            if(response.status === 204) {
                return null;
            }else if(response.status === 400) {
                return response.json();
            } else {
                return Promise.reject(`Unexpected response status code: ${response.status}`)
            }
        })
        .then(data => {
            if (!data) {
                //Happy Path
                return { type: 'success'};
            } else {
                //Display Errors
                return { type: 'invalid', messages: data };
            }
        })
        .catch(console.log)
}

export function remove(url) {
    const init = {
        method: 'DELETE'
    };
    return fetch(url, init)
    .then(response => {
        if( response.status === 204) {
            return {type: 'success'};
        }else if (response.status === 404)  {
            return {type: 'notfound'}
        }else {
            return Promise.reject(`Unexpected response status code: ${response.status}`);
        }
    })
    .catch(console.log)
}