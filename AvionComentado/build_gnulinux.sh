#!/bin/bash
rm -rfv build;
mkdir build &&
javac -d build src/org/sergiotarxz/avion/Main.java &&
rm avion.jar ;
jar cf avion.jar build ;
