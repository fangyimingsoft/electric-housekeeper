package com.fym.electrichousekeeper.entiry.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarningOverview {

    private String deviceCode;

    private int warningCount;
}
