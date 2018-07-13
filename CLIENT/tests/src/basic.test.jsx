import React from 'react';
import { shallow, mount } from 'enzyme';
import RouterApp from 'views/MainApp.jsx';

// https://github.com/airbnb/enzyme/issues/341
// to get a default document where mount components
import jsdom from 'jsdom'
const doc = jsdom.jsdom('<!doctype html><html><body></body></html>')
global.document = doc
global.window = doc.defaultView

test('it should render', () => {
    shallow(<RouterApp />);
});

// snapshot test - test the final HTML
test('it should render the expected HTML', () => {
    expect(
    mount(<RouterApp />).html()
).toMatchSnapshot();
});
