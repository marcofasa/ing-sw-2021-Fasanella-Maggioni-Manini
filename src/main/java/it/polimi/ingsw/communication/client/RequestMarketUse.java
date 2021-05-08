package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class RequestMarketUse extends ClientRequest {
    /**
     * Create a RequestMarketUse with specified column or row
     * @param message from 1 to 3 if key is row, from 1 to 4 if key is column
     * @param key either "row" or "column"
     */
    public RequestMarketUse(String message, String key){
        super(message, key);
    }

    /**
     * Create a RequestMarketUse with specified column or row
     * @param message from 1 to 3 if key is row, from 1 to 4 if key is column
     * @param key either "row" or "column"
     * @param timeoutID timeout
     */
    public RequestMarketUse(String message, String key, int timeoutID) {
        super(message, key, timeoutID);
    }

    @Override
    public void read(VirtualClient virtualClient) {

    }
}
