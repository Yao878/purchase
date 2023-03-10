package com.yp.stock.qianduan;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplierVo implements Serializable {

    private Long UserId;

    private Long partnerId;

    private String PartnerCode;

    private String PartnerName;
}
