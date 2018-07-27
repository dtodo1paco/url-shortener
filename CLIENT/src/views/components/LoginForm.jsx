import React from 'react';
import PropTypes from 'prop-types';

import { Link } from 'react-router-dom';
import { Card, CardTitle, CardText, CardActions } from "react-md/lib/Cards"
import { Button } from 'react-md/lib/Buttons';
import { TextField } from 'react-md/lib/TextFields';


const LoginForm = ({
    onSubmit,
    onChange,
    errors,
    successMessage,
    userName,
    userPass
    }) => (
    <Card className="main-form md-block-centered">
        <CardTitle title="Login"></CardTitle>

          {successMessage && <div className="message message-success">{successMessage}</div>}
          {errors.summary && <div className="message message-error">{errors.summary}
                {
                    errors.errors.map(function(item, i) {
                        return <li key={i}>{item}</li>
                        })
                }
          </div>}

        <form action="/" onSubmit={onSubmit} className="md-grid" id="login-form" >
                <TextField
                    required
                    errorText='Please, type your email address'
                    id="username"
                    name="username"
                    label="Email address"
                    lineDirection="center"
                    placeholder="myemailaddress@example.com"
                    value={userName}
                    onChange={onChange}
                    className="md-cell md-cell--12 md-cell--4-phone md-cell--8-tablet"
                />
                <TextField
                    required
                    type='password'
                    errorText='Please, type your password'
                    id="password"
                    name="password"
                    label="Password"
                    lineDirection="center"
                    value={userPass}
                    onChange={onChange}
                    className="md-cell md-cell--12 md-cell--4-phone md-cell--8-tablet"
                />
                <Button id="login-submit" flat type="submit" onSubmit={onSubmit} primary
                    className="md-cell md-cell--12 md-cell--4-phone md-cell--8-tablet">
                    Log in
                </Button>
            <CardText>Don't have an account? <Link to={'/signup'}>Create one</Link>.</CardText>
        </form>
    </Card>
);

LoginForm.propTypes = {
    onSubmit: PropTypes.func.isRequired,
    onChange: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    successMessage: PropTypes.string.isRequired,
    userName: PropTypes.string.isRequired,
    userPass: PropTypes.string.isRequired
};

export default LoginForm;