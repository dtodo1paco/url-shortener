import React from "react"
import Media, { MediaOverlay } from "react-md/lib/Media"


export default class NotFound extends React.Component {
  render() {
      return (
      <Media>
          <h1>404 Not Found</h1>
          <img id="not-found-image"
              src="https://images.hellogiggles.com/uploads/2016/09/30045207/www.reddit.com-.gif"
              role="presentation"
          />
          <MediaOverlay>

          </MediaOverlay>
      </Media>
      )
  }
}
