let mockStorage = {};

module.exports = window.localStorage = {
  setItem: (key, val) => Object.assign(mockStorage, {[key]: val}),
  getItem: (key) => mockStorage[key],
  removeItem: (key) => Object.assign(mockStorage, {[key]: null}),
  clear: () => mockStorage = {}
};

