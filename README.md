# Pervasive Classes
The purpose of the application is to improve the experience of the students in a university context. 
The main goal is to collect useful data and use them in order to help the students. The sensors used in the project are 
the estimote beacons(www.estimote.com), and date are managed with Parse (www.parse.com). 

# Main Functionalities

- Monitoring classrooms attendance.
- Digital signature presence.
- Collecting classrooms average noise.
- Uploading and downloading teaching material.

#web application compiling and deployment
- to compile and deploy a correct WAR file for the web application portion of the software you need eclipse IDE 
  (version LUNA and above) and add as component the VAADIN plugin, plus the usual packages to work with webApp servers.
  At this point you should safely import from git the project and compile it (all the neede data and classess should be there,plus ivyDe should download the required dependancies) you could then try to build and launch the project in eclipse or export it as a war file and deploy it on a service of your choice, said that it offers apache tomcat as web app container
- If the progect runs but instead of some components you see in the webBrowser windows some strange error messages, this means
  that for some reason the widgetset is broken and need recompiling (vaadin technology is similar to GWT so you need to compile
  the project, compile the css (if you have modified them) and also compile the widgetset if yoo add a new widget or if, for some
  reason, something went wrong during import from git


#Web application description
the web application is designed in order to offer a global view of the information related to classes, courses, and any other statistics such as,
noise levels, classroom attendance percentage, classrooms schedules.

At the moment the webApp is still incomplete and has more the purpose to show what should be the global idea;
Should be said that the core functionalities are indeed implemented, in a more or less intelligent way (as usual there will
always be room for improvements)
Mainly the webApp interfaces with Parse.com services and is designed in such a way to minimize calling to parse services only when is strictly needed 
(objects that are not private such as class schedule for example is shared system wide among all the connected users)

#Web application funcionalities description
-Secure Login screen (mockup only, functionality still not implemented)
-Classroom section:
    Classroom selector (implemented)
    Seats occupancy (interface implemented, logic missing)
    Noise levels history (web application side logic implemented, decoupled from actual data on parse services)
    Actual noise meter (web application side logic implemented, decoupled from actual data on parse services)
    Classroom synthetics (partially implemented)
-Lectures section:
    Past, present and future lessons (implemented)
    Topics list (if present) (implemented)
-Professor section:
  This section will most certainly have some secure mesure to ensure that only professors could access this view and edit only
  the content they should own
    Topics fo lecture editing (implemented with html capabilities, such as hypertext, links and image support)
-Administration section:
    Reserved fo future purposes

# Requirements 

- A modern web browser basically

