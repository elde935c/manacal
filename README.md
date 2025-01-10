# Mancala

IMPORTANT NOTE:
Make sure you use java version 11 or higher!

OO implementation of the game Mancala in Java with a very basic frontend in Javascript


## Repository structure

In this repository, we find the following files and directories.

```
api
   *Java subproject for running the webserver*
client
   *Our front-end*
domain
   *Java subproject for the Mancala domain*
persistence
   *Java subproject for interacting with the database*
.gitignore
README.md
settings.gradle
...
```

## The back-end

The back-end is a Java API, which is served using a simple [Jetty server](https://en.wikipedia.org/wiki/Jetty_(web_server)). We can build, test and run the whole project using the gradle wrapper:

```bash
# Build the project
./gradlew build
# Run all unit tests
./gradlew test
# Run the Jetty server
./gradlew run
```

If you run the server, you will see that it listens at `http://localhost:8080`.

## The front-end

The front-end (found in the `client` folder) is a [React](https://react.dev/) project, which is served using [Vite](https://vitejs.dev/). Here we find the following files:

```
public
   *static files such as images*
src
   *The React source code*
package.json
README.md
vite.config.ts
...
```

Styling can be done using [tailwindcss](https://tailwindcss.com/). As you may remember from the Front-End module, [`package.json`](./client/package.json) contains the dependencies of the project. Before we can run it, we need to install the dependencies:

```bash
cd client
npm install
```

After that, we can use the scripts from `package.json` to do the following:

```bash
# Start the front-end server
npm run dev
# Check code for common mistakes and style conventions
npm run lint
# Create a production-worthy build of the client
npm run build
```

If you start the front-end server, you will see that it listens at `http://localhost:5173`. Navigate to this url in your favorite browser, and admire the Mancala website! If you save changes to any of the React files, the front-end server will immediately inform the browser of these changes, and you will see them on your screen immediately.

