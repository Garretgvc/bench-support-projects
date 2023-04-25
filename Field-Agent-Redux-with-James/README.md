
# Dev10 Field Agent Deluxe

## Tasks

* [x] Add people to the private repo
* [x] Review the plan
* [x] Set up and test the backend
  * [x] Create the database
  * [x] Start the API and send a test HTTP request
* [x] Set up the frontend project
* [x] Stub out routes and components

* [_] Implement Agencies

* [ ] Refactor all fetches to services

* [ ] Update menu to be mobile friendly
* [ ] Update layout to be fluid

_Define more tasks as they're identified_

## Roles

_No roles just yet... the initial version of the API doesn't have any security_

## Project Set Up

* [x] Create a new project
* [x] Add React Router
* [x] Add React Bootstrap
* [x] Stub out top level components
* [x] Create a nav bar
* [x] Add base Bootstrap layout and classes

## Pages

### Agencies

* [x] Display a list of agencies
* [ ] Include button to add an agency
* [ ] Include button to view and delete each agency

### Agency Detail

* [ ] Display the detail for a single agency (no editing)
* [ ] Include button to edit the base agency information
* [ ] Include button to delete the agency

#### Agency Locations

* [ ] Display the agency's locations (if available)
* [ ] Include a button to add a location (for the current agency)
* [ ] Include buttons to edit and delete each location (for the current agency)

#### Agency Agents

* [ ] Displays the agency's agents (if available)
* [ ] Include a button to add an agent (display a modal to enter the agency/agent information?)
* [ ] Include buttons to edit each agency/agent (display a model to update the agency/agent information?)
* [ ] Include buttons to delete each agency/agent
* [ ] Include buttons to edit each agent (for convenience)

### Add/Edit Agency

* [ ] Display a form to add/edit an agency

### Agency Location

* [ ] Display a form to add/edit an agency location

### Agents

* [ ] Display a list of agents
* [ ] Include button to add an agent
* [ ] Include buttons to edit and delete each agent

### Agent Detail and/or Add/Edit Agent

_Demonstrate an alternative approach... allow the user to add/edit all aspects of an agent (including agency relationships and aliases) so the user can save everything at one time_

---

_Might not get to these items..._

### Security Clearances

* [ ] Display a list of security clearances
* [ ] Include button to add a security clearance
* [ ] Include buttons to edit and delete each security clearance
  * Update the API to include a reference count for each security clearance
  * Use this count to enable/disable the delete buttons

### Add/Edit Security Clearance

* [ ] Display a form to add/edit a security clearance

### Missions

_TODO_ implement missions (leave this for associates to implement based upon the patterns shown elsewhere?)

_Note: The API does not implement missions... so the backend would need to be updated to support missions_

### Security

_TODO_ implement security in both the backend and frontend???
