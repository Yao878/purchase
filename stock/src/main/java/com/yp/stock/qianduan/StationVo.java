package com.yp.stock.qianduan;

import lombok.Data;

import java.io.Serializable;

@Data
public class StationVo implements Serializable {

    private Long Id;

    private String StationName;

    private String StationCode;

    private Long MasterStationId;
}
