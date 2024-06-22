package com.neonlab.common.models;

import lombok.Data;

@Data
public class UserReportModel {

    private Long totalUserCount;
    private Long totalActiveCount;
    private Long totalInActiveCount;

    public UserReportModel(Long totalUserCount, Long totalActiveCount){
        this.totalUserCount = totalUserCount;
        this.totalActiveCount = totalActiveCount;
        this.totalInActiveCount = totalUserCount - totalActiveCount;
    }

}
