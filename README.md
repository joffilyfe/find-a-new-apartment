# Find a new apartment

This is a tool to help you to find a new apartment from a known blue startup (`4nd4r5`). It basicaly fetchs data from their API and accumulates into a database, then you can track only new apartments :)

This is my first clojure project, so it may do not like too functionalish but it is a start.

## Running

Just run

```shell
$ clj -M:dev
```

## Building

If you wish to generate a `jar` file, just use the `depstar` tool, run:

```shell
$ clojure -X:depstar uberjar :jar app.jar
```

and run the jar with:

```shell
$ java -cp app.jar clojure.main -m find-a-new-apartment.core
```