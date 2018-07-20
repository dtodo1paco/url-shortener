import React from "react"
import { Link } from 'react-router-dom';

import FooterDtodo1paco from 'views/components/FooterDtodo1paco';
import AppMenu from 'views/components/AppMenu';



export default class App extends React.Component {

    componentWillReceiveProps(nextProps) {
    }

    render() {
        return (
            <div>
                <AppMenu title={this.props.title}
                    currentUser={this.props.loginHandler.getUserAuth()} />
                <main>
                    {this.props.children}
                </main>
                <FooterDtodo1paco />
            </div>
        )
    }
}




