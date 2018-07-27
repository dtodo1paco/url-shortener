import React from 'react';

import { Redirect } from 'react-router-dom';
import LoginForm from 'views/components/LoginForm.jsx';

import api from 'modules/api'

class LoginPage extends React.Component {
    constructor(props, context) {
        super(props, context);
        // set the initial component state
        this.state = {
            loading: false,
            errors: {},
            successMessage: '',
            user: {
                username: '',
                password: '',
                data: null,
            }
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
            api.logIn(this.sanitize(), this.checkResponse);
        }
    }

    checkResponse(response) {
        let code = response.status;
        if (code === 200) {
            let user = this.state.user;
            user['data'] = response.data;
            this.setState({
                loading: false,
                user: user
            });
            if (this.props.loginHandler) {
                this.props.loginHandler.handleLoginSuccess(response.data, this.getReferer());
            }
        } else {
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

    getReferer() {
        if (this.props.location.state != null && this.props.location.state.referer != null) {
            return this.props.location.state.referer;
        }
        return null;
    }

    /**
     * Render the component.
     */
    render() {
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
export default LoginPage;