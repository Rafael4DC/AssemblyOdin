const { resolve } = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: './src/index.tsx',
    resolve: {
        extensions: ['.js', '.ts', '.tsx', '.css'],
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'public/index.html', // Ensure this points to your actual HTML template
            title: 'Assembly Odin',
        }),
    ],
    output: {
        filename: '[name].bundle.js',
        path: resolve(__dirname, 'dist'),
        clean: true,
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader'],
            },
            {
                test: /\.(png|jpe?g|gif|svg|mp4)$/i,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[path][name].[ext]',
                        },
                    },
                ],
            },
        ],
    },
};
