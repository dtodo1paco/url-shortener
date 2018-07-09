var debug = process.env.NODE_ENV !== "production"
var webpack = require("webpack")
var path = require("path")

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0"

module.exports = {
  entry: "./src/index.jsx",
  resolve: {
    root: [path.join(__dirname, ""), path.join(__dirname, "src")],
    // extensions: ['', '.js', '.jsx']
    extensions: ["", ".css", ".js", ".jsx"]
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /(node_modules|bower_components)/,
        loader: "babel-loader",
        query: {
          presets: ["react", "es2015", "stage-0"],
          plugins: [
            "react-html-attrs",
            "transform-class-properties",
            "transform-decorators-legacy"
          ]
        }
      },
      {
        test: /\.s(a|c)ss$/,
        loader: "style!css?importLoaders=2!postcss!sass?sourceMap&outputStyle=expanded"
      }
    ]
  },
  output: {
    path: __dirname + "/../REST/src/main/resources/static/js",
    filename: "bundle.js"
  },
  exclude: [
    /\.html$/,
    /\.(js|jsx)$/,
    /\.css$/,
    /\.json$/,
    /\.svg$/,
    /\.s(c|a)ss$/
  ],

  plugins: debug
    ? []
    : [
        new webpack.optimize.DedupePlugin(),
        new webpack.optimize.OccurenceOrderPlugin(),
        new webpack.optimize.UglifyJsPlugin({
          // Eliminate comments
          comments: false,

          // Compression specific options
          compress: {
            // remove warnings
            warnings: false,

            // Drop console statements
            drop_console: true
          },

          mangle: {
            keep_fnames: true,
            except: ["$super"]
          },
          sourcemap: false
        })
      ]
}
