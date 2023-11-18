package com.example.bootbasetest.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HookStationData {
    private final List<HookData> hooks = new ArrayList<>(4);
}
