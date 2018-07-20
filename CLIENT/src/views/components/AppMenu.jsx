import React from "react"
import { Link } from 'react-router-dom';
import { Toolbar } from 'react-md/lib/Toolbars';

const AppMenu = ( props ) => (
    <Toolbar
        colored={false}
        nav={<Link className="index-link" to="/">{props.title}</Link>}
        title={null}
        actions={
            <div>
            {props.currentUser ? (
                <div>
                    <span className="username">{props.currentUser.sub}</span>
                    <Link to="/logout"><span>Log out</span></Link>
                </div>
            ) : (
                <div className="top-bar-right-links">
                    <Link to="/login"><span>Log in</span></Link>
                    <Link to="/signup"><span>Sign up</span></Link>
                </div>
            )}
            </div>}
    />
);
export default AppMenu;
