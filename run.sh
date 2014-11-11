#!/bin/sh
##############################################################################
# Script to run cyclehunter application.
#
# Notes: Make sure you have compiled and packaged the application first:
# 
#     mvn compile
#     mvn package
#
##############################################################################

# Run cyclehunter's main.
mvn exec:java -Dexec.mainClass="com.ryanluu.cyclehunter.App"

##############################################################################
