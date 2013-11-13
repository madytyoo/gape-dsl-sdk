## GAPE DSL Software Development Kit

GAPE is a [Domain Specific Language ](http://gape-todo.appspot.com)
for developing and deploying Java applications on [Google App Engine](http://gape-todo.appspot.com).
Clarity is a primary objective, as it impacts design and development; the easier it is to read something
the easier is to find mistakes.
The limited expressiveness of GAPE makes it harder to define wrong things and easier to detect errors,
providing a way to clearly define what-the-system-is (the domain model) and what-the-system-does (functionality),
without comprehensive documentation.
The DSL includes explicit information about what code and object belong to and participate to a use case,
which makes it useful to keep track of the system functionalities.
GAPE is based on [Xtext](http://www.eclipse.org/Xtext/) which provides a comfortable development environment integrated with [Eclipse](http://www.eclipse.org/).

## Installation Instructions
GAPE is implemented with Xtext, so you must have Xtext up and running in order to proceed.
There are two easy ways to get Xtext:
* A pre-configured Eclipse distribution which has all the necessary plug-ins installed. 
* Install Xtext into your existing Eclipse by means of the Eclipse update mechanism.

You can find detailed information on [Xtext website](http://www.eclipse.org/Xtext/download.html)

## Plugin installation
Copy the files from __SDK /plugins__ directory files to the __Eclipse /plugins__ directory, then restart Eclipse. 

## Sample ToDo Application
The sample ToDo Application comes with a Maven pom.xml file. You must manually add the __/lib/gape-runtime-1.0.0.jar__ 
to local repository using: 

    mvn install:install-file -Dfile=/home/madytyoo/tmp/myapplication/gape-dsl-2.0.jar -DgroupId=com.mylaensys.gape.dsl.runtime  -DartifactId=gape -Dversion=1.0 -Dpackaging=jar


To build and start the sample ToDo application, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo. Just run the command.

    mvn appengine:devserver
