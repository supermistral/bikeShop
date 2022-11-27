const path              = require("path");
const webpack           = require("webpack");
const HtmlWebpackPlugin = require('html-webpack-plugin');


const DEBUG = process.env.NODE_ENV === "production" ? 0 : 1; 


module.exports = {
    entry: path.resolve(__dirname, "src", "index.js"),
    output: {
        path: path.resolve(__dirname, "build"),
        publicPath: DEBUG ? '/' : '/static/',
        filename: "[name].bundle.js"
    },
    devtool: "source-map",
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader"
                }
            },
            {
                test: /\.css$/,
                use: [
                    "style-loader",
                    "css-loader"
                ]
            },
            {
                test: /\.(ts|tsx)$/,
                exclude: /node_modules/,
                use: ["ts-loader"],
            },
            {
                test: /\.(woff(2)?|eot|ttf|otf|svg|)$/,
                type: 'asset/inline',
            },
            {
                test: /\.(?:ico|gif|png|jpg|jpeg)$/i,
                type: 'asset/resource',
            }
        ]
    },
    devServer: {
        historyApiFallback: true,
        compress: true,
        hot: true,
        port: 3000,
        proxy: {
            "/api": "http://localhost:8080",
            "/media": "http://localhost:8080",
        },
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, "src", "index.html")
        }),
        new webpack.DefinePlugin({
            "process.env": JSON.stringify(process.env)
        }),
    ],
}