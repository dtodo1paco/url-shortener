import React from "react"

import Home from "views/Home";
import App from "views/App";

export default class MainApp extends React.Component {
  render() {
    return (
        <App>
            <Home />
        </App>
    )
  }
}


