import React from "react"

import Router from 'react-router-dom/HashRouter';
import { Switch, Route, Redirect } from 'react-router-dom';
import httpClient from 'modules/httpClient'

import HomePage from "views/pages/HomePage";
import LoginPage from "views/pages/LoginPage";
import DashboardPage from "views/pages/DashboardPage";
import Logout from 'views/components/Logout'
import App from "views/App";

export default class MainApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null
        }
        this.handleLoginSuccess = this.handleLoginSuccess.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
    }

    handleLoginSuccess(user) {
        this.setState({ currentUser: httpClient.getCurrentUser() })
    }

    handleLogout() {
        httpClient.logOut()
        this.setState({ currentUser: null })
    }

    isUserAuth() {
        return httpClient.getCurrentUser() != null;
    }
    getUserAuth() {
        return httpClient.getCurrentUser();
    }

    render() {
        return (
            <Router>
                <App title="URL Shortener" loginHandler={this} >
                    <Switch>
                        <Route exact path='/' component={HomePage}/>
                        <Route path='/login' render={(props) => {
                            return <LoginPage {...props} loginHandler={this} />
                        }}/>
                        <Route path="/logout" render={(props) => {
                            return <Logout loginHandler={this} goTo="/" />;
                        }} />
                        <Route path='/dashboard' render={(props) => {
                            return this.isUserAuth()
                                ? <DashboardPage {...props} handleLoginSuccess={this.handleLoginSuccess} handleLogout={this.handleLogout} />
                                : <Redirect to="/login" from='/dashboard'/>
                        }}/>
                    </Switch>
                </App>
            </Router>
        )
    }
}


