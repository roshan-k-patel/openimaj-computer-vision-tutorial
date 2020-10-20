# openimaj-computer-vision-tutorial
Following the tutorial to learn how to use the openIMAJ computer vision library: http://openimaj.org/tutorial-pdf.pdf

## Setup Instructions

1.) Install Maven

with homebrew:

```brew install maven```

2.) To create a new OpenIMAJ project, run the following command:

```mvn -DarchetypeGroupId=org.openimaj -DarchetypeArtifactId=openimaj-quickstart-archetype -DarchetypeVersion=1.3.10  archetype:generate```

Maven will then prompt you for some input. For the groupId, enter something that identifies you or a group that you belong to
(for example, I might choose uk.ac.soton.ecs.jsh2 for personal projects, or org.openimaj for OpenIMAJ sub-projects). For
the artifactId enter a name for your project (for example, OpenIMAJ-Tutorial01). The version can be left as 1.0-SNAPSHOT,
and the default package is also OK. Finally enter Y and press return to confirm the settings. Maven will then generate a new project
in a directory with the same name as the artifactId you provided.

3.) Create the target directory and assemble the jar 

```cd OpenIMAJ-Tutorial01```

and run the command:

```mvn assembly:assembly```

4.) Open the project as a maven project in an IDE (some require you to import) and copy the chapter folders to /src/main/java
