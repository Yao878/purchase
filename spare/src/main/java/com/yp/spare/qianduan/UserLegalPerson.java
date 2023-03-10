package com.yp.spare.qianduan;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhaoyue on 2017/7/15.
 */
@Data
public class UserLegalPerson implements Serializable {

    private Long id;
    private String legalPersonName;
    private String legalPersonCode;
    private Boolean enabled;

}
