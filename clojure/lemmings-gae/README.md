# Lemmings on Google App Engine

> Through application of specific skills at the right time and in coordination with others is it possible to reach that which seems impossible at first.

## Install [boot](https://github.com/boot-clj/boot) on the Vagrant VM:
`sudo bash -c "cd /usr/local/bin && curl -fsSLo boot https://github.com/boot-clj/boot-bin/releases/download/latest/boot.sh && chmod 755 boot"`

## Start the local Google App Engine Dev Server
This will download the App Engine SDK and several dependencies the first time. Grab a cup of tea :)  
Run: `bin/server`

## Deploy it to Google App Engine
Make sure the `:app-id` in build.boot is correct  
Run: `bin/deploy`
