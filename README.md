# playmd
playmd is a server-side web application that renders Markdown files as a webpage. The intent is that this is all 
processed on the server, and that no JavaScript is required for an operational webpage.

## Writing
playmd currently supports either Markdown or HTML for content.

### Markdown
Standard markdown syntax is supported. GFM or others may be added eventually.

### HTML
This is a special case; Since playmd injects the content into an HTML document already, you don't need to add the entire
document structure to your HTML. The following shows where your file contents will be inserted:
```html
<!DOCTYPE html>
<html lang="en">
  <body>
    <main>
      <!-- your file will be inserted here! -->
    </main>
  </body>
</html>
```

## Linking Between Pages
It is strongly recommended that you link relatively between pages, for readability and compatibility. For example, in
this directory tree (only showing `public` folder for conciseness):
```
public
| a.md
| b.md
| sub
| | c.md
```

you can create a link to `b.md` from `a.md` using the following:
```md
[click me!](b.md)
```

Relative links work from the file you are linking from. For example, linking to `b.md` from `c.md` instead requires the
following:
```md
[click me!](../b.md)
```

## Launching

Ensure that you have the latest version of SBT installed. This also depends on Java.

To test locally:

```shell
sbt run
```

You can deploy your server using 
[the deployment instructions from Play Framework.](https://www.playframework.com/documentation/2.8.x/Deploying).

## Configuration
You can configure playmd using the config in `conf/application.conf`. This uses the LightBend HOCON format.

- `title`: The title of the website. This will be displayed in the header.
- `links`: The links to display in the header:
  - `text`: The text to be displayed for each link.
  - `href`: The target of the link. Can be relative to `/public`, or any other external link (`http`, `https`, `mailto`,
    `tel`, `ssh`, really whatever is supported by the internet.)

## Licensing
playmd is licensed under the [GNU General Public License, version 3](LICENSE.md). 

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
[GNU General Public License, version 3](LICENSE.md) for more details.

### External Libraries

playmd uses the following external libraries, which are licensed under
[the Apache License, version 2.0](https://www.apache.org/licenses/LICENSE-2.0):

- [Play Framework](https://www.playframework.com)
- [TxtMark](https://github.com/rjeschke/txtmark)