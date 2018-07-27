import fs from 'fs';
export default class FileSystem {
    parseJSONResponse(model) {
        // TODO: add error responses using random number
        let response = this.parseJSONFile('tests/__data/genericResponse.json');
        let data = this.parseJSONFile('tests/__data/' + model + ".json");
        response.data = data;
        response.status = 200;
        return response;
    }

    parseJSONFile(file) {
        const content = String(fs.readFileSync(file));
        return JSON.parse(content);
    }
}