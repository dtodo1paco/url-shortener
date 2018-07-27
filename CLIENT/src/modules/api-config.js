//const apiVersion = 'v1';
let backendHost;
let node = process.env.NODE_ENV;

if (node === 'production') {
    backendHost = "http://localhost:8080/";
} else {
    if (node === 'development') {
        backendHost = "http://localhost:8080/";
    } else {
        //console.log("TEST MODE: simulating server responses");
        backendHost = null;
    }
}

export const API_ROOT = backendHost;