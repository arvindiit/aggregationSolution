Design Approach
++++++++++++
Event driven approach has been chosen. Every request will result into the collection
of queue messages and published to respective queue in parllel. Other side of the queue,
lies the services which will fetch the result from respective backend services.
Scheduler are used to pick the messages from the queue. Scheduler  can be replaced with 
the listeners in case of JMS queues.

Development Approach
+++++++++++++++++++++++
Solution is spring boot application with maven as build tool. It is modular in nature and developed
to respect the SLA timeout agreement.

Focus is given more on correctness and robustness of solution than on exception handling,
logging and testing. These are also important component of development but given
time constraint, these parts are less focused and with more time these can be further improved.

This should be considered while evaluating the solution.

Running the solution
+++++++++++++++++++++
From command line
+++++
mvn clean install
java -jar target/aggregation-0.0.1-SNAPSHOT.jar

From IDE(intellij)
+++++++++
import as maven project
run as spring boot application(AggregationApplication)

application runs on 8081 and it can be changed in application.properties

firing http://localhost:8081/aggregation?pricing=NL,CN&track=109347263,123456891&shipments=109347263,123456891

should give back the below response between 5-10 sec.

{
    "pricing": {
        "CN": 4.710624916843842,
        "NL": 67.25997660122196
    },
    "shipments": {
        "109347263": [
            "box",
            "pallet",
            "box"
        ],
        "123456891": [
            "envelope"
        ]
    },
    "track": {
        "109347263": "COLLECTED",
        "123456891": "COLLECTED"
    }
}