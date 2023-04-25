
# Field Agent API Assessment

## Tasks

* [x] Update known good state stored procedure
  * [x] Reset and seed security clearances
  * [x] Reset and seed aliases
  * [x] Use Workbench to test the known good state stored procedure

```sql
use field_agent_test;
set SQL_SAFE_UPDATES = 0;
call set_known_good_state();
set SQL_SAFE_UPDATES = 1;
```

* [x] Configure environment variables within IntelliJ's run configurations
  * [x] Spring Boot run configuration
  * [x] JUnit run configuration

* [x] Add the `Alias` model class

* [x] Find an agent with their aliases attached
  * Update the `AgentJdbcTemplateRepository.findById()` method to load aliases for the specified agent
  * Update the `AgentJdbcTemplateRepository.deleteById()` method to delete from the `alias` table

* [x] Implement aliases CRUD
  * [x] Features
    * Add an alias
    * Update an alias
    * Delete an alias
  * [x] Add the `AliasJdbcTemplateRepository` class
    * `List<Alias> findByName(String name)` (to support alias validation)
    * `Alias add(Alias alias)`
    * `boolean update(Alias alias)`
    * `boolean deleteById(int aliasId)`
  * [x] Add the `AliasJdbcTemplateRepositoryTest` class
  * [x] Extract the `AliasRepository` interface
  * [x] Add the `AliasService` class
    * `Result<Alias> add(Alias alias)`
    * `Result<Alias> update(Alias alias)`
    * `boolean deleteById(int aliasId)`
  * [x] Add the `AliasServiceTest` class
  * [x] Add the `AliasController` class
    * `@PostMapping` `ResponseEntity<Object> add(@RequestBody Alias alias)`
    * `@PutMapping("/{aliasId}")` `ResponseEntity<Object> update(@PathVariable int aliasId, @RequestBody Alias alias)`
    * `@DeleteMapping("/{aliasId}")` `ResponseEntity<Void> deleteById(@PathVariable int aliasId)`

* [x] Implement security clearance CRUD
  * [x] Features
    * Find all security clearances
    * Find a security clearance by id
    * Add a security clearance
    * Update a security clearance
    * Delete a security clearance (challenge)
  * [x] Add the following methods to the `SecurityClearanceRepository` interface and `SecurityClearanceJdbcTemplateRepository` class
    * `List<SecurityClearance> findAll()`
    * `SecurityClearance findById(int securityClearanceId)`
    * `SecurityClearance add(SecurityClearance securityClearance)`
    * `boolean update(SecurityClearance securityClearance)`
    * `boolean deleteById(int securityClearanceId)`
    * `int getUsageCount(int securityClearanceId)`
  * [x] Update the `SecurityClearanceJdbcTemplateRepositoryTest` class with additional test casess
  * [x] Add the `SecurityClearanceService` class
    * `List<SecurityClearance> findAll()`
    * `SecurityClearance findById(int securityClearanceId)`
    * `Result<SecurityClearance> add(SecurityClearance securityClearance)`
    * `Result<SecurityClearance> update(SecurityClearance securityClearance)`
    * `Result<SecurityClearance> deleteById(int securityClearanceId)`
  * [x] Add the `SecurityClearanceServiceTest` classs
  * [x] Add the `SecurityClearanceController` class
    * `@GetMapping` `List<SecurityClearance> findAll()`
    * `@GetMapping("/{securityClearanceId}")` `ResponseEntity<Object> findById(@PathVariable int securityClearnaceId)`
    * `@PostMapping` `ResponseEntity<Object> add(@RequestBody SecurityClearance securityClearance)`
    * `@PutMapping("/{securityClearanceId}")` `ResponseEntity<Object> update(@PathVariable int securityClearanceId, @RequestBody SecurityClearance securityClearance)`
    * `@DeleteMapping("/{securityClearanceId}")` `ResponseEntity<Object> deleteById(@PathVariable int securityClearanceId)`

* [x] Implement global error handling
  * [x] Return a specific data integrity error message for data integrity issues
  * [x] Return a general error message for issues other than data integrity

* [x] Run through the HTTP test plan and ensure that all test cases pass

* [x] Review the prewritten plan and confirm that I implemented all of the requirements

## Requirements Checklist

* [x] Find all security clearances
* [x] Find a security clearance by id
* [x] Add a security clearance
* [x] Update a security clearance
* [x] Delete a security clearance (challenge)
* [x] Find agent with aliases
* [x] Add an alias
* [x] Update an alias
* [x] Delete an alias
* [x] Global Error Handling (correctly handles data and general errors differently)
* [x] Test data components (all data components are tested with valuable tests)
* [x] Test domain components (all domain components are tested with valuable tests)
* [x] Java Idioms (excellent layering, class design, method responsibilities, and naming)

## Test Plan

_If the trainee followed instructions during kickoff, they should have an HTTP file with a good sequence of events for demonstrating CRUD capabilities._

### Security Clearance

* [x] GET all security clearances
* [x] GET a security clearance by ID
* [x] For GET return a 404 if security clearance is not found
* [x] POST a security clearance
* [x] For POST return a 400 if the security clearance fails one of the domain rules
  * [x] Security clearance name is required
  * [x] Name cannot be duplicated
* [x] PUT an existing security clearance
* [x] For PUT return a 400 if the security clearance fails one of the domain rules
* [x] DELETE a security clearance that is not in use by ID
* [x] For DELETE return a 404 if the security clearance is not found
* [x] For DELETE return a 400 if the security clearance is in use 

### Alias

* [x] GET an agent record with aliases attached
* [x] POST an alias
* [x] For POST return a 400 if the alias fails one of the domain rules
  * [x] Name is required
  * [x] Persona is not required unless a name is duplicated. The persona differentiates between duplicate names.
* [x] PUT an alias
* [x] For PUT return a 400 if the alias fails one of the domain rules
* [x] DELETE an alias by ID
* [x] For DELETE Return a 404 if the alias is not found

### Global Error Handling

* [x] Return a specific data integrity error message for data integrity issues
* [x] Return a general error message for issues other than data integrity
