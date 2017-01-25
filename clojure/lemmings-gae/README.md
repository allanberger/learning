# Lemmings on Google App Engine

> Through application of specific skills at the right time and in coordination with others is it possible to reach that which seems impossible at first.

```shell
# Install boot on macOS
brew install boot-clj

# run locally on http://localhost:8080
# will download App Engine SDK the first time, just give it as much time as it takes.
bin/server

# Deploy it on app engine. Make sure the `:app-id` in build.boot is correct
bin/deploy
```
