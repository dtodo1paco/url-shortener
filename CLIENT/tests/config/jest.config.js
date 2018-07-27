{
    "collectCoverageFrom": ["<rootDir>/src/**/*.jsx"],
    "coverageDirectory": "<rootDir>/coverage",
    "coveragePathIgnorePatterns": [
        "/node_modules/"
    ],
    "moduleFileExtensions": [
        "js",
        "jsx"
    ],
    "modulePaths": ["src"],
    "testRegex": "tests/.*\\.test\\.(js|jsx)$",
    "testEnvironment": "jsdom",
    "setupFiles": ["<rootDir>/tests/config/localStorage-mock.js"],
    "globals": {
      "window": true,
      "NODE_ENV": "test"
    }
}
