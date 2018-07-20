
import React, { PropTypes } from 'react';
import { Redirect } from 'react-router-dom';
import LoginForm from 'views/components/LoginForm.jsx';

import httpClient from 'modules/httpClient'

class LoginPage extends React.Component {
    constructor(props, context) {
        super(props, context);
        const storedMessage = localStorage.getItem('successMessage');
        let successMessage = '';
        if (storedMessage) { // cleanup last message
            successMessage = storedMessage;
            localStorage.removeItem('successMessage');
        }

        // set the initial component state
        this.state = {
            loading: false,
            errors: {},
            successMessage,
            user: {
                username: '',
                password: ''
            },
            navigateTo: null
        };
        this.processForm = this.processForm.bind(this);
        this.changeUser = this.changeUser.bind(this);
        this.checkResponse = this.checkResponse.bind(this);
    }

    sanitize() {
        if (this.state.user) {
            const username = encodeURIComponent(this.state.user.username);
            const password = encodeURIComponent(this.state.user.password);
            return {
                "username": username,
                "password": password
            }
        }

    }
    /**
     * Process the form.
     *
     * @param {object} event - the JavaScript event object
     */
    processForm(event) {
        // prevent the form submission event
        event.preventDefault();
        let user = this.state.user;
        if (user.username.length > 0) {
            this.setState ({loading: true});
            httpClient.logIn(this.sanitize(), this.checkResponse);
        }

    }

    checkResponse(response) {
        let code = response.status;
        if (code) {
            let errors = [];
            if (code === 401) {
                errors.push('Bad credentials! Make sure you type correctly your access data');
            } else {
                errors.push('Try again in a minute and if it is still failing, call @dtodo1paco');
            }
            this.setState({
                loading: false,
                errors: {
                    summary: 'Ooops! something went wrong.',
                    errors: errors
                }
            })
        } else {
            this.props.loginHandler.handleLoginSuccess(response);
            this.setState( {
                navigateTo: "/dashboard",
                loading: false
            });
        }
    }

    /**
     * Change the user object.
     *
     * @param {string} value - the value set to the field
     * @param {object} event - the JavaScript event object
     */
    changeUser(value, event) {
        const field = event.target.name;
        const user = this.state.user;
        user[field] = value;
        this.setState({
            user
        });
    }

    /**
     * Render the component.
     */
    render() {
        if (this.state.navigateTo != null) {
            let referer = this.props.location.pathname;
            return <Redirect to={this.state.navigateTo || { from: { pathname: {referer} }}} />;
        }
        return (
            <LoginForm
                onSubmit={this.processForm}
                onChange={this.changeUser}
                errors={this.state.errors}
                successMessage={this.state.successMessage}
                userName={this.state.user.username}
                userPass={this.state.user.password}
            />
        );
    }

}

LoginPage.contextTypes = {
    router: PropTypes.object.isRequired
};

export default LoginPage;