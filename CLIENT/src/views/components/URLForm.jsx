import React from "react"
import Card from "react-md/lib/Cards/Card"
import CardTitle from "react-md/lib/Cards/CardTitle"
import CardActions from "react-md/lib/Cards/CardActions"
import CircularProgress from 'react-md/lib/Progress/CircularProgress';

import Media, { MediaOverlay } from "react-md/lib/Media"
import Button from "react-md/lib/Buttons"
import { TextField } from 'react-md';
import api from 'modules/api';

export default class URLForm extends React.Component {
    constructor() {
        super();
        this.state = {
            loading: false,
            numClicks: 0,
            urlValue: '',
            urlShortened: null
        }
        this.handleClick = this.handleClick.bind(this);
        this.handleReset = this.handleReset.bind(this);
        this.handleUrlChange = this.handleUrlChange.bind(this);
        this.checkResponse = this.checkResponse.bind(this);
        this.openInNewTab = this.openInNewTab.bind(this);
    }

    checkResponse(response) {
        if (response && response.data && response.data.shortened) {
            let loc = location.toString();
            let link = loc==='about:blank'?loc:loc + response.data.shortened;
            link = link.replace('/#','');
            this.setState({
                urlShortened: link,
                loading: false
            });
        }
    }

    handleUrlChange(url) {
        this.setState({urlValue: url})
    }

    handleClick (e) {
        e.preventDefault();
        let nClicks = this.state.numClicks + 1;
        let loading = false;
        let url = this.state.urlValue;
        if (url.length > 0) {
            loading = true;
            api.postModel("/url", {source: url}, this.checkResponse);
        }
        this.setState ({numClicks: nClicks, loading: loading});
    }

    handleReset (e) {
        this.setState({
            urlValue: '',
            loading: false,
            urlShortened: '',
            numClicks: 0
        })
    }

    openInNewTab () {
        window.open(this.state.urlShortened, "_blank");
    }

    render() {
        const { urlValue, numClicks, loading, urlShortened } = this.state;
        let result = null;
        if (loading) {
            result = <CircularProgress key="progress" id="url-shortener-waiting" />
        } else if (urlShortened) {
            result = <div id="url-shortened">
                <div className="result md-cell md-cell--10 md-cell--3-phone md-cell--6-tablet">
                    <span>Your URL shortened: <a href={urlShortened}>{urlShortened}</a></span>
                    <Button icon primary onClick={this.openInNewTab} className="md-cell md-cell--1 md-cell--1-phone md-cell--1-tablet">
                        open_in_new
                    </Button>
                </div>
                <Button icon primary onClick={this.handleReset} className="md-cell md-cell--2 md-cell--1-phone md-cell--2-tablet">
                    autorenew
                </Button>
            </div>;
        }
        return (
            <Card className="main-form md-block-centered">
                <Media>
                    <img
                        src="http://inspirationseek.com/wp-content/uploads/2014/06/Abstract-Painting-Ideas.jpg"
                        role="presentation"
                    />
                    <MediaOverlay>
                        <CardTitle title="URL Shortener" subtitle="Type a URL and click 'Shorten'">
                        </CardTitle>
                    </MediaOverlay>
                </Media>
                <form className="md-grid" onSubmit={this.handleClick}>
                    <div className="url-form">
                        <TextField
                            required
                            errorText='Please, type a URL before click button'
                            error={numClicks > 0 && (urlValue == null || urlValue.length == 0)}
                            id="floating-center-title"
                            label="URL"
                            lineDirection="center"
                            placeholder="https://google.com"
                            value={this.state.urlValue}
                            onChange={this.handleUrlChange}
                            className="md-cell md-cell--9 md-cell--3-phone md-cell--6-tablet"
                        />
                        <Button flat onClick={this.handleClick}
                            className="md-cell md-cell--2 md-cell--1-phone md-cell--2-tablet">
                            Shorten
                        </Button>
                    </div>
                    {result}
                </form>
            </Card>
        );
    }
}
