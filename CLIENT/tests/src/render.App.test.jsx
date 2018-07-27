import React from 'react';
import { shallow, mount, render } from 'enzyme';

import MainApp from 'views/MainApp.jsx';
import HomePage from 'views/pages/HomePage.jsx';
import NotFound from 'views/pages/NotFound.jsx';

describe('App render', function() {
    it('Home path, routes to HomePage', () => {
        const wrapper = mount(
                <MainApp/>
        );
        expect(wrapper.find(HomePage)).toHaveLength(1);
        expect(wrapper.find(NotFound)).toHaveLength(0);
    })
});
