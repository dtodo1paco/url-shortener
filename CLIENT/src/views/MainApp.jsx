import React from "react"

import Router from 'react-router-dom/HashRouter';
import { Switch, Route, Redirect } from 'react-router-dom';
import api from 'modules/api'

import HomePage from "views/pages/HomePage";
import LoginPage from "views/pages/LoginPage";
import DashboardPage from "views/pages/DashboardPage";
import NotFound from 'views/pages/NotFound'

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

    handleLoginSuccess(user, referer) {
        this.setState({ currentUser: api.getCurrentUser() })
        let ref = referer;
        console.log("RETURNING TO: " + ref);
        if (ref === null) {
            ref = '/dashboard';
        }

        location.replace(location.href.replace('/login', ref));
    }

    handleLogout() {
        api.logOut()
        this.setState({ currentUser: null })
    }

    isUserAuth() {
        return api.getCurrentUser() != null;
    }
    getUserAuth() {
        return api.getCurrentUser();
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
                                : <Redirect to={ {
                                    pathname: '/login',
                                    state: {
                                        referer: '/dashboard'
                                    }
                                    } }/>
                        }}/>
                        <Route component={NotFound}/>
                    </Switch>
                </App>
            </Router>
        )
    }
}


