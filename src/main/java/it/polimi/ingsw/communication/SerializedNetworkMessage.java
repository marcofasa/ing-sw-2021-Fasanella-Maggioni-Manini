package it.polimi.ingsw.communication;

import java.io.Serializable;

/**
 * This is the base message sent via socket object reader and writer from both Client and Server
 * <p>
 *      This message is extended by ClientMessage and ServerMessage, which are themselves extended
 *      from all the different messages, Response s and Request s.
 *      This is also the base of the strategy pattern revolving around the read method, which is implemented
 *      in the abstract classes ClientMessage and ServerMessage.
 *      This division has been made to force the client to send only ClientMessage s and vice versa but still
 *      retaining basic timeout functionalities and connection between the two types of messages.
 * </p>
 * <p>
 *     Moreover, this class handles the timeout functionality via setTimeoutID and getTimeoutID.
 *     The TimeoutID is set to a random value when sent to a player using the TimeoutHandler.sendAndWait
 *     method. This method sets the value of the timeout to a value different from his default (-1).
 *     When a Response is sent back from the other side of the Network, his timeoutID is check and if
 *     it matches the one sent, the timeout is verified.
 *     If the timeout has already been reached, it means that the function calling the sendAndWait method
 *     has already moved on (with an exception) and an exception is generated while reading the message.
 * </p>
 */
public abstract class SerializedNetworkMessage implements Serializable {

    private int timeoutID = -1;

    public void setTimeoutID(int timeoutID){
        if(this.timeoutID == -1)
            this.timeoutID = timeoutID;
        else
            throw new RuntimeException("TimeoutID has already been set!");
    }

    public int getTimeoutID() {
        return timeoutID;
    }

}
