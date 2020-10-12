package com.example.re_stats_android.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassementModel {
    private  String championnat;
    private List<ClassementData> classement;
    private  String seasons;
}
