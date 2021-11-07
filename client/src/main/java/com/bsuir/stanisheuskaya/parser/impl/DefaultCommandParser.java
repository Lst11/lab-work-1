package com.bsuir.stanisheuskaya.parser.impl;

import com.bsuir.stanisheuskaya.command.Command;
import com.bsuir.stanisheuskaya.exception.CommandNotFoundException;
import com.bsuir.stanisheuskaya.exception.WrongCommandFormatException;
import com.bsuir.stanisheuskaya.parser.CommandParser;
import com.bsuir.stanisheuskaya.util.CommandType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultCommandParser implements CommandParser {
    private static final String CMD_COMMON_REGEX = "^([a-z]+)( -[a-z]+((?==)='[\\w .-:\\\\]+')*)*$";
    private static final int COMMAND_GROUP_INDEX = 1;

    @Override
    public Command handle(String cmd) throws WrongCommandFormatException, CommandNotFoundException {
        Pattern pattern = Pattern.compile(CMD_COMMON_REGEX);
        Matcher matcher = pattern.matcher(cmd);

        if (!matcher.find()) {
            throw new WrongCommandFormatException("Wrong command format.");
        }

        final String commandName = matcher.group(COMMAND_GROUP_INDEX);

        if (CommandType.hasCommand(commandName)) {
            return new TokenParser(commandName).handle(cmd);
        }

        throw new CommandNotFoundException("Wrong command: " + commandName);
    }
}
