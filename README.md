# fcm-kafka
 This is a multi-module maven project containing 4 child projects with a parent pom.xml file containing shared dependencies:
   - common-dto: this is a common module containing dtos and other components used in common by the child projects
   - registration-management-service: a rest api running on port 8080 that handles subscription and unsubscribe requests to FCM through Kafka. it runs a specific
     configuration for a kafka producer and forwards messages to a kafka broker running via docker (docker-compose.yml) on port 9092
   - notification-service: a rest api running on port 8081 that handles all types of notification requests and forwards them to FCM via Kafka. it runs a specific 
     configuration for a kafka producer and forwards messages to the kafka broker.
   - consumer-to-fcm-service: A separate service running on port 8082 hosting two kafka listeners (consumers) and specific configurations for these listeners. The
     service also hosts the configuration for the FCM admin sdk and is responsible for forwarding the received registration/notification messages to the FCM backend.
