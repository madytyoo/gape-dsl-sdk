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
GAPE is based on [XText](http://www.eclipse.org/Xtext/) which provides a comfortable development environment integrated with [Eclipse](http://www.eclipse.org/).

## Installation Instructions
GAPE is implemented with XText, so you must have aXtext up and running in order to proceed.
There are two easy ways to get Xtext up and running. A pre-configured Eclipse distribution is available which has already all the necessary plug-ins installed. Alternatively, you can install Xtext into your existing Eclipse by means of the Eclipse update mechanism.

You can find detailed information on [Xtext website](http://www.eclipse.org/Xtext/download.html)

## Plugin installation
Copy the files in the /plugins directory in the Eclipse plugins directory and restart Eclipse. 

## Sample ToDo Application
The sample ToDo Application comes with a Maven pom.xml file. You must add manually the lib/gape-runtime-1.0.0.jar 
to local repository using: 

    mvn install:install-file -Dfile=/home/madytyoo/tmp/myapplication/gape-dsl-2.0.jar -DgroupId=com.mylaensys.gape.dsl.runtime  -DartifactId=gape -Dversion=1.0 -Dpackaging=jar


To build, and start the sample ToDo, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo. Just run the command.

    mvn appengine:devserver
