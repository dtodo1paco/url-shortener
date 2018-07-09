import React from "react"
import FooterDtodo1paco from 'views/FooterDtodo1paco.jsx';

export default class App extends React.Component {
  render() {
    return (
        <div>
            {this.props.children}
         <FooterDtodo1paco />
        </div>
    )
  }
}
