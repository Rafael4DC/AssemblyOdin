# Asssemblu Odin - API Documentation

## Table of Contents

- [Introduction](#introduction)
- [Pagination](#pagination)
- [API Endpoints](#api-endpoints)
    - [Department](#department)
        - [Get All Departments](#get-all-departments)
        - [Get Department by id](#get-department-by-id)
        - [Create Department](#save-department)
        - [Update Department](#update-department)
        - [Delete Department](#delete-department)
    - [Field Study](#field-study)
        - [Get All Field Studies](#get-all-fields-of-study)
        - [Get Field Study by id](#get-field-study-by-id)
        - [Create Field Study](#save-field-study)
        - [Update Field Study](#update-field-study)
        - [Delete Field Study](#delete-field-study)
    - [Module](#module)
        - [Get All Modules](#get-all-modules)
        - [Get Module by id](#get-module-by-id)
        - [Create Module](#save-module)
        - [Update Module](#update-module)
        - [Delete Module](#delete-module)
    - [Section](#section)
        - [Get All Sections](#get-all-sections)
        - [Get Section by id](#get-section-by-id)
        - [Create Section](#save-section)
        - [Update Section](#update-section)
        - [Delete Section](#delete-section)
    - [Tech](#tech)
        - [Get All Techs](#get-all-techs)
        - [Get Tech by id](#get-tech-by-id)
        - [Create Tech](#save-tech)
        - [Update Tech](#update-tech)
        - [Delete Tech](#delete-tech)
        - [Create Tech Schedule](#save-tech-multiple)
    - [User](#user)
        - [Get All Users](#get-all-users)
        - [Get User by id](#get-user-by-id)
        - [Create User](#save-user)
        - [Update User](#update-user)
        - [Delete User](#delete-user)
        - [Get User Logs](#get-session-with-logs)
        - [Get Session](#get-session)
        - [Get Students](#get-students)
        - [Get Roles](#get-roles)
    - [Voc](#voc)
        - [Get All Vocs](#get-all-vocs)
        - [Get Voc by id](#get-voc-by-id)
        - [Create Voc](#save-voc)
        - [Update Voc](#update-voc)
        - [Delete Voc](#delete-voc)
- [Input Models](#input-models)
    - [Department Input Models](#department-input-models)
        - [Save Department Input Model](#save-department-input-model)
        - [Update Department Input Model](#update-department-input-model)
    - [Field Study Input Models](#field-study-input-models)
        - [Save Field Study Input Model](#save-field-study-input-model)
        - [Update Field Study Input Model](#update-field-study-input-model)
    - [Module Input Models](#module-input-models)
        - [Save Module Input Model](#save-module-input-model)
        - [Update Module Input Model](#update-module-input-model)
    - [Section Input Models](#section-input-models)
        - [Save Section Input Model](#save-section-input-model)
        - [Update Section Input Model](#update-section-input-model)
    - [Tech Input Models](#tech-input-models)
        - [Save Tech Input Model](#save-tech-input-model)
        - [Update Tech Input Model](#update-tech-input-model)
        - [Save Schedule Tech Input Model](#save-schedule-tech-input-model)
        - [Update Schedule Tech Input Model](#update-schedule-tech-input-model)
    - [User Input Models](#user-input-models)
        - [Save User Input Model](#save-user-input-model)
        - [Update User Input Model](#update-user-input-model)
    - [Voc Input Models](#voc-input-models)
        - [Save Voc Input Model](#save-voc-input-model)
        - [Update Voc Input Model](#update-voc-input-model)
- [Output Models](#output-models)
    - [Department Output Models](#department-output-models)
        - [Get All Departments Output Model](#get-all-departments-output-model)
        - [Get Department Output Model](#get-department-output-model)
        - [Save Department Output Model](#save-department-output-model)
        - [Update Department Output Model](#update-department-output-model)
    - [Field Study Output Models](#field-study-output-models)
        - [Get All Field Studies Output Model](#get-all-field-studies-output-model)
        - [Get Field Study Output Model](#get-field-study-output-model)
        - [Save Field Study Output Model](#save-field-study-output-model)
        - [Update Field Study Output Model](#update-field-study-output-model)
    - [Module Output Models](#module-output-models)
        - [Get All Modules Output Model](#get-all-modules-output-model)
        - [Get Module Output Model](#get-module-output-model)
        - [Save Module Output Model](#save-module-output-model)
        - [Update Module Output Model](#update-module-output-model)
    - [Section Output Models](#section-output-models)
        - [Get All Sections Output Model](#get-all-sections-output-model)
        - [Get Section Output Model](#get-section-output-model)
        - [Save Section Output Model](#save-section-output-model)
        - [Update Section Output Model](#update-section-output-model)
    - [Tech Output Models](#tech-output-models)
        - [Get All Techs Output Model](#get-all-techs-output-model)
        - [Get Tech Output Model](#get-tech-output-model)
        - [Save Tech Output Model](#save-tech-output-model)
        - [Update Tech Output Model](#update-tech-output-model)
        - [Get User Logs Output Model](#get-user-logs-output-model)
    - [User Output Models](#user-output-models)
        - [Get All Users Output Model](#get-all-users-output-model)
        - [Get User Output Model](#get-user-output-model)
        - [Save User Output Model](#save-user-output-model)
        - [Update User Output Model](#update-user-output-model)
        - [Get User Logs Output Model](#get-user-logs-output-model)
    - [Voc Output Models](#voc-output-models)
        - [Get All Vocs Output Model](#get-all-vocs-output-model)
        - [Get Voc Output Model](#get-voc-output-model)
        - [Save Voc Output Model](#save-voc-output-model)
        - [Update Voc Output Model](#update-voc-output-model)
- [Error Handling](#error-handling)
    - [Problem Details](#problem-details)
    - [Invalid Name](#invalid-name)
    - [Department Not Found](#department-not-found)
    - [Department Already Exists](#department-already-exists)
    - [Field Study Not Found](#field-study-not-found)
    - [Field Study Already Exists](#field-study-already-exists)
    - [Module Not Found](#module-not-found)
    - [Module Already Exists](#module-already-exists)
    - [Section Not Found](#section-not-found)
    - [Section Already Exists](#section-already-exists)
    - [Tech Not Found](#tech-not-found)
    - [Tech Already Exists](#tech-already-exists)
    - [Invalid Schedule](#invalid-schedule)
    - [User Not Found](#user-not-found)
    - [User Already Exists](#user-already-exists)
    - [Voc Not Found](#voc-not-found)
    - [Voc Already Exists](#voc-already-exists)

## Introduction

This document outlines the API endpoints and how to make requests to say API.
The API is a simple REST API that allows to interact with the Assembly Odin.
The API is divided into several endpoints, each responsible for a different part of the system.

## Pagination

As of current implementation, the API does not support pagination, due to the quantity of data being small.

## API Endpoints

The API has the following endpoints:

### Department

Department Endpoint is responsible for managing Departments in the system.

#### Get Department by id

- **URL:** `/api/departments/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the Department.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Department Output Model](#get-department-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Department Not Found](#department-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/departments/1" -H "accept: application/json"
    ```

#### Get All Departments

- **URL:** `/api/departments`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Departments Output Model](#get-all-departments-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/departments" -H "accept: application/json"
    ```

#### Save Department

- **URL:** `/api/departments/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Department Input Model](#save-department-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Department Output Model](#save-department-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [Department Already Exists](#department-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/departments/save" -H "accept: application/json" -d @- <<'EOF'
    {
        "name": "Department A",
        "description": "Description A"
    }
    EOF
    ```

#### Update Department

- **URL:** `/api/departments/update`
- **Method:** `PUT`
- **Request Body:**
    - `application/json`
        - [Update Department Input Model](#update-department-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update Department Output Model](#update-department-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Department Not Found](#department-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/departments/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "name": "Updated Department",
        "description": "Updated Description"
    }
    EOF
    ```

#### Delete Department

- **URL:** `/api/departments/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the Department.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Department Output Model](#get-department-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Department Not Found](#department-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/departments/1" -H "accept: application/json"
    ```

### Field Study

Field Study Endpoint is responsible for managing Field Studies in the system.

#### Get Field Study by id

- **URL:** `/api/fieldsstudy/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the Field Study.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Field Study Output Model](#get-field-study-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Field Study Not Found](#field-study-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/fieldsstudy/1" -H "accept: application/json"
    ```

#### Get All Fields of Study

- **URL:** `/api/fieldsstudy`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Field Studies Output Model](#get-all-field-studies-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/fieldsstudy" -H "accept: application/json"
    ```


#### Save Field Study

- **URL:** `/api/fieldsstudy/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Field Study Input Model](#save-field-study-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Field Study Output Model](#save-field-study-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [Field Study Already Exists](#field-study-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/fieldsstudy/save" -H "accept: application/json" -d @- <<'EOF'
    {
        "name": "Field Study A",
        "description": "Description A",
        "department": 1
    }
    EOF
    ```

#### Update Field Study

- **URL:** `/api/fieldsstudy/update`
- **Method:** `PUT`
- **Path Variables:**
    - `id` - The unique id of the Field Study.
- **Request Body:**
    - `application/json`
        - [Update Field Study Input Model](#update-field-study-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update Field Study Output Model](#update-field-study-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Field Study Not Found](#field-study-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/fieldsstudy/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "name": "Updated Field Study",
        "description": "Updated Description",
        "department": 1
    }
    EOF
    ```

#### Delete Field Study

- **URL:** `/api/fieldsstudy/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the Field Study.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Field Study Output Model](#get-field-study-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Field Study Not Found](#field-study-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/field-studies/1" -H "accept: application/json"
    ```

### Module

Module Endpoint is responsible for managing Modules in the system.

#### Get Module by id

- **URL:** `/api/modules/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the Module.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Module Output Model](#get-module-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Module Not Found](#module-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/modules/1" -H "accept: application/json"
    ```

#### Get All Modules

- **URL:** `/api/modules`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Modules Output Model](#get-all-modules-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/modules" -H "accept: application/json"
    ```

#### Save Module

- **URL:** `/api/modules/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Module Input Model](#save-module-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Module Output Model](#save-module-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [Module Already Exists](#module-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/modules/save" -H "accept: application/json" -d @- <<'EOF'
     {
        "name": "Module A",
        "fieldStudy": 1,
        "tier": 1,
        "description": "Description A"
    }
    EOF
    ```

#### Update Module

- **URL:** `/api/modules/update`
- **Method:** `PUT`
- **Path Variables:**
    - `id` - The unique id of the Module.
- **Request Body:**
    - `application/json`
        - [Update Module Input Model](#update-module-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update Module Output Model](#update-module-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Module Not Found](#module-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/modules/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "name": "Updated Module",
        "fieldStudy": 1,
        "tier": 1,
        "description": "Updated Description"
    }
    EOF
    ```

#### Delete Module

- **URL:** `/api/modules/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the Module.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Module Output Model](#get-module-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Module Not Found](#module-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/modules/1" -H "accept: application/json"
    ```

### Section

Section Endpoint is responsible for managing Sections in the system.

#### Get Section by id

- **URL:** `/api/sections/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the Section.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Section Output Model](#get-section-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Section Not Found](#section-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/sections/1" -H "accept: application/json"
    ```

#### Get All Sections

- **URL:** `/api/sections`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Sections Output Model](#get-all-sections-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/sections" -H "accept: application/json"
    ```

#### Save Section

- **URL:** `/api/sections/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Section Input Model](#save-section-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Section Output Model](#save-section-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [Section Already Exists](#section-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/sections/save" -H "accept: application/json" -d @- <<'EOF'
    {
        "name": "Section A",
        "module": 1,
        "students": [1, 2]
    }
    EOF
    ```

#### Update Section

- **URL:** `/api/sections/update`
- **Method:** `PUT`
- **Path Variables:**
    - `id` - The unique id of the Section.
- **Request Body:**
    - `application/json`
        - [Update Section Input Model](#update-section-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update Section Output Model](#update-section-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Section Not Found](#section-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/sections/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "name": "Updated Section",
        "module": 1,
        "students": [1, 2]
    }
    EOF
    ```

#### Delete Section

- **URL:** `/api/sections/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the Section.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Section Output Model](#get-section-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Section Not Found](#section-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/sections/1" -H "accept: application/json"
    ```

### Tech

Tech Endpoint is responsible for managing Techs in the system.

#### Get Tech by id

- **URL:** `/api/techs/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the Tech.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Tech Output Model](#get-tech-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Tech Not Found](#tech-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/techs/1" -H "accept: application/json"
    ```

#### Get All Techs

- **URL:** `/api/techs`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Techs Output Model](#get-all-techs-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/techs" -H "accept: application/json"
    ```

#### Save Tech

- **URL:** `/api/techs/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Tech Input Model](#save-tech-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Tech Output Model](#save-tech-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [Tech Already Exists](#tech-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/techs/save" -H "accept: application/json" -d @- <<'EOF'
    {
        "teacher": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z",
        "summary": "Summary A",
        "missTech": [1, 2]
    }
    EOF
    ```

#### Update Tech

- **URL:** `/api/techs/update`
- **Method:** `PUT`
- **Request Body:**
    - `application/json`
        - [Update Tech Input Model](#update-tech-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update Tech Output Model](#update-tech-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Tech Not Found](#tech-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/techs/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "teacher": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z",
        "summary": "Updated Summary",
        "missTech": [1, 2]
    }
    EOF
    ```

#### Delete Tech

- **URL:** `/api/techs/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the Tech.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Tech Output Model](#get-tech-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Tech Not Found](#tech-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/techs/1" -H "accept: application/json"
    ```

#### Save Tech Multiple

- **URL:** `/api/techs/savemultiple`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Schedule Tech Input Model](#save-schedule-tech-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Tech Output Model](#save-tech-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [User Not Found](#user-not-found)
            - [Section Not Found](#section-not-found)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/techs/savemultiple" -H "accept: application/json" -d @- <<'EOF'
    {
        "teacher": 1,
        "section": 1,
        "startDate": "2023-01-01",
        "endDate": "2023-01-31",
        "classTime": "10:00",
        "classLengthHours": 2,
        "dayOfWeek": "MONDAY"
    }
    EOF
    ```

### User

User Endpoint is responsible for managing Users in the system.

#### Get User by id

- **URL:** `/api/users/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the User.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get User Output Model](#get-user-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [User Not Found](#user-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/users/1" -H "accept: application/json"
    ```

#### Get All Users

- **URL:** `/api/users`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Users Output Model](#get-all-users-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/users" -H "accept: application/json"
    ```


#### Save User

- **URL:** `/api/users/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save User Input Model](#save-user-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save User Output Model](#save-user-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [User Already Exists](#user-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/users/save" -H "accept: application/json" -d @- <<'EOF'
    {
        "email": "usera@example.com",
        "username": "User A",
        "credits": 100,
        "role": 1
    }
    EOF
    ```

#### Update User

- **URL:** `/api/users/update`
- **Method:** `PUT`
- **Request Body:**
    - `application/json`
        - [Update User Input Model](#update-user-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update User Output Model](#update-user-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [User Not Found](#user-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/users/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "email": "updateduser@example.com",
        "username": "Updated User",
        "credits": 150,
        "role": 1
    }
    EOF
    ```

#### Delete User

- **URL:** `/api/users/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the User.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get User Output Model](#get-user-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [User Not Found](#user-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/users/1" -H "accept: application/json"
    ```

#### Get Session With Logs

- **URL:** `/api/users/logs`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get User Logs Output Model](#get-user-logs-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [User Not Found](#user-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/users/logs" -H "accept: application/json"
    ```

#### Get Session

- **URL:** `/api/users/session`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get User Output Model](#get-user-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [User Not Found](#user-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/users/logs" -H "accept: application/json"
    ```

#### Get Students

- **URL:** `/api/users/students`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Users Output Model](#get-all-users-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/users/logs" -H "accept: application/json"
    ```

#### Get Roles

- **URL:** `/api/users/roles`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Roles Output Model](#get-all-roles-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/users/logs" -H "accept: application/json"
    ```


### Voc

Voc Endpoint is responsible for managing Vocs in the system.

#### Get Voc by id

- **URL:** `/api/vocs/{id}`
- **Method:** `GET`
- **Path Variables:**
    - `id` - The unique id of the Voc.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Voc Output Model](#get-voc-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Voc Not Found](#voc-not-found)
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/vocs/1" -H "accept: application/json"
    ```

#### Get All Vocs

- **URL:** `/api/vocs`
- **Method:** `GET`
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get All Vocs Output Model](#get-all-vocs-output-model)
- **Error Response:**
    - **Content:**
        - `nothing`
- **Sample Call:**
    ```shell
    curl -X GET "http://localhost:8080/api/vocs" -H "accept: application/json"
    ```


#### Save Voc

- **URL:** `/api/vocs/save`
- **Method:** `POST`
- **Request Body:**
    - `application/json`
        - [Save Voc Input Model](#save-voc-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Save Voc Output Model](#save-voc-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Invalid Name](#invalid-name)
            - [Voc Already Exists](#voc-already-exists)
- **Sample Call:**
    ```shell
    curl -X POST "http://localhost:8080/api/vocs/save" -H "accept: application/json" -d @- <<'EOF'
    {
        "description": "Description A",
        "approved": true,
        "user": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z"
    }
    EOF
    ```

#### Update Voc

- **URL:** `/api/vocs/update`
- **Method:** `PUT`
- **Request Body:**
    - `application/json`
        - [Update Voc Input Model](#update-voc-input-model)
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Update Voc Output Model](#update-voc-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Voc Not Found](#voc-not-found)
            - [Invalid Name](#invalid-name)
- **Sample Call:**
    ```shell
    curl -X PUT "http://localhost:8080/api/vocs/update" -H "accept: application/json" -d @- <<'EOF'
    {
        "id": 1,
        "description": "Updated Description",
        "approved": true,
        "user": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z"
    }
    EOF
    ```

#### Delete Voc

- **URL:** `/api/vocs/{id}`
- **Method:** `DELETE`
- **Path Variables:**
    - `id` - The unique id of the Voc.
- **Success Response:**
    - **Content:**
        - `application/json`
            - [Get Voc Output Model](#get-voc-output-model)
- **Error Response:**
    - **Content:**
        - `application/json`
            - [Voc Not Found](#voc-not-found)
- **Sample Call:**
    ```shell
    curl -X DELETE "http://localhost:8080/api/vocs/1" -H "accept: application/json"
    ```

## Input Models

### Department Input Models

#### Save Department Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `name`: The name of the department.
    - **Optional:**
        - `description`: The description of the department.
- **Example:**

    ```json
    {
        "name": "Department A",
        "description": "Description A"
    }
    ```

#### Update Department Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `name`: The name of the department.
    - **Optional:**
        - `description`: The description of the department.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Department",
        "description": "Updated Description"
    }
    ```

### Field Study Input Models

#### Save Field Study Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `name`: The name of the field study.
    - **Optional:**
        - `description`: The description of the field study.
        - `department`: The department id.
- **Example:**

    ```json
    {
        "name": "Field Study A",
        "description": "Description A",
        "department": 1
    }
    ```

#### Update Field Study Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the field study.
        - `name`: The name of the field study.
    - **Optional:**
        - `description`: The description of the field study.
        - `department`: The department id.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Field Study",
        "description": "Updated Description",
        "department": 1
    }
    ```

### Module Input Models

#### Save Module Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `name`: The name of the module.
        - `fieldStudy`: The field study id.
        - `tier`: The tier of the module.
    - **Optional:**
        - `description`: The description of the module.
- **Example:**

    ```json
    {
        "name": "Module A",
        "fieldStudy": 1,
        "tier": 1,
        "description": "Description A"
    }
    ```

#### Update Module Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the module.
        - `name`: The name of the module.
        - `fieldStudy`: The field study id.
        - `tier`: The tier of the module.
    - **Optional:**
        - `description`: The description of the module.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Module",
        "fieldStudy": 1,
        "tier": 1,
        "description": "Updated Description"
    }
    ```

### Section Input Models

#### Save Section Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `name`: The name of the section.
        - `module`: The module id.
    - **Optional:**
        - `students`: The list of student ids.
- **Example:**

    ```json
    {
        "name": "Section A",
        "module": 1,
        "students": [1, 2]
    }
    ```

#### Update Section Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the section.
        - `name`: The name of the section.
        - `module`: The module id.
    - **Optional:**
        - `students`: The list of student ids.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Section",
        "module": 1,
        "students": [1, 2]
    }
    ```

### Tech Input Models

#### Save Tech Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `section`: The section id.
        - `started`: The start time of the tech.
        - `ended`: The end time of the tech.
        - `summary`: The summary of the tech.
    - **Optional:**
        - `teacher`: The teacher id.
        - `missTech`: The list of missing student ids.
- **Example:**

    ```json
    {
        "teacher": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z",
        "summary": "Summary A",
        "missTech": [1, 2]
    }
    ```

#### Update Tech Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the tech.
        - `section`: The section id.
        - `started`: The start time of the tech.
        - `ended`: The end time of the tech.
        - `summary`: The summary of the tech.
    - **Optional:**
        - `teacher`: The teacher id.
        - `missTech`: The list of missing student ids.
- **Example:**

    ```json
    {
        "id": 1,
        "teacher": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z",
        "summary": "Updated Summary",
        "missTech": [1, 2]
    }
    ```

#### Save Schedule Tech Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `teacher`: The teacher id.
        - `section`: The section id.
        - `startDate`: The start date of the schedule.
        - `endDate`: The end date of the schedule.
        - `classTime`: The class time.
        - `classLengthHours`: The class length in hours.
        - `dayOfWeek`: The day of the week.
- **Example:**

    ```json
    {
        "teacher": 1,
        "section": 1,
        "startDate": "2023-01-01",
        "endDate": "2023-01-31",
        "classTime": "10:00",
        "classLengthHours": 2,
        "dayOfWeek": "MONDAY"
    }
    ```

#### Update Schedule Tech Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `teacher`: The teacher id.
        - `section`: The section id.
        - `startDate`: The start date of the schedule.
        - `endDate`: The end date of the schedule.
        - `classTime`: The class time.
        - `classLengthHours`: The class length in hours.
        - `dayOfWeek`: The day of the week.
- **Example:**

    ```json
    {
        "teacher": 1,
        "section": 1,
        "startDate": "2023-01-01",
        "endDate": "2023-01-31",
        "classTime": "10:00",
        "classLengthHours": 2,
        "dayOfWeek": "MONDAY"
    }
    ```

### User Input Models

#### Save User Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `email`: The email of the user.
        - `username`: The username of the user.
        - `role`: The role id.
    - **Optional:**
        - `credits`: The credits of the user.
- **Example:**

    ```json
    {
        "email": "usera@example.com",
        "username": "User A",
        "credits": 100,
        "role": 1
    }
    ```

#### Update User Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the user.
        - `email`: The email of the user.
        - `username`: The username of the user.
        - `role`: The role id.
    - **Optional:**
        - `credits`: The credits of the user.
- **Example:**

    ```json
    {
        "id": 1,
        "email": "updateduser@example.com",
        "username": "Updated User",
        "credits": 150,
        "role": 1
    }
    ```

### Voc Input Models

#### Save Voc Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `description`: The description of the voc.
        - `approved`: The approval status of the voc.
        - `section`: The section id.
        - `started`: The start time of the voc.
        - `ended`: The end time of the voc.
    - **Optional:**
        - `user`: The user id.
- **Example:**

    ```json
    {
        "description": "Description A",
        "approved": true,
        "user": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z"
    }
    ```

#### Update Voc Input Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the voc.
        - `description`: The description of the voc.
        - `approved`: The approval status of the voc.
        - `section`: The section id.
        - `started`: The start time of the voc.
        - `ended`: The end time of the voc.
    - **Optional:**
        - `user`: The user id.
- **Example:**

    ```json
    {
        "id": 1,
        "description": "Updated Description",
        "approved": true,
        "user": 1,
        "section": 1,
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z"
    }
    ```



## Output Models

### Department Output Models

#### Get All Departments Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `departments`: List of departments.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "departments": [
            {
                "id": 1,
                "name": "Department A",
                "fieldsStudy": [
                    {
                        "id": 1,
                        "name": "Field Study A",
                        "modules": [
                            {
                                "id": 1,
                                "name": "Module A",
                                "tier": 1
                            }
                        ]
                    }
                ]
            },
            {
                "id": 2,
                "name": "Department B",
                "fieldsStudy": [
                    {
                        "id": 2,
                        "name": "Field Study B",
                        "modules": [
                            {
                                "id": 2,
                                "name": "Module B",
                                "tier": 2
                            }
                        ]
                    }
                ]
            }
        ],
        "size": 2
    }
    ```

#### Get Department Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the department.
        - `name`: The name of the department.
        - `fieldsStudy`: The list of field studies.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Department A",
        "fieldsStudy": [
            {
                "id": 1,
                "name": "Field Study A",
                "modules": [
                    {
                        "id": 1,
                        "name": "Module A",
                        "tier": 1
                    }
                ]
            }
        ]
    }
    ```

#### Save Department Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created department.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update Department Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated department.
        - `name`: The name of the department.
        - `fieldsStudy`: The list of field studies.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Department",
        "fieldsStudy": [
            {
                "id": 1,
                "name": "Updated Field Study",
                "modules": [
                    {
                        "id": 1,
                        "name": "Updated Module",
                        "tier": 1
                    }
                ]
            }
        ]
    }
    ```

### Field Study Output Models

#### Get All Field Studies Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `fieldsStudy`: List of field studies.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "fieldsStudy": [
            {
                "id": 1,
                "name": "Field Study A",
                "modules": [
                    {
                        "id": 1,
                        "name": "Module A",
                        "tier": 1
                    }
                ]
            },
            {
                "id": 2,
                "name": "Field Study B",
                "modules": [
                    {
                        "id": 2,
                        "name": "Module B",
                        "tier": 2
                    }
                ]
            }
        ],
        "size": 2
    }
    ```

#### Get Field Study Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the field study.
        - `name`: The name of the field study.
        - `modules`: The list of modules.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Field Study A",
        "modules": [
            {
                "id": 1,
                "name": "Module A",
                "tier": 1
            }
        ]
    }
    ```

#### Save Field Study Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created field study.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update Field Study Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated field study.
        - `name`: The name of the field study.
        - `modules`: The list of modules.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Field Study",
        "modules": [
            {
                "id": 1,
                "name": "Updated Module",
                "tier": 1
            }
        ]
    }
    ```

### Module Output Models

#### Get All Modules Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `modules`: List of modules.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "modules": [
            {
                "id": 1,
                "name": "Module A",
                "tier": 1
            },
            {
                "id": 2,
                "name": "Module B",
                "tier": 2
            }
        ],
        "size": 2
    }
    ```

#### Get Module Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the module.
        - `name`: The name of the module.
        - `tier`: The tier of the module.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Module A",
        "tier": 1
    }
    ```

#### Save Module Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created module.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update Module Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated module.
        - `name`: The name of the module.
        - `tier`: The tier of the module.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Module",
        "tier": 1
    }
    ```

### Section Output Models

#### Get All Sections Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `sections`: List of sections.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "sections": [
            {
                "id": 1,
                "name": "Section A",
                "module": {
                    "id": 1,
                    "name": "Module A",
                    "tier": 1
                },
                "students": [
                    {
                        "id": 1,
                        "name": "User A",
                        "description": "Description A"
                    }
                ]
            },
            {
                "id": 2,
                "name": "Section B",
                "module": {
                    "id": 2,
                    "name": "Module B",
                    "tier": 2
                },
                "students": [
                    {
                        "id": 2,
                        "name": "User B",
                        "description": "Description B"
                    }
                ]
            }
        ],
        "size": 2
    }
    ```

#### Get Section Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the section.
        - `name`: The name of the section.
        - `module`: The module of the section.
        - `students`: The list of students.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Section A",
        "module": {
            "id": 1,
            "name": "Module A",
            "tier": 1
        },
        "students": [
            {
                "id": 1,
                "name": "User A",
                "description": "Description A"
            }
        ]
    }
    ```

#### Save Section Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created section.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update Section Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated section.
        - `name`: The name of the section.
        - `module`: The module of the section.
        - `students`: The list of students.
- **Example:**

    ```json
    {
        "id": 1,
        "name": "Updated Section",
        "module": {
            "id": 1,
            "name": "Updated Module",
            "tier": 1
        },
        "students": [
            {
                "id": 1,
                "name": "Updated User",
                "description": "Updated Description"
            }
        ]
    }
    ```

### Tech Output Models

#### Get All Techs Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `techs`: List of techs.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "techs": [
            {
                "id": 1,
                "teacher": {
                    "id": 1,
                    "name": "User A",
                    "description": "Description A"
                },
                "section": {
                    "id": 1,
                    "name": "Section A",
                    "module": {
                        "id": 1,
                        "name": "Module A",
                        "tier": 1
                    },
                    "students": [
                        {
                            "id": 1,
                            "name": "User A",
                            "description": "Description A"
                        }
                    ]
                },
                "started": "2023-01-01T10:00:00Z",
                "ended": "2023-01-01T12:00:00Z",
                "summary": "Summary A",
                "missTech": [
                    {
                        "id": 1,
                        "name": "User A",
                        "description": "Description A"
                    }
                ]
            },
            {
                "id": 2,
                "teacher": {
                    "id": 2,
                    "name": "User B",
                    "description": "Description B"
                },
                "section": {
                    "id": 2,
                    "name": "Section B",
                    "module": {
                        "id": 2,
                        "name": "Module B",
                        "tier": 2
                    },
                    "students": [
                        {
                            "id": 2,
                            "name": "User B",
                            "description": "Description B"
                        }
                    ]
                },
                "started": "2023-01-01T10:00:00Z",
                "ended": "2023-01-01T12:00:00Z",
                "summary": "Summary B",
                "missTech": [
                    {
                        "id": 2,
                        "name": "User B",
                        "description": "Description B"
                    }
                ]
            }
        ],
        "size": 2
    }
    ```

#### Get Tech Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the tech.
        - `teacher`: The teacher of the tech.
        - `section`: The section of the tech.
        - `started`: The start time of the tech.
        - `ended`: The end time of the tech.
        - `summary`: The summary of the tech.
        - `missTech`: The list of missing students.
- **Example:**

    ```json
    {
        "id": 1,
        "teacher": {
            "id": 1,
            "name": "User A",
            "description": "Description A"
        },
        "section": {
            "id": 1,
            "name": "Section A",
            "module": {
                "id": 1,
                "name": "Module A",
                "tier": 1
            },
            "students": [
                {
                    "id": 1,
                    "name": "User A",
                    "description": "Description A"
                }
            ]
        },
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z",
        "summary": "Summary A",
        "missTech": [
            {
                "id": 1,
                "name": "User A",
                "description": "Description A"
            }
        ]
    }
    ```

#### Save Tech Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created tech.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update Tech Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated tech.
        - `teacher`: The teacher of the tech.
        - `section`: The section of the tech.
        - `started`: The start time of the tech.
        - `ended`: The end time of the tech.
        - `summary`: The summary of the tech.
        - `missTech`: The list of missing students.
- **Example:**

    ```json
    {
        "id": 1,
        "teacher": {
            "id": 1,
            "name": "Updated User",
            "description": "Updated Description"
        },
        "section": {
            "id": 1,
            "name": "Updated Section",
            "module": {
                "id": 1,
                "name": "Updated Module",
                "tier": 1
            },
            "students": [
                {
                    "id": 1,
                    "name": "Updated User",
                    "description": "Updated Description"
                }
            ]
        },
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z",
        "summary": "Updated Summary",
        "missTech": [
            {
                "id": 1,
                "name": "Updated User",
                "description": "Updated Description"
            }
        ]
    }
    ```

### User Output Models

#### Get All Users Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `users`: List of users.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "users": [
            {
                "id": 1,
                "email": "usera@example.com",
                "username": "User A",
                "credits": 100,
                "role": {
                    "id": 1,
                    "name": "Role A"
                }
            },
            {
                "id": 2,
                "email": "userb@example.com",
                "username": "User B",
                "credits": 200,
                "role": {
                    "id": 2,
                    "name": "Role B"
                }
            }
        ],
        "size": 2
    }
    ```

#### Get User Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the user.
        - `email`: The email of the user.
        - `username`: The username of the user.
        - `credits`: The credits of the user.
        - `role`: The role of the user.
- **Example:**

    ```json
    {
        "id": 1,
        "email": "usera@example.com",
        "username": "User A",
        "credits": 100,
        "role": {
            "id": 1,
            "name": "Role A"
        }
    }
    ```

#### Save User Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created user.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update User Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated user.
        - `email`: The email of the user.
        - `username`: The username of the user.
        - `credits`: The credits of the user.
        - `role`: The role of the user.
- **Example:**

    ```json
    {
        "id": 1,
        "email": "updateduser@example.com",
        "username": "Updated User",
        "credits": 150,
        "role": {
            "id": 1,
            "name": "Updated Role"
        }
    }
    ```

#### Get User Logs Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `logs`: List of logs associated with the user.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "logs": [
            {
                "date": "2023-01-01T10:00:00Z",
                "description": "Login",
                "value": 10
            },
            {
                "date": "2023-01-01T12:00:00Z",
                "description": "Logout",
                "value": 0
            }
        ],
        "size": 2
    }
    ```

### Voc Output Models

#### Get All Vocs Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `vocs`: List of vocs.
        - `size`: The size of the list.
- **Example:**

    ```json
    {
        "vocs": [
            {
                "id": 1,
                "description": "Voc A",
                "approved": true,
                "user": {
                    "id": 1,
                    "name": "User A",
                    "description": "Description A"
                },
                "section": {
                    "id": 1,
                    "name": "Section A",
                    "module": {
                        "id": 1,
                        "name": "Module A",
                        "tier": 1
                    },
                    "students": [
                        {
                            "id": 1,
                            "name": "User A",
                            "description": "Description A"
                        }
                    ]
                },
                "started": "2023-01-01T10:00:00Z",
                "ended": "2023-01-01T12:00:00Z"
            },
            {
                "id": 2,
                "description": "Voc B",
                "approved": false,
                "user": {
                    "id": 2,
                    "name": "User B",
                    "description": "Description B"
                },
                "section": {
                    "id": 2,
                    "name": "Section B",
                    "module": {
                        "id": 2,
                        "name": "Module B",
                        "tier": 2
                    },
                    "students": [
                        {
                            "id": 2,
                            "name": "User B",
                            "description": "Description B"
                        }
                    ]
                },
                "started": "2023-01-01T10:00:00Z",
                "ended": "2023-01-01T12:00:00Z"
            }
        ],
        "size": 2
    }
    ```

#### Get Voc Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the voc.
        - `description`: The description of the voc.
        - `approved`: The approval status of the voc.
        - `user`: The user associated with the voc.
        - `section`: The section associated with the voc.
        - `started`: The start time of the voc.
        - `ended`: The end time of the voc.
- **Example:**

    ```json
    {
        "id": 1,
        "description": "Voc A",
        "approved": true,
        "user": {
            "id": 1,
            "name": "User A",
            "description": "Description A"
        },
        "section": {
            "id": 1,
            "name": "Section A",
            "module": {
                "id": 1,
                "name": "Module A",
                "tier": 1
            },
            "students": [
                {
                    "id": 1,
                    "name": "User A",
                    "description": "Description A"
                }
            ]
        },
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z"
    }
    ```

#### Save Voc Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the newly created voc.
- **Example:**

    ```json
    {
        "id": 1
    }
    ```

#### Update Voc Output Model

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `id`: The id of the updated voc.
        - `description`: The description of the voc.
        - `approved`: The approval status of the voc.
        - `user`: The user associated with the voc.
        - `section`: The section associated with the voc.
        - `started`: The start time of the voc.
        - `ended`: The end time of the voc.
- **Example:**

    ```json
    {
        "id": 1,
        "description": "Updated Voc",
        "approved": true,
        "user": {
            "id": 1,
            "name": "Updated User",
            "description": "Updated Description"
        },
        "section": {
            "id": 1,
            "name": "Updated Section",
            "module": {
                "id": 1,
                "name": "Updated Module",
                "tier": 1
            },
            "students": [
                {
                    "id": 1,
                    "name": "Updated User",
                    "description": "Updated Description"
                }
            ]
        },
        "started": "2023-01-01T10:00:00Z",
        "ended": "2023-01-01T12:00:00Z"
    }
    ```

## Error Handling

## Error Handling

### Invalid Name

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 400,
        "title": "Invalid Name",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/name-incorrect"
    }
    ```

### Department Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "Department Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/department-not-found"
    }
    ```

### Department Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "Department Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/department-already-exists"
    }
    ```

### Field Study Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "Field Study Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/field-study-not-found"
    }
    ```

### Field Study Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "Field Study Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/field-study-already-exists"
    }
    ```

### Module Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "Module Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/module-not-found"
    }
    ```

### Module Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "Module Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/module-already-exists"
    }
    ```

### Section Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "Section Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/section-not-found"
    }
    ```

### Section Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "Section Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/section-already-exists"
    }
    ```

### Tech Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "Tech Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/tech-not-found"
    }
    ```

### Tech Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "Tech Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/tech-already-exists"
    }
    ```

### Invalid Schedule

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 400,
        "title": "Invalid Schedule",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/invalid-schedule"
    }
    ```

### User Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "User Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/user-not-found"
    }
    ```

### User Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "User Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/user-already-exists"
    }
    ```

### Voc Not Found

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 404,
        "title": "Voc Not Found",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/voc-not-found"
    }
    ```

### Voc Already Exists

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 409,
        "title": "Voc Already Exists",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/voc-already-exists"
    }
    ```

### Unexpected Error

- **Type:** `application/json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
- **Example:**

    ```json
    {
        "status": 500,
        "title": "Unexpected Error",
        "type": "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/unexpected-error"
    }
    ```


### Problem Details

- **Type:** `application/problem+json`
- **Attributes:**
    - **Required:**
        - `status`: The HTTP status code of the error.
        - `title`: The title of the error.
        - `type`: A URI reference that identifies the problem type.
