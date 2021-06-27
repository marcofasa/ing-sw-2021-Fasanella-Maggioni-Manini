# ing-sw-2021-Fasanella-Maggioni-Manini
## Gruppo 08

- ###   10617541    Marco Fasanela ([@marcofasa](https://github.com/marcofasa))<br>marco.fasanella@mail.polimi.it
- ###   10610008    Elia Maggioni ([@Eliaxie](https://github.com/Eliaxie))<br>elia.maggioni@mail.polimi.it
- ###   10625965    Lucas Manini ([@lmanini](https://github.com/lmanini))<br>lucasjose.manini@mail.polimi.it

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model) |
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model) |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/server) |
| GUI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/client/gui) |
| CLI |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/client/cli) |
| Multiple games | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/blob/master/src/main/java/it/polimi/ingsw/server/Server.java)|
| Persistence | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/S0NN1/ing-sw-2020-piemonti-pirovano-sonnino/tree/master/src/main/java/it/polimi/ingsw/model/player/gods/advancedgods) |
|  | [![RED](http://placehold.it/15/f03c15/f03c15)]() |
|  | [![RED](http://placehold.it/15/f03c15/f03c15)]() |

#### Legend
[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented


<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

## Creating the artifacts
In order to create the jar files, it's sufficient to invoke the maven goal package. This will create in the deliverables/jar/ directory 2 jars, (PSP08-Client.jar and PSP08-Server.jar) which are the two starting points of, respectively, the client application and the server application.

## Running the server
In order to run the server application the latest version of Java must be used.
From the command line, setting the current working directory to the directory containing the jar, to run the server the command ```java -jar PSP08-Server.jar``` must be used.
This will create a server application listening on ports 51214.
In order to change on which port the socket server will listen for incoming connections, the ```-SOCKET='port number'``` command line argument can be used.

| TAG | Effect |
|:-----------------------|:------------------------------------:|
| --d | Enable debug mode |
| --no-timeout | Disable heart-beat verification |
| --port port| Change port from 51214 |


## Running the client
In order to run the client application the latest version of Java must be used and the JavaFX 12 libraries must be located somewhere on the computer.
Open a terminal in the same folder of the client jar and type the command ```java -jar PSP08-Client.jar``` to start the application.
The default interface is GUI

| TAG | Effect |
|:-----------------------|:------------------------------------:|
| --d | Enable debug mode |
| --c | Start Client in Command Line Interface |

####Lobby
When a player connects to the server, a checka whether the lobby has been already setup or not is run, in which case the player
is inserted in the lobby with the other players waiting. If the lobby dimensions are still unset, meaning that he is the first player of a new Game, the client is asked to set them.
The game starts when the lobby is full, and a new lobby is made available for other players to join.
