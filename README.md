# PlanckFood

A Java/Spring training project for [Planck](https://planckdata.com/).

Use the following branches:

## phase-1
- Basic Spring Web project
- Food controller and some (mock) operations 

## phase-2
- Food repository interface
- In-memory repository implementation
- Food controller linked to repository

## phase-3
- HTTP errors implemented using exceptions

## phase-4
- Food service interface and implementation
- All business logic code from the controller moved to the service class
- Food controller linked to service, service linked to repository

## phase-5
- Unit and integration tests
- Deletion API, functionality, and integration tests (using TDD)

## phase-6
- JPA-based H2 DB instead of proprietary in-memory repository

## phase-7
- Postgres instead of H2

## phase-8
- Logging, MDC
