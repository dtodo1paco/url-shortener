import React from 'react'
import { Redirect } from 'react-router-dom'

class Logout extends React.Component {

    componentDidMount() {
        this.props.loginHandler.handleLogout();
    }

    render() {
        return <Redirect to={this.props.goTo} />
    }
}

export default Logout