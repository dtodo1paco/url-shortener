import React from 'react';
import { shallow, mount, render } from 'enzyme';

import { MemoryRouter } from 'react-router';

import LoginPage from 'views/pages/LoginPage.jsx';
import Button from "react-md/lib/Buttons"



describe('LoginPage component tests', () => {
    beforeAll(() => {
    })
    afterAll(() => {
        /* Runs after all tests */
    })
    beforeEach(() => {
        /* Runs before each test */
    })
    afterEach(() => {
        /* Runs after each test */
    })


    test("Login gets a valid token with user info", () => {
        const wrapper = mount(<MemoryRouter>
            <LoginPage />
        </MemoryRouter>);

        expect(wrapper.find('.md-text--error')).toHaveLength(0);
        const username = wrapper.find('#username');
        username.simulate('change', {target: {name: 'username', value: 'dtodo1paco'}});
        const password = wrapper.find('#password');
        username.simulate('change', {target: {name: 'password', value: 'supersecret'}});

        expect(wrapper.find('LoginPage').getNode().state).toHaveProperty(
            'user', {"username":"dtodo1paco", "password":"supersecret", "data": null}
        );

        const submit = wrapper.find('#login-submit');
        submit.simulate('submit');

        expect(wrapper.find('LoginPage').getNode().state).toHaveProperty(
            'user', {"username":"dtodo1paco", "password":"supersecret", "data": {
                "exp": 1532622245,
                "iat": 1532621645,
                "sub": "dtodo1paco"
            }}
        );
    });


    test("Login form fails when no credentials are supplied", () => {
        const wrapper = mount(<MemoryRouter>
            <LoginPage />
        </MemoryRouter>);
        expect(wrapper.find('.md-text--error')).toHaveLength(0);
        const username = wrapper.find('#username');
        username.simulate('change', {target: {name: 'username', value: ''}});
        const password = wrapper.find('#password');
        username.simulate('change', {target: {name: 'password', value: ''}});

        const submit = wrapper.find('#login-submit');
        submit.simulate('submit');
        expect(wrapper.find('LoginPage').getNode().state).toHaveProperty(
            'user', {"username":"", "password":"", "data": null}
        );
    });
});