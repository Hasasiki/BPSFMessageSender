package com.example.bootbasetest.controller;

import com.example.bootbasetest.dto.HookStationData;
import com.example.bootbasetest.dto.TerminalData;
import com.example.bootbasetest.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
    private final MainService service;

    public MainController(MainService service) {
        this.service = service;
    }

    @PostMapping("/terminal")
    public void test(@RequestBody TerminalData data) {
        service.handler(data);
    }
    @PostMapping("/hook")
    public void test(@RequestBody HookStationData data) {
        service.handler(data);
    }

}
