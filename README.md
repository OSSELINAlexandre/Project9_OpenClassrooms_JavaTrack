# Mediscreen - Project 9 OpenClassrooms 
Mediscreen is a web application for doctors. The purpose of this app is to manage patient, medical notes, and to provides a diagnostic on patient based on their related notes. 

There are four main components for this application:

* patientmanagementapi (sprint 1)
* patientnotes (sprint 2)
* diagnosisapi (sprint 3)
* doctorinterface

Patientmanagementapi is in charge of the patient management. It provides the capabilities to Cread, Read, Update or Delete patient from your local SQL database.
PatientNotes is in charge of the doctors notes. It provides the capabilities to Cread, Read, Update or Delete patient note from your local NoSQL database.
Diagnosisapi is in charge of providing a diagnostic for the patient based on the content of the notes related to them. 
Doctorinterface is the web interface coordinating all endpoints in order to provide a visualisation of all thse services.


## Prerequisite

* Java 11.
* Gradle 7.2
* Spring
* Docker
* MongoDB
* MySQL



## Usage

First, you need to build the Docker images of each microservice.
There is three of them (patientmanagementapi, patientnotes, diagnosisapi).

Go in each repository, launch the following command for each corresponding repository (the names are important here, the DockerCompose is set for specific names).

```bash
docker build -t patientmanagementapi .
```

followed by : 

```bash
docker run -p 8081:8081 patientmanagement
```

then 


```bash
docker build -t patientnotesapi .
```

To launch the next app, you first need to launch your local MongoDB database. 

followed by : 

```bash
docker run -p 8082:8082 patientnotesapi
```

then 

```bash
docker build -t diagnosisapi .
```

followed by : 

```bash
docker run -p 8080:8080 diagnosisapi
```

Once all the Docker image are up and running :

```bash
java -jar build/libs/patientnotes-0.0.1-SNAPSHOT.jar
```

Well done ! The application is running. 

You can now access the interface on http://localhost:8079

If you want to access specific endpoints defined by a microservice itself in a container, instead of passing by the UserApp here are the adresses :

patientmanagement : http://localhost:8081

patientnotesapi : http://localhost:8082

## Some tips

If you run all these applications by the .jar or by your IDE, be carefull to change the 'host.internal.docker' in 'localhost' in doctorinterface and in the URL of the local database in both application.properties of patientmanagementapi and patientnotes. 


## Potential improvements

This project was segmented in three sprints with client requests to develop. 
The three sprints are fullly adressed in this current exemple, however we can see imporvements : 

A => The front end design part wasn't part of the project, therefore we develop a minimal interface with no further thoughts on UI or UX.
B => The notes could be improved to really reflect the relationship between a doctor and its patient (by adding a note subject, a motive of visit..).
C => DiagnosisApi could be improved and integrated into the interface.
