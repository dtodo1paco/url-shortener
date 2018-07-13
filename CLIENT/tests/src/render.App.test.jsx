import React from 'react';
import { shallow, mount, render } from 'enzyme';
import MainApp from 'views/MainApp.jsx';

// https://github.com/airbnb/enzyme/issues/341
// to get a default document where mount components
import jsdom from 'jsdom'
const doc = jsdom.jsdom('<!doctype html><html><body></body></html>')
global.document = doc
global.window = doc.defaultView

describe('App renders correctly', function() {
    it("renders the index page", () => {
        const wrapper = mount(<MainApp />);
        expect(wrapper.find('h2')).toHaveLength(1);
        expect(wrapper.find('input')).toHaveLength(1);
        expect(wrapper.find('button')).toHaveLength(1);
        expect(wrapper.find('footer')).toHaveLength(1);
    });
});