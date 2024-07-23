const {merge} = require('webpack-merge');

const common = require('./webpack.common.js');


module.exports = merge(common, {

    mode: 'development',

    devtool: 'inline-source-map',

    devServer: {
        port: 1337,
        historyApiFallback: true,
        compress: false,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
            },
        },
    },

});