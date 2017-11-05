#!/bin/sh
rm -f compileList.txt;
find ./src -name *.java > compileList.txt;
javac -d ./bin @compileList.txt

