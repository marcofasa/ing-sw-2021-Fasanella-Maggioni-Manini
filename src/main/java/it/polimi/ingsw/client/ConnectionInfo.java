package it.polimi.ingsw.client;

import java.util.regex.Pattern;

/**
 * Data class containing the necessary values to the connection setup
 */
public class ConnectionInfo {
    private int port = 53124;
    private String address = "127.0.0.1";
    private String nickname;

    public ConnectionInfo(ConnectionInfo connectionInfo) {
        this.port = connectionInfo.getPort();
        this.address = connectionInfo.getAddress();
        this.nickname = connectionInfo.getNickname();
    }

    public ConnectionInfo() {

    }

    /**
     * set and verifies the port
     * @param port to be set, port > 1024 && port < 65535
     * @throws IllegalPortException thrown if port given is invalid
     */
    public void setPort(int port) throws IllegalPortException {
        if ( port > 1024 && port < 65535)
            this.port = port;
        else
            throw new IllegalPortException();
    }

    /**
     * set and verifies the address
     * @param address to be set. It must be a valid IP or a valid link
     * @throws IllegalAddressException thrown if address given is invalid
     */
    public void setAddress(String address) throws IllegalAddressException {
        Pattern linkRegex = Pattern.compile("[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
        Pattern ipRegex = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        if (linkRegex.matcher(address).matches() || ipRegex.matcher(address).matches())
            this.address = address;
        else
            throw new IllegalAddressException();
    }

    /**
     * set and verifies nickname
     * @param nickname to be set. nickname.length() > 1 && nickname.length() < 30
     * @throws IllegalNicknameException thrown if nickname is invalid
     */
    public void setNickname(String nickname) throws IllegalNicknameException {
        if (nickname.length() > 1 && nickname.length() < 30)
            this.nickname = nickname;
        else
            throw new IllegalNicknameException();
    }

    public int getPort() {
        return port;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }
}
