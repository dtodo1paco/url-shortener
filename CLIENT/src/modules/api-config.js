//const apiVersion = 'v1';
let backendHost;
let node = process.env.NODE_ENV;

if (node === 'production') {
    backendHost = "https://dtodo1paco-url-shortener.herokuapp.com";
} else {
    if (node === 'development') {
        backendHost = "http://localhost:8080";
    } else {
        //console.log("TEST MODE: simulating server responses");
        backendHost = null;
    }
}

export const API_ROOT = backendHost;
