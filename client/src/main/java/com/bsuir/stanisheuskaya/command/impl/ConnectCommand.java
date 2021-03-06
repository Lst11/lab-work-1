package com.bsuir.stanisheuskaya.command.impl;

import com.bsuir.stanisheuskaya.controller.impl.TcpClientController;
import com.bsuir.stanisheuskaya.exception.AvailableTokenNotPresentException;
import com.bsuir.stanisheuskaya.exception.WrongCommandFormatException;
import com.bsuir.stanisheuskaya.service.Connection;
import com.bsuir.stanisheuskaya.service.impl.TcpConnection;
import com.bsuir.stanisheuskaya.util.AvailableToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class ConnectCommand extends BaseCommand {
    public ConnectCommand() {
        Arrays.stream(AvailableToken.values()).forEach(t -> getAvailableTokens().put(t.getName(), t.getRegex()));
    }

    @Override
    public void execute() {
        try {
            validateRequired();
            validateTokens();

            String firstKey = String.valueOf(getTokens().keySet().toArray()[0]);
            AvailableToken currentToken = AvailableToken.find(firstKey);

            switch (currentToken) {
                case IP:
                    executeConnect();
                    break;
                case HELP:
                    executeHelp();
                    break;
            }
        } catch (WrongCommandFormatException | AvailableTokenNotPresentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ConnectCommand build() {
        return new ConnectCommand();
    }

    private void validateRequired() throws WrongCommandFormatException {
        Map<String, String> tokens = getTokens();

        if (tokens.size() > 1) {
            throw new WrongCommandFormatException("This command should have only one token.");
        }

        if (tokens.containsKey(AvailableToken.HELP.getName())) {
            return;
        }

        for (AvailableToken t : AvailableToken.values()) {
            if (t.getName().equals("ip") && t.isRequired()) {
                String value = tokens.get(t.getName());

                if (value == null || value.isEmpty()) {
                    throw new WrongCommandFormatException("'" + t.getName() + "' token required. Check -help.");
                }
            }
        }
    }

    private void executeConnect() {
        String address = getTokens().get(AvailableToken.IP.getName());
        Connection tcpConnection = TcpConnection.getInstance();
        tcpConnection.setServerIpAddress(address);
        Properties properties = new Properties();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream("client\\src\\main\\resources\\configuration.xml");
            properties.loadFromXML(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String serverPort = properties.getProperty("server.port");
        tcpConnection.connect(serverPort);
        TcpClientController.getInstance().setConnection(tcpConnection);
        System.out.println("Client was connected to server: " + tcpConnection.getRemoteSocketIpAddress());
    }

    private void executeHelp() {
        System.out.println("Command format:");
        System.out.println("   connect -ip='192.168.0.1' [-help]");
    }
}
