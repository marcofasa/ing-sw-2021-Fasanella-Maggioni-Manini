package it.polimi.ingsw.client;

import java.util.regex.Pattern;

public class ConnectionInfo {
    private int port = 53124;
    private String ip = "127.0.0.1";
    String nickname;

    public ConnectionInfo(ConnectionInfo connectionInfo) {
        this.port = connectionInfo.getPort();
        this.ip = connectionInfo.getIp();
        this.nickname = connectionInfo.getNickname();
    }

    public ConnectionInfo() {

    }

    public void setPort(int port) throws IllegalPortException {
        /*if ( port > 0 && port < 65535)
            this.port = port;
        else
            throw new IllegalPortException();*/
        this.port = port;

    }

    public void setIP(String ip) throws IllegalAddressException {
        /*Pattern pattern = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        if (pattern.matcher(ip).matches())
            this.ip = ip;
        else
            throw new IllegalAddressException()
            */;
        this.ip = ip;
    }

    public void setNickname(String nickname) throws IllegalNicknameException {
        if (nickname.length() > 1 && nickname.length() < 255)
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

    public String getIp() {
        return ip;
    }
}
