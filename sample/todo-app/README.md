## Sample ToDo Application

Requires [Apache Maven](http://maven.apache.org) 3.0 or greater, and JDK 7+ in order to run.

The sample ToDo Application comes with a Maven pom.xml file. You must manually add the __/lib/gape-runtime-1.0.0.jar__ 
to local repository using: 

    mvn install:install-file -Dfile=/<path-to-sdk>/lib/gape-runtime-1.0.0.jar -DgroupId=com.mylaensys.gape.dsl.runtime  -DartifactId=gape -Dversion=1.0 -Dpackaging=jar

To build and start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine


/opt/apache-maven-3.1.1/bin/mvn install:install-file -Dfile=/home/madytyoo/tmp/myapplication/gape-dsl-2.0.jar -DgroupId=com.mylaensys.gape.dsl -DartifactId=gape -Dversion=2.0 -Dpackaging=jar
