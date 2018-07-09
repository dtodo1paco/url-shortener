import React from "react"
import { render } from "react-dom"
import { Router, Route, hashHistory, IndexRoute } from "react-router"
import { Provider } from "react-redux"
import injectTapEventPlugin from "react-tap-event-plugin"
injectTapEventPlugin()

import App from "./views/App"
import Home from "./views/Home"

import "./styles.scss"

render(
    <Router history={hashHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={Home} />
      </Route>
    </Router>,
    document.getElementById("app")
)
