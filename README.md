# Description
This project is a Java-based application using the Spring Boot framework and Spring Cloud for building reactive microservices with MongoDB and Kafka integrations. It includes API documentation using Springdoc OpenAPI and uses Log4j for logging with Logstash Logback Encoder.

# Installation
To build and run the project, follow these steps:

Clone the repository: git clone https://github.com/YosefOwl/TimersService.git

Navigate to the project directory: cd TimersService

Ensure you have Java 21 installed.

Build the project: ./gradlew build

Run the application: ./gradlew bootRun

Make sure to set up your MongoDB and Kafka instances if needed and configure the application properties accordingly.


# Usage
Once the project is set up and running, you can interact with the APIs, monitor the application using Actuator, and access the OpenAPI UI for documentation.

Here are some key endpoints and functionalities:

MongoDB Integration: CRUD operations for MongoDB data using Spring Data Reactive.

Kafka Integration: Messaging between microservices using Spring Cloud Stream and Kafka binder.

Actuator Endpoints: Monitor application health, metrics, and more using Actuator endpoints (e.g., /actuator/health).

API Documentation: Access API documentation using the Springdoc OpenAPI UI at /swagger-ui.html.


### RSocket API 
The RSocket API in this project provides reactive communication for timer-related operations using the RSocket protocol. 
Below are the available endpoints:

* createTimer: Send a JSON payload of TimerBoundary to create a new timer. This endpoint uses the RSocket message mapping "create-timer-req-resp".
* updateTimer: Send a JSON payload of TimerBoundary to update an existing timer. This endpoint uses the RSocket message mapping "update-timer-req-resp".
* getTimerById: Retrieve a timer by its ID. This endpoint uses the RSocket message mapping "get-timer-by-id-req-resp". 
* getTimerByDeviceId: Retrieve timers associated with a specific device ID. This endpoint uses the RSocket message mapping "get-timers-by-device-id-req-stream". 
* disableTimerById: Cancel a timer by its ID. This endpoint uses the RSocket message mapping "disable-timer-by-id-fnf". 
* disableTimerByDeviceId: Cancel all timers associated with a specific device ID. This endpoint uses the RSocket message mapping "disable-timers-by-device-id-fnf". 
* deleteAll: Delete all timers. This endpoint uses the RSocket message mapping "delete-all-timers-fnf".

These endpoints utilize RSocket's reactive capabilities for efficient and asynchronous communication between microservices, providing real-time updates and data streaming functionalities.
### REST API

The REST API provides traditional HTTP-based communication, Here are some endpoints:

POST /timers/create: Create a new timer by sending a JSON payload of TimerBoundary.
PUT /timers/update: Update an existing timer by sending a JSON payload of TimerBoundary.
GET /timers/timerId={timerId}: Retrieve a timer by its ID.
GET /timers/deviceId={deviceId}: Retrieve timers associated with a specific device ID.
DELETE /timers/cancel/timerId={timerId}: Cancel a timer by its ID.
DELETE /timers/cancel/deviceId={deviceId}: Cancel all timers associated with a specific device ID.
DELETE /timers/delete-all: Delete all timers.

### TimerBoundary Example
Here's an example JSON for timer to create:

```JSim
{
  "timerId": "1", //Unique identifier for the timer.
  "name": "Sample Timer", // Name of the timer.
  "description": "This is a sample timer for testing purposes", // Description of the timer.
  "createdAt": "2024-03-12T10:00:00Z", // Timestamp when the timer was created.
  "status": "active", // Current status of the timer (e.g., active, inactive).
  "startTime": "2024-03-12T10:00:00Z", // Timestamp when the timer will start.
  "updateTime": "2024-03-12T10:00:00Z", // Timestamp when the timer was last updated.
  "duration": { // Duration of the timer (hours, minutes, seconds).
    "hours": 1,
    "minutes": 30,
    "seconds": 0
  },
  "recurrence": { // Recurrence settings for the timer.
    "type": "DAILY", // Type of recurrence (e.g., daily, weekly, monthly).
    "interval": 1, // Interval between recurrences.
    "endDate": "2024-04-12T10:00:00Z" // End date for recurring events.
  },
  "deviceId": "device1", // ID of the device associated with the timer.
  "deviceType": "type1", // Type of device associated with the timer.
  "deviceAction": { // Actions to be performed when the timer starts or completes.
    "onStart": {}, // Action to be performed when the timer starts (empty object for now).
    "onComplete": {} // Action to be performed when the timer completes (empty object for now).
  }
}

```
### Message to Kafka JSON Example
```json
{
    "messageId": "",
    "publishedTimestamp": "",
    "messageType": "timerNotification",
    "summary": "message from timers on start or complete timer",
    "externalReferences": {
        "service": "timerService",
        "externalServiceId": "timer id"
    },
    "messageDetails": {
        "onStart": {
            "timerId": "123",
            "deviceId": "device1",
            "deviceType": "type1",
            "deviceAction": {}
        }
    }
}
```



