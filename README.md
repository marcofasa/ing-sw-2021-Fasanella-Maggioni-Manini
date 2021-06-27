# ing-sw-2021-Fasanella-Maggioni-Manini
## Gruppo 08

- ###   10617541    Marco Fasanela ([@marcofasa](https://github.com/marcofasa))<br>marco.fasanella@mail.polimi.it
- ###   10610008    Elia Maggioni ([@Eliaxie](https://github.com/Eliaxie))<br>elia.maggioni@mail.polimi.it
- ###   10625965    Lucas Manini ([@lmanini](https://github.com/lmanini))<br>MAILLUCAS@mail.polimi.it

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Local Single Player | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| ----- | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

## Creating the artifacts
In order to create the jar files, it's sufficient to invoke the maven goal package. This will create in the deliverables/jar/ directory 2 jars, (PSP08-Client.jar and PSP08-Server.jar) which are the two starting points of, respectively, the client application and the server application.

## Running the server
In order to run the server application the latest version of Java must be used.
From the command line, setting the current working directory to the directory containing the jar, to run the server the command ```java -jar PSP08-Server.jar``` must be used.
This will create a server application listening on ports 51214.
In order to change on which port the socket server will listen for incoming connections, the ```-SOCKET='port number'``` command line argument can be used.

-> mettere tag

## Running the client
In order to run the client application the latest version of Java must be used and the JavaFX 12 libraries must be located somewhere on the computer.
Open a terminal in the same folder of the client jar and type the command ```java -jar PSP08-Client.jar``` to start the application.

->tag

//spiegazione inizio game
