import React from 'react';
import { shallow, mount } from 'enzyme';
import RouterApp from 'views/MainApp.jsx';

test('it should render', () => {
    shallow(<RouterApp />);
});

// snapshot test - test the final HTML
test('it should render the expected HTML', () => {
    expect(
    mount(<RouterApp />).html()
).toMatchSnapshot();
});
