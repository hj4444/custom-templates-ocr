#!/bin/bash
mvn compile jib:build  -DsendCredentialsOverHttp=true