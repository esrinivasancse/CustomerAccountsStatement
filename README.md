# Customer Monthly Reports - SpringBoot

### Prerequisites

* Java8

### Abstact

This application will parse records.xml and records.csv files exported into ./input/ path on monthly basis and generate report in ./output/reports.csv. 

Newly exported file contains reference number, description & status of the record. There could be many reasons to fail like duplicate reference number & end balance mismatch.

### Scheduler

This application implemented in such a way that will execute for every minute and it can be changed in application.properties file based on our need.
