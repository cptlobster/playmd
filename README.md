# playmd
playmd is a server-side web application that renders Markdown files as a webpage. The intent is that this is all 
processed on the server, and that no JavaScript is required for an operational webpage.

## Files
Every page is a Markdown file stored in the document tree. They are all stored in the `/public` folder in your Play
project. When a file is accessed, Play renders the HTML and serves it.

### Viewing the Source
The source files can be viewed by adding `/src/` to the beginning of the path. For example, reading the source of this
file can be done by navigating to `/src/index.md`.

## Syntax
Standard Markdown syntax is supported.

## Usage

```shell
sbt run
```