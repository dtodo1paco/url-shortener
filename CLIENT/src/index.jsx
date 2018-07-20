import React from "react"
import { render } from "react-dom"

import MainApp from "./views/MainApp"
import "./styles.scss"
import injectTapEventPlugin from "react-tap-event-plugin"
injectTapEventPlugin()
render(
    <MainApp />, document.getElementById("app")
)
