# PCM modeling using the Processeditor

This document explains how to use the Processeditor to create PCM models.
A PCM-Process can be described by many PCM fragments and one PCM scenario.

## Preparations

Currently you need both, the Processeditor Workbench and the Processeditor Server to model and Save PCM.
You will use the Workbench for modeling and the Server as a global repository.

## PCM Fragments

PCM Fragments are small Business Process models. They can be modeled using a subset of the BPMN-Notation:

* Tasks
* Events
 * Blanko Start-Event
 * Blanko End-Event
* Gateways
 * Parallel Gateway
 * Exclusive Gateway
* Data Objects
* Sequence Flow
* Data Flow

All these elements are offered by the model type PCM Fragment.

### Marking a Task as Global

PCM allows using the same task in more than one fragment. To do so

1. model the Task (in one scenario)
2. Save the model to the repository
3. Right click on the Task and choose *Properties*
4. Set the *global flag*

### Copy and Refer an existing Task

1. In another Fragment right click on any node
2. Choose "Copy and Refer Task"
3. Connect to the server if necessary
4. Choose the Model and the Task you want to refer
5. Click on Ok

## PCM Scenario

A Scenario defines which PCM Fragments are part of one Process.
All PCM Fragments have to be saved on the Server. You can alter the Scenario only by moving the nodes and adding/removing PCM Fragments.

### Defining a PCM Scenario

1. Create a new PCM Scenario Model.
2. Right Click on one of the two nodes
3. Choose Add Fragments
4. Mark all Models you want to add in the left List (CTRL for multi select)
5. click on add than on ok

Now there should be entries for all the fragments (inside green node) and for all their data objects (inside white node).

### Removing a Fragment From an Scenario

1. Right Click on one of the two nodes
2. Choose *Add Fragments*
3. Select all the models you want to remove from the right list
4. Click on *Remove* than click *Ok*

### Select a Data Model

You can select Domain Models published on a server as a data model for a scenario.

1. Right click on the canvas
2. Choose *Properties*
3. Insert the url to the Domain Model in the property *DomainModelURI*

### Select a Data Class for a Data Object

Each data object can be associated to a data class inside a domain model.
If the model should be executable you have to assert, that the classes are part of the domain model assigned to the scenario.

1. Right click on a *Data Object Node*
2. Choose *Properties*
3. Click on the button next to *Data Class*
4. Click on *Fetch*
5. Select a Domain Model
6. Select the Class to be refered
7. Click on Ok

### Set a Termination Condition

If a termination condition is full filled the process is terminated.
Currently only one termination condition consisting of one Data Object in one specific state is possible.

1. Open your Scenario
2. Right Click on the canvas (not the Nodes)
3. Choose *Properties*
4. Fill out the *Termination Data Object* and *Termination State* fields
