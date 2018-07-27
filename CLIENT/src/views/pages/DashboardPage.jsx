import React from "react"
import { Redirect } from 'react-router-dom';
import CircularProgress from 'react-md/lib/Progress/CircularProgress';
import URLDataTable from 'views/components/URLDataTable'
import Message from 'views/components/Message';
import api from 'modules/api'

class DashboardPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            urls: null,
            goToLogin: false
        }
        this.checkResponse = this.checkResponse.bind(this);
        this.relogin = this.relogin.bind(this);
    }

    componentDidMount () {
        this.doSearch();
    }

    shouldComponentUpdate(nextProps, nextState) {
        let loadingChanged = this.state.loading != nextState.loading;
        let urlsChanged = this.state.urls != nextState.urls;
        return loadingChanged || urlsChanged;
    }

    doSearch () {
        this.setState({loading: true});
        api.getModel("/user/urls", this.checkResponse);
    }

    checkResponse (response) {
        let urls = [];
        let errorSum = {};
        if (response.status === 200 && response.data != null) {
            urls = response.data;
        } else {
            errorSum = response;
        }
        this.setState({loading: false, urls: urls, errors: errorSum});
    }

    buildHeadersFromData() {
        let headers = [];
        if (this.state.data != null && this.state.data.length > 0) {
            let obj = this.state.data[0];
            let keys = Object.keys(obj);
            keys.map(function (item, i) {
                headers.push(item);
            });
        }
        return headers;
    }

    relogin() {
        console.log("relogin");
    }

    render() {
        let messages = null;
        if (this.state.errors != null && this.state.errors.summary) {
            let relogin = <div>Click <a href="#" onClick={this.relogin}>here</a> to go to login page</div>
            this.state.errors.errors.push(relogin);
            messages = <Message success={false} summary={this.state.errors.summary} messages={this.state.errors.errors} />
        }

        let content = null;
        if (this.state.loading || this.state.urls === null) {
            content = <CircularProgress key="progress" id="url-shortener-dashboard" />
        } else if (this.state.goToLogin === true) {
            let referer = {from: '/dashboard'};
            content = <div></div> // <Redirect to="/login" state={referer} />;
        } else {
            let headers = [
                {label: 'Created', field: 'created'},
                {label: 'Code',    field: 'shortened'},
                {label: 'Source',  field: 'source'}
            ];
            content = <URLDataTable headers={headers} data={this.state.urls} pageSize={10} />
        }
        return (
            <div id="dashboard-page">
                {messages}
                {content}
            </div>
            )
    }
}

export default DashboardPage;