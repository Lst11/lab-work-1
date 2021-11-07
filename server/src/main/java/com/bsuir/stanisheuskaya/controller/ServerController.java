package com.bsuir.stanisheuskaya.controller;

import com.bsuir.stanisheuskaya.service.Connection;

public interface ServerController {
    void work();

    Connection getConnection();
}
