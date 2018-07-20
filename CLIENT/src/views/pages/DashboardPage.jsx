import React from "react"
import { Redirect } from 'react-router-dom';
import CircularProgress from 'react-md/lib/Progress/CircularProgress';
import URLDataTable from 'views/components/URLDataTable'
import httpClient from 'modules/httpClient'

class DashboardPage extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            loading: false,
            urls: []
        }
        this.checkResponse = this.checkResponse.bind(this);
    }

    componentDidMount() {
        this.doSearch();
    }

    shouldComponentUpdate(nextProps, nextState) {
        let loadingChanged = this.state.loading != nextState.loading;
        let urlsChanged = this.state.urls != nextState.urls;
        return loadingChanged || urlsChanged;
    }

    doSearch () {
        this.setState({loading: true});
        httpClient.getModel("/user/urls", this.checkResponse)
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

    render() {
        let headers = [
            {label: 'Created', field: 'created'},
            {label: 'Code',    field: 'shortened'},
            {label: 'Source',  field: 'source'}
        ];
        if (this.state.loading) {
            return <CircularProgress key="progress" id="url-shortener-dashboard" />
        }
        return (
            <URLDataTable headers={headers} data={this.state.urls} pageSize={10} />
        )
    }
}

export default DashboardPage;