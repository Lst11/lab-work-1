package com.bsuir.stanisheuskaya.controller;

import com.bsuir.stanisheuskaya.service.Connection;

public interface ClientController {
    void work();

    Connection getConnection();
}
