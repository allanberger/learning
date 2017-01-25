# compojure-bot

Facebook Messenger Bot in Clojure via Leiningen’s Compojure Template

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Using environment variables

To add environment variables on OSX as required in the code example, open the terminal and type `export FB_PAGE_ACCESS_TOKEN="<YOUR_TOKEN>"`.

Check if the variable has been added to your local environment by using the command `printenv`.

Note: Start the web server in the same terminal window where you've added the variable since it's only stored in this terminal session.

## Running

To start a web server for the application in the Vagrant VM, run:

    lein ring server-headless

## License

Copyright © 2017 FIXME
