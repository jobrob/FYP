#!/bin/sh
find . -name *.java > compileList.txt
javac -d ./bin @compileList.txt

