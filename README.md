# The Man Who Was There
## Computer Networks Project

#### Proof Of Concept for the Research Paper Titled: The Man Who Was There: Validating Check-ins in Location-Based Services 

[Link](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.675.4695) to the paper


**Description**
This project was implemented as part of my Bachelor course on Computer Networks. The idea is to detect and prevent user check-ins that are fake. A user making check-ins at too many places in quick succession or attempting a check-in at a place where they are not are few examples of such cases. This system uses a middle layer that connects each "Place" with a "User". The check-in attempt is made by the user to the middle server, the middle server then validates the request based on meta-data of user's current gps location and check-in history and decides if the check-in qualifies as a valid check-in or a (potentially) fake one. Details of implementation and a user walkthrough can be found in the Documentation section of this repository.