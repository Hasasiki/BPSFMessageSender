package com.example.bootbasetest.dto;

import lombok.Data;

import java.util.List;

@Data
public class FormatData {
    private String name;
    List<DataModule> dataModules;
}
