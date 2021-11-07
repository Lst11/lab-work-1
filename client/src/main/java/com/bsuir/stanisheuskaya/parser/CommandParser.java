package com.bsuir.stanisheuskaya.parser;

import com.bsuir.stanisheuskaya.command.Command;
import com.bsuir.stanisheuskaya.exception.CommandNotFoundException;
import com.bsuir.stanisheuskaya.exception.WrongCommandFormatException;

public interface CommandParser {
    Command handle(String cmd) throws WrongCommandFormatException, CommandNotFoundException;

    default Command parse(String cmd) throws WrongCommandFormatException, CommandNotFoundException {
        return handle(cmd);
    }
}
