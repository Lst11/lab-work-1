package com.bsuir.stanisheuskaya.command.impl;

import com.bsuir.stanisheuskaya.command.Command;
import com.bsuir.stanisheuskaya.controller.ServerController;
import com.bsuir.stanisheuskaya.controller.impl.TcpServerController;
import com.bsuir.stanisheuskaya.service.Connection;
import com.bsuir.stanisheuskaya.util.AvailableToken;

import java.io.IOException;
import java.util.Arrays;

public class EchoCommand extends BaseCommand {
    private ServerController serverController;

    public EchoCommand() {
        Arrays.stream(AvailableToken.values()).forEach(t -> getAvailableTokens().put(t.getName(), t.getRegex()));
        serverController = TcpServerController.getInstance();
    }

    @Override
    public void execute() {
        try {
            String content = getAllTokens().get(AvailableToken.CONTENT.getName());
            if (content != null) {
                Connection tcpConnection = serverController.getConnection();
                tcpConnection.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Command build() {
        return new EchoCommand();
    }
}
