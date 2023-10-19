# playmd
playmd is a server-side web application that renders Markdown files as a webpage. The intent is that this is all 
processed on the server, and that no JavaScript is required for an operational webpage.

## Usage

To test locally:

```shell
sbt run
```

You can deploy your server using 
[the deployment instructions from Play Framework.](https://www.playframework.com/documentation/2.8.x/Deploying).

## External Libraries

playmd uses the following external libraries:

- [Play Framework](https://www.playframework.com)
- [TxtMark](https://github.com/rjeschke/txtmark)