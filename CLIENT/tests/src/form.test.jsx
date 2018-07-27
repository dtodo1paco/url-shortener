import React from 'react';
import { shallow, mount, render } from 'enzyme';
import URLForm from 'views/components/URLForm.jsx';
import Button from "react-md/lib/Buttons"


describe('URLForm component tests', function() {

    it("click with empty value shows error", () => {
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
    it("click with a valid value gets a transformed URL", () => {
        const wrapper = mount((<URLForm />));
        //wrapper.find('input').simulate('change', {target: {value: 'http://mylongurlwhichnobodywillremember.com'}});
        wrapper.find('input').simulate('change', { target: { value: 'http://mylongurlwhichnobodywillremember.com' } })
        const submit = wrapper.find(Button).first();
        expect(submit.exists()).toBe(true)
        submit.simulate('click');
        expect(wrapper.find('label.md-text--error')).toHaveLength(0);
        expect(wrapper.state('urlShortened')).toEqual('b70383d8');
    });
});