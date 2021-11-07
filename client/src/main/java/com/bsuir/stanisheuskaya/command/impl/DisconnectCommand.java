package com.bsuir.stanisheuskaya.command.impl;

import com.bsuir.stanisheuskaya.command.Command;
import com.bsuir.stanisheuskaya.controller.ClientController;
import com.bsuir.stanisheuskaya.controller.impl.TcpClientController;
import com.bsuir.stanisheuskaya.exception.WrongCommandFormatException;
import com.bsuir.stanisheuskaya.service.Connection;

public class DisconnectCommand extends BaseCommand {
    private ClientController clientController;

    public DisconnectCommand() {
        this.clientController = TcpClientController.getInstance();
    }

    @Override
    public void execute() {
        try {
            checkTokenCount();

            Connection connection = clientController.getConnection();

            if (connection != null) {
                connection.close();
                System.out.println("You've been disconnected from server.");
            } else {
                System.out.println("You're not connected to server.");
            }
        } catch (WrongCommandFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Command build() {
        return new DisconnectCommand();
    }

    private void checkTokenCount() throws WrongCommandFormatException {
        if (getTokens().size() > 0) {
            throw new WrongCommandFormatException("Command hasn't any tokens. See -help");
        }
    }
}