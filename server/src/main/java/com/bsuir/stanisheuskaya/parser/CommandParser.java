package com.bsuir.stanisheuskaya.parser;

import com.bsuir.stanisheuskaya.command.Command;

public interface CommandParser {
    Command handle(String command) throws Exception;

    default Command parse(String command) throws Exception {
        return handle(command);
    }
}
