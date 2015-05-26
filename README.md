# teamcity-plugin-jmh

[![Build Status](https://travis-ci.org/presidentio/teamcity-plugin-jmh.svg?branch=master)](https://travis-ci.org/presidentio/teamcity-plugin-jmh)

## Overview
===========

This plugin help you to work with [jmh](http://openjdk.java.net/projects/code-tools/jmh/) framework. 
JMH is a Java harness for building, running, and analysing nano/micro/milli/macro benchmarks written in Java and other languages targeting the JVM.
Plugin run benchmarks on the agent side and collect results for further displaying them on build tab named "Benchmark".

## Usage
===========

### Install
```
git clone https://github.com/presidentio/teamcity-plugin-jmh
cd teamcity-plugin-jmh
mvn package -DskipTests
cp target/teamcity-plugin-jmh.zip <TeamCity Data Directory>/plugins/
service teamcity restart 
```
More info: [Installing Additional Plugins for Teamcity](https://confluence.jetbrains.com/display/TCD9/Installing+Additional+Plugins)

### Setup
Go to you project *Build Configuration Settings* and create a new build step with runner type: *Jmh*.
To run it, you must specify at least one required parameter -  *jar path*. It specify path to the jar compiled for the benchmarks running.
Also you can configure benchmarks run with additional parameter supported by JMH, all of them available on build step settings page.

### Viewing Results
After the project build finished successfully, you can open *Benchmarks* tab on project build page. 
You will see results grouped by benchmark mode and class name. Each bar shows result percentiles. 
Black vertical line shows average value and gray dashed vertical line, closest previous build average result for this benchmark. 
The bar can lighted with one of the colors: red and green. Red color means benchmark result became slower and green - faster.

Licensing
=========

[![][license img]][license]

This software is licensed under the terms in the file named "LICENSE" in this directory.