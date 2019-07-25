#!/usr/bin/env bash

mvn clean test
open target/site/jacoco/com.xiongyingqi.code/index.source.html

