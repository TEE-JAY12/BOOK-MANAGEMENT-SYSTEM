# REST API 

This is a Springboot project  

This is backend application for Book Management System

## Prerequirements

* Java 11
* SpringBoot 
* H2 database

## List of Endpoint

The base url for the application is  http://localhost:8080/. The List of Api include

1. Add a book (http://localhost:8080/book/savebook) - This endpoint is used to register a new user.
2. Edit a book (http://localhost:8080/book/savebook) - This endpoint is used to edit a book  using the id of the book 
3. Retrieve Book details(http://localhost:8080/book/getbook/{id})- This endpoint is used to retrieve a book
4. Get all Books (http://localhost:8080/book/getAllBooks?pageNumber=1&pageSize=3) - This is Used to retrieve the list of books paginated
5. Search books by title or author(http://localhost:8080/book/getbooks/{SearchItem}?pageNumber=1&pageSize=3) - This is used to search a books by title or author


## To set up the application, please follow these guidelines:

1. Clone the repository  at https://github.com/TEE-JAY12/BOOK-MANAGEMENT-SYSTEM.git
2. Run the application.It should run by default on port 8080.
3. To access the H2 database use this link http://localhost:8080/h2-console/login . The Jdbc url is jdbc:h2:mem:bookstoreh2:mem:bookstore
4. Test the application with the required body using Postman.


# REST API

The REST API for the Book app is described below.

## Add Book

### Request
`POST /savebook/`

    | POST | http://localhost:8080/book/savebook | Add a book |

### REQUEST
    {
        "id": 0,
        "title": "Battle for the Young",
        "author": "Gbile Akanni",
        "publicationYear": 2024
    }
### Response Body
    {
        "responseCode": 200,
        "responseMessage": "SAVED SUCCESSFULLY",
        "statusCode": "000",
        "data": {
            "id": 1,
            "title": "Battle for the Young",
            "author": "Gbile Akanni",
            "publicationYear": 2024,
            "createdDate": "2023-09-26T12:40:10.937+00:00",
            "modifiedDate": null
        }
    }

## Edit a Book

### Request
`POST /savebook/`

    
    | POST | http://localhost:8080/book/savebook | Edit a book |

### REQUEST

    {
        "id": 1,
        "title": "Battle for the Young",
        "author": "Gbile Akanni",
        "publicationYear": 2025
    }
### Response Body
    {
        "responseCode": 200,
        "responseMessage": "SAVED SUCCESSFULLY",
        "statusCode": "000",
        "data": {
            "id": 1,
            "title": "Battle for the Young",
            "author": "Gbile Akanni",
            "publicationYear": 2025,
            "createdDate": "2023-09-26T13:07:34.374+00:00",
            "modifiedDate": "2023-09-26T13:07:34.374+00:00"
        }
    }

## Get a Book detail

### Request
`GET /getbook/{id}`

    | GET | http://localhost:8080/book/getbook/1 | Get a book |


### Response Body
    {
        "responseCode": 200,
        "responseMessage": "SUCCESS",
        "statusCode": "000",
        "data": {
            "id": 1,
            "title": "Battle for the Young",
            "author": "Gbile Akanni",
            "publicationYear": 2025,
            "createdDate": "2023-09-26T13:07:34.374+00:00",
            "modifiedDate": "2023-09-26T13:07:34.374+00:00"
        }
    }

## Get List of Books(Paginated)

### Request
`GET /getAllBooks?pageNumber=1&pageSize=3`

    | GET | http://localhost:8080/book/getAllBooks?pageNumber=1&pageSize=3 | Get List of book |


### Response Body
    {
        "responseCode": 200,
        "responseMessage": "SUCCESS",
        "statusCode": "000",
        "data": {
            "content": [
                {
                    "id": 1,
                    "title": "Battle for the Young",
                    "author": "Gbile Akanni",
                    "publicationYear": 2025,
                    "createdDate": "2023-09-26T13:07:34.374+00:00",
                    "modifiedDate": "2023-09-26T13:07:34.374+00:00"
                },
                {
                    "id": 2,
                    "title": "Life Goes On",
                    "author": " Tony Agbamu ",
                    "publicationYear": 2022,
                    "createdDate": "2023-09-26T13:12:37.717+00:00",
                    "modifiedDate": null
                },
                {
                    "id": 3,
                    "title": "Never a Rebel",
                    "author": " Wash Nwachukwu ",
                    "publicationYear": 2022,
                    "createdDate": "2023-09-26T13:13:20.059+00:00",
                    "modifiedDate": null
                }
            ],
            "pageable": {
                "sort": {
                    "empty": true,
                    "sorted": false,
                    "unsorted": true
                },
                "offset": 0,
                "pageSize": 3,
                "pageNumber": 0,
                "paged": true,
                "unpaged": false
            },
            "totalElements": 3,
            "last": true,
            "totalPages": 1,
            "size": 3,
            "number": 0,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "numberOfElements": 3,
            "first": true,
            "empty": false
        }
    }

## search books by title or author. 

### Request
`GET /getbooks/{Search word}`

    
    | GET | http://localhost:8080/book/getbooks/Arsernal?pageNumber=1&pageSize=3| search books by title or author. |


### Response Body
    {
        "responseCode": 200,
        "responseMessage": "SUCCESS",
        "statusCode": "000",
        "data": {
            "content": [
                {
                    "id": 4,
                    "title": "Arsernal",
                    "author": " Wash Nwachukwu ",
                    "publicationYear": 2022,
                    "createdDate": "2023-09-26T13:17:18.387+00:00",
                    "modifiedDate": null
                },
                {
                    "id": 5,
                    "title": "Moving Forward",
                    "author": " Arsernal",
                    "publicationYear": 2022,
                    "createdDate": "2023-09-26T13:17:47.826+00:00",
                    "modifiedDate": null
                },
                {
                    "id": 6,
                    "title": "Clean Code",
                    "author": " Arsernal",
                    "publicationYear": 2022,
                    "createdDate": "2023-09-26T13:18:16.772+00:00",
                    "modifiedDate": null
                }
            ],
            "pageable": {
                "sort": {
                    "empty": true,
                    "sorted": false,
                    "unsorted": true
                },
                "offset": 0,
                "pageSize": 3,
                "pageNumber": 0,
                "paged": true,
                "unpaged": false
            },
            "totalElements": 3,
            "last": true,
            "totalPages": 1,
            "size": 3,
            "number": 0,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "numberOfElements": 3,
            "first": true,
            "empty": false
        }
    }

## Delete a Book 

### Request
`DELETE /getbook/{id}`

    | DELETE | http://localhost:8080/book/getbook/1 | Delete a book |


### Response Body
    {
        "responseCode": 200,
        "responseMessage": "DELETED",
        "statusCode": "000",
        "data": {
            "id": 1,
            "title": "Clean Code",
            "author": " Arsernal",
            "publicationYear": 2022,
            "createdDate": "2023-09-26T14:38:16.274+00:00",
            "modifiedDate": null
        }
    }


**Thank for going through the application**

