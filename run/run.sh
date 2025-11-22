#!/bin/bash

cd "$(dirname "$0")/.." || exit 1

mysql -u root -p < db/setup.sql

mkdir -p out

javac -cp "lib/*" -d out $(find src/main/java -name "*.java")

java -cp "out:lib/*" edu.univ.erp.ui.Main
