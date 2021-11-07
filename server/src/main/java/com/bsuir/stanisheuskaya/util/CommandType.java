package com.bsuir.stanisheuskaya.util;

import com.bsuir.stanisheuskaya.command.Command;
import com.bsuir.stanisheuskaya.command.impl.DownloadCommand;
import com.bsuir.stanisheuskaya.command.impl.EchoCommand;
import com.bsuir.stanisheuskaya.command.impl.TimeCommand;
import com.bsuir.stanisheuskaya.command.impl.UploadCommand;
import com.bsuir.stanisheuskaya.exception.CommandNotFoundException;

public enum CommandType {
    ECHO("echo", new EchoCommand()),
    TIME("time", new TimeCommand()),
    DOWNLOAD("download", new DownloadCommand()),
    UPLOAD("upload", new UploadCommand());

    private String commandName;
    private Command command;

    CommandType(String commandName, Command command) {
        this.commandName = commandName;
        this.command = command;
    }

    public static Command findCommand(String commandName) throws CommandNotFoundException {
        for (CommandType type : CommandType.values()) {
            if (type.getName().equals(commandName)) {
                return type.getCommand();
            }
        }
        throw new CommandNotFoundException("Cannot find command by name[=" + commandName + "]");
    }

    public static boolean hasCommand(String commandName) {
        for (CommandType type : CommandType.values()) {
            if (type.getName().equals(commandName)) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return commandName;
    }

    public Command getCommand() {
        return command.build();
    }
}