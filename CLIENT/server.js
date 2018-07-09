// server.js
var express = require("express")
var path = require("path")
var compression = require("compression")

var app = express()
app.use(compression())

// serve our static stuff like index.css
//app.use(express.static(__dirname))
app.use(express.static(path.join(__dirname, "dist")))

// send all requests to index.html so browserHistory in React Router works
app.get("*", function(req, res) {
  //res.sendFile(path.join(__dirname, 'index.html'))
  res.sendFile(path.join(__dirname, "dist", "index.html"))
})

var PORT = process.env.PORT || 3000
app.listen(PORT, function() {
  console.log("Production Express server running at localhost:" + PORT)
})
