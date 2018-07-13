import React from 'react';
import { shallow, mount, render } from 'enzyme';
import URLForm from 'views/URLForm.jsx';
import Button from "react-md/lib/Buttons"


// https://github.com/airbnb/enzyme/issues/341
// to get a default document where mount components
import jsdom from 'jsdom'
const doc = jsdom.jsdom('<!doctype html><html><body></body></html>')
global.document = doc
global.window = doc.defaultView

describe('URLForm component tests', function() {
    it("click with empty value produces error", () => {
        const wrapper = mount((<URLForm />));
        expect(wrapper.find('.md-text--error')).toHaveLength(0);
        const submit = wrapper.find(Button).first();
        expect(submit.exists()).toBe(true)
        submit.simulate('click');
        expect(wrapper.find('label.md-text--error')).toHaveLength(1);
    });
    it("click with a value renders circular progress loading icon", () => {
        const wrapper = mount((<URLForm />));
        wrapper.find('input').simulate('change', {target: {value: 'https://google.com'}});
        const submit = wrapper.find(Button).first();
        expect(submit.exists()).toBe(true)
        submit.simulate('click');
        expect(wrapper.find('label.md-text--error')).toHaveLength(0);
        expect(wrapper.find('.md-progress')).toHaveLength(1);
    });
});