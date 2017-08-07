#REST Endpoint sample application w/ Spring Boot
This is a sample application using Spring Boot and REST to serve up an endpoint.

##Setup
You'll need Java 1.8, Groovy, and Gradle installed locally to run this application.  You can install Groovy & Gradle using
Homebrew with the following commands:

```
brew install groovy
brew install gradle
```

Once you've got the dependencies installed, building and running the code is as easy as:

```
gradle bootrun
```

After the service has started up, you can test it out locally by hitting the following URL:

```
http://localhost:8080/ping
```

If everything is successfully configured and running, you'll see a short JSON response when you hit that URL.
