# La Liga Stats

An application that allows users to collect information from the Internet about the football league La Liga and display information such as table, results and fixtures. It is also possible to display statistics - number of won odds of the matches.

## Table of contents
* [Introduction](#introduction)
* [Project details](#project-details)
* [Technologies](#technologies)
* [Setup](#setup)
* [Screenshots](#screenshots)

## Introduction

The application allows users to load the current table, results and future matches with odds about La Liga. The team data is downloaded from the [SkySports table page](https://www.skysports.com/la-liga-table), the match data from the [SkySports results page](https://www.skysports.com/la-liga-results), and the fixtures data from the [SkySports fixtures page](https://www.skysports.com/la-liga-fixtures). Then, all data is saved to JSON files. An Internet connection and working SkySports website are required to download all the necessary information. In order to collect information about the winning odds, the application have to be launched regularly - the data on the SkySports website about odds is provided only for a few matches ahead. The statistics screens are based on the matches played on December 5 and 6.

## Project details

The data package includes classes responsible for downloading data about La Liga from the Internet. The gui package contains graphical user interfaces classes. The json package includes classes responsible for saving and loading data from / to JSON files. The statistics package contains a class for calculating the statistics of winning odds. The resources folder contains sample data in JSON files.

## Technologies

Technologies used:
* Java 11
* Maven
* Jsoup API
* JUnit 5
* Swing API
* JSON

## Setup

For the application to work properly, user must have an internet connection and the SkySports website must be working. The application should be launched regularly so that it can collect data about the odds.

## Screenshots

| ![](https://github.com/k4lin97/images/blob/master/laligastats_0.png) |
|:--:|
| <b>Main menu</b>|

| ![](https://github.com/k4lin97/images/blob/master/laligastats_1.png) |
|:--:|
| <b>Results</b>|

| ![](https://github.com/k4lin97/images/blob/master/laligastats_2.png) |
|:--:|
| <b>Statistics of won odds - December 05 and 06</b>|
