import axios from 'axios'

import { API_ROOT } from "modules/api-config.js";
import api from "modules/api";
// instantiate axios
const httpClient = axios.create()

httpClient.logIn = function(credentials, callback) {
    let conf = {
        auth: credentials,
    }
    axios.get(API_ROOT + "auth/", conf)
        .then((serverResponse) => {
            //console.log("httpClient.logIn serverResponse: " + JSON.stringify(serverResponse));
            const token = serverResponse.data.Authorization;
            if (token) {
                api.setToken(token);
                // sets token as an included header for all subsequent api requests
                axios.defaults.headers.common['Authorization'] = token;
                serverResponse.data = api.getCurrentUser();
            }
            try {
                if (callback) {
                    callback(serverResponse)
                }
            } catch (e) {
                console.log("Ignoring error on callback: " + e);
            }
        }).catch(
            function (response) {
                console.error("ERROR when calling server:" + JSON.stringify(response), response);
                if (callback) {
                    callback(response)
                }
            }
        );
}


httpClient.logOut = function() {
    delete axios.defaults.headers.common['Authorization']
    return true
}

httpClient.getModel = function(token, model, callback) {
    if (token) {
        axios.defaults.headers.common['Authorization'] = token;
    }
    axios.get(API_ROOT + model).then( (response) => {
        if (response.status === 200) {
            if (callback) {
                try {
                    callback(response);
                } catch (e) {
                    console.error("Catching ERROR on callback: " + e, e);
                }

            }
        } else {
            console.log("httpClient.getModel: Unexpected OK status ["+response.status+"]. See https://github.com/axios/axios#handling-errors");
        }
    }).catch(
        function (response) {
            console.log("httpClient:: catching server error ["+response.status+"|"+response+"]");
            let errors = [];
            let errorSum = {}
            if (response.status === 401 || response.status === 403) {
                api.logOut();
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

httpClient.postModel = function(token, model, properties, callback) {
    axios.post(API_ROOT + model, properties).then( (response) => {
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
                api.logOut();
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

httpClient.setAuthToken = function (token) {
    axios.defaults.headers.common['Authorization'] = token
}

export default httpClient