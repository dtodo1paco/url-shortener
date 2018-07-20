import axios from 'axios'
import jwtDecode from 'jwt-decode'

const SERVER_URL = "http://localhost:8080";

// instantiate axios
const httpClient = axios.create()

httpClient.getToken = function() {
    return localStorage.getItem('token')
}

httpClient.setToken = function(token) {
    localStorage.setItem('token', token)
    return token
}

httpClient.getCurrentUser = function() {
    const token = httpClient.getToken()
    if(token) return jwtDecode(token)
    return null
}

httpClient.logIn = function(credentials, callback) {
    let conf = {
        "auth": credentials
    }
    axios.get(SERVER_URL + "/auth/", conf)
        .then((serverResponse) => {
            const token = serverResponse.data.Authorization;
            let ret = serverResponse;
            if (token) {
                // sets token as an included header for all subsequent api requests
                httpClient.setToken(token)
                axios.defaults.headers.common['Authorization'] = token;
                ret = jwtDecode(token)
            }
            console.log("httpClient.logIn returning: " +ret);
            try {
                if (callback) {
                    callback(ret)
                }
            } catch (e) {
                console.log("Ignoring error on callback: " + e);
                debugger;
            }
        }).catch(
            function (response) {
                console.log("ERROR when calling server:" + JSON.stringify(response));
                if (callback) {
                    callback(response)
                }
            }
        );
}


httpClient.logOut = function() {
    console.log("httpClient:: logging OUT current user: " + httpClient.getToken());
    localStorage.removeItem('token')
    delete axios.defaults.headers.common['Authorization']
    return true
}

httpClient.getModel = function(model, callback) {
    let token = httpClient.getToken();
    if (token) {
        axios.defaults.headers.common['Authorization'] = token;
    }
    axios.get(SERVER_URL + model).then( (response) => {
        if (response.status === 200) {
            if (callback) {
                callback(response);
            }
        } else {
            console.log("httpClient.getModel: Unexpected OK status ["+response.status+"]. See https://github.com/axios/axios#handling-errors");
        }
    }).catch(
        function (response) {
            console.log("httpClient:: catching error ["+response.status+"|"+response+"]");
            let errors = [];
            let errorSum = {}
            if (response.status === 401) {
                httpClient.logOut();
                errors.push('Your session has expired or is not active.');
            } else {
                errors.push('Try again in a minute and if it is still failing, call @dtodo1paco');
            }
            if (errors.length > 0) {
                errorSum['summary'] = 'Ooops! something went wrong.';
                errorSum['errors'] = errors;
            }
            if (callback) {
                callback(errorSum);
            }
        }
    )
}

httpClient.postModel = function(model, properties, callback) {
    let token = httpClient.getToken();
    if (token) {
        axios.defaults.headers.common['Authorization'] = token;
    }
    axios.post(SERVER_URL + model, properties).then( (response) => {
        if (response.status < 300) {
            if (callback) {
                callback(response);
            }
        } else {
            console.log("httpClient.postModel: Unexpected OK status ["+response.status+"]. See https://github.com/axios/axios#handling-errors");
        }
    }).catch(
        function (response) {
            console.log("httpClient:: catching error ["+response.status+"|"+response+"]");
            let errors = [];
            let errorSum = {}
            if (response.status === 401) {
                httpClient.logOut();
                errors.push('Your session has expired or is not active.');
            } else {
                errors.push('Try again in a minute and if it is still failing, call @dtodo1paco');
            }
            if (errors.length > 0) {
                errorSum['summary'] = 'Ooops! something went wrong.';
                errorSum['errors'] = errors;
            }
            if (callback) {
                callback(errorSum);
            }
        }
    )
}

// During initial app load attempt to set a localStorage stored token
// as a default header for all api requests.
axios.defaults.headers.common['Authorization'] = httpClient.getToken()
export default httpClient