# Resource Scheduling
The following "Resource Scheduling Library" is a library designed to provide a "plug-and-play" Java microservice with in-memory store to assist with resources scheduling.

The underlying problem it solves is the "Interval Partitioning Problem", scheduling all requests with the minimal amount of resources, while providing extended functionality, mostly conecrns scheduling collisions.

## Install & Run
- **Language**: 
>Java 8 (Build [#1.8.0_311](https://www.java.com/en/download/ie_manual.jsp))

- **JVM Build**:
>Gradle (Release [#5.2.1](https://docs.gradle.org/5.2.1/release-notes.html))


- **Framework**:
> dropwizard (dropwizard-core 0.8)




by default the service will be exposed on `http://localhost:1406`.

## API Reference

### auth
| Security Scheme Type      | HTTP         |
|---------------------------|--------------|
| **HTTP Authorization Scheme** | Not Required |

## Schedule
--
#### Create Schedule
--
> Authorization [`auth`](###auth) 

> Query Parameters **None**
-----
>**POST** /schedule/resource/bulk 

>Content type: application/json
    
**Request Body Example**:
```json
{
    "resources": [
        {
            "resourceId": "a",
            "startTime": "1500",
            "endTime": "1600"
        },
        {
            "resourceId": "a",
            "startTime": "1800",
            "endTime": "1900"
        },
        {
            "resourceId": "b",
            "startTime": "1700",
            "endTime": "3000"
        }
    ]
}
```

**Response**   
```json
{
    "responseMessage": "Success",
    "resources": [
        {
            "resourceId": "a",
            "startTime": "1500",
            "endTime": "1600"
        },
        {
            "resourceId": "a",
            "startTime": "1700",
            "endTime": "3000"
        },
        {
            "resourceId": "b",
            "startTime": "1800",
            "endTime": "1900"
        }
    ]
}
```

#### Get Schedule
--
> Authorization [`auth`](##auth) 

> Query Parameters **None**
-----
>**GET** /schedule/resource

>**Content type**: application/json

**Response**   
```json
{
    "responseMessage": "Success",
    "resources": [
        {
            "id": "143278f8-f21a-4447-ae23-427c099d2959",
            "resourceId": "a",
            "startTime": "1500",
            "endTime": "1600"
        },
        {
            "id": "d5d403c9-4d8d-4081-98e4-2b732b19f3a1",
            "resourceId": "a",
            "startTime": "1700",
            "endTime": "3000"
        },
        {
            "id": "8e928acd-3d98-4418-bdbb-be5dbeea448e",
            "resourceId": "b",
            "startTime": "1800",
            "endTime": "1900"
        }
    ]
}
```

#### Get First Collision
> Authorization [`auth`](##auth) 

> Query Parameters **None**
-----
>**GET** /schedule/resource/firstCollision
>**Content type**: application/json

**Request Body Example**:
```json
{
    "resourcesId": "a"
}
```

**Response**   
```json
{
    "responseMessage": "Success",
    "firstCollidingResource": [
        {
            "id": "d5d403c9-4d8d-4081-98e4-2b732b19f3a1",
            "resourceId": "a",
            "startTime": "0000",
            "endTime": "0100"
        },
        {
            "id": "143278f8-f21a-4447-ae23-427c099d2959",
            "resourceId": "a",
            "startTime": "0000",
            "endTime": "0300"
        }
    ]
}
```

#### Get Resource Availability
--
> Authorization [`auth`](##auth) 

> Query Parameters **None**
-----
>**GET** /schedule/resource/availability
>**Content type**: application/json

**Request Body Example**:
```json
{
    "resourceId": "a",
    "time": "0015"
}
```

**Response**   
```json
{
    "requestStatus": "Success",
    "availabilityStatus": "locked"
}
```

#### Get All Collisions
--
> Authorization [`auth`](##auth) 

> Query Parameters **None**
-----
>**GET** /schedule/resource/allCollisions

>**Content type**: application/json

**Request Body Example**:
```json
{
    "resourceId": "a"
}
```

**Response**   
```json
{
    "status": "Success",
    "collisionsList": [
        {
            "resource": {
                "id": "9d95436a-39c8-4954-b6e0-202e0539330a",
                "resourceId": "a",
                "startTime": "0000",
                "endTime": "0300"
            },
            "collisions": [
                {
                    "id": "16fa5d82-dd9c-4870-ae94-8399cd4b0f65",
                    "resourceId": "a",
                    "startTime": "0000",
                    "endTime": "0100"
                },
                {
                    "id": "34484217-9127-41b6-a15a-6dc267ad5f8a",
                    "resourceId": "a",
                    "startTime": "0015",
                    "endTime": "0200"
                },
                {
                    "id": "3394f741-bb68-48d3-b85b-fed6148f0ac3",
                    "resourceId": "a",
                    "startTime": "0130",
                    "endTime": "0300"
                },
                {
                    "id": "0674afd2-34ec-44a1-925c-beae7afb746f",
                    "resourceId": "a",
                    "startTime": "0230",
                    "endTime": "0245"
                },
                {
                    "id": "9b80f303-4c7e-45bb-b336-8fdf2ef35859",
                    "resourceId": "a",
                    "startTime": "0300",
                    "endTime": "0400"
                },
                {
                    "id": "0edeeef1-680b-476e-8a49-615ea86a7129",
                    "resourceId": "a",
                    "startTime": "0300",
                    "endTime": "0400"
                }
            ]
        },
        {
            "resource": {
                "id": "3394f741-bb68-48d3-b85b-fed6148f0ac3",
                "resourceId": "a",
                "startTime": "0130",
                "endTime": "0300"
            },
            "collisions": [
                {
                    "id": "0674afd2-34ec-44a1-925c-beae7afb746f",
                    "resourceId": "a",
                    "startTime": "0230",
                    "endTime": "0245"
                },
                {
                    "id": "9b80f303-4c7e-45bb-b336-8fdf2ef35859",
                    "resourceId": "a",
                    "startTime": "0300",
                    "endTime": "0400"
                },
                {
                    "id": "0edeeef1-680b-476e-8a49-615ea86a7129",
                    "resourceId": "a",
                    "startTime": "0300",
                    "endTime": "0400"
                }
            ]
        }
    ]
}
```
