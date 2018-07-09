import axios from "axios"

var sendToServer = function (model,properties, callback) {
    var portletUrl = 'http://localhost:8080/'+model;
    var objPayload = properties;

    axios.post(portletUrl, objPayload).then(res=> {
        if (callback) {
            callback(res);
        }
    });
};
module.exports = sendToServer;