import { API_ROOT } from "modules/api-config.js";
import jwtDecode from 'jwt-decode'
import httpClient from 'modules/httpClient';
import FileSystem from 'modules/FileSystem.js';

const api = {};
const fileReader = new FileSystem();
/** TOKEN RELATED **/
api.getToken = function() {
    return localStorage.getItem('token')
}
api.setToken = function(token) {
    localStorage.setItem('token', token)
    return token
}
api.getCurrentUser = function() {
    const token = api.getToken();
    if(token) return jwtDecode(token)
    return null
}
api.logOut = function() {
    httpClient.logOut();
    localStorage.removeItem('token')
    return true
}
/** TOKEN RELATED **/


// During initial app load attempt to set a localStorage stored token
// as a default header for all api requests.
httpClient.setAuthToken(api.getToken());

/** SERVER RELATED **/
api.logIn = function(credentials, callback) {
    if (API_ROOT) {
        httpClient.logIn(credentials, callback);
    } else {
        const result = fileReader
            .parseJSONResponse('login');
        api.setToken(result.data.Authorization);
        result.data = api.getCurrentUser();
        if (callback) {
            callback(result);
        }
    }
}

api.getModel = function(model, callback) {
    if (API_ROOT) {
        httpClient.getModel(api.getToken(), model, callback);
    } else {
        console.log("READING FROM FILE getModel");
    }
}

api.postModel = function(model, properties, callback) {
    if (API_ROOT) {
        httpClient.postModel(api.getToken(), model, properties, callback);
    } else {
        const result = fileReader.parseJSONResponse(model);
        if (callback) {
            callback(result);
        }
    }

}

// During initial app load attempt to set a localStorage stored token
// as a default header for all api requests.
export default api;