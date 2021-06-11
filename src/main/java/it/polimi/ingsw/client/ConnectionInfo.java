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
        if ( port > 49152 && port < 65535)
            this.port = port;
        else
            throw new IllegalPortException();
    }

    public void setIP(String ip) throws IllegalAddressException {
        Pattern linkRegex = Pattern.compile("[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
        Pattern ipRegex = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        if (linkRegex.matcher(ip).matches() || ipRegex.matcher(ip).matches())
            this.ip = ip;
        else
            throw new IllegalAddressException();
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
