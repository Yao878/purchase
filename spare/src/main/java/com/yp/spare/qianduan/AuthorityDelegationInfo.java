package com.yp.spare.qianduan;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaoyue on 2017/7/15.
 */
@Data
public class AuthorityDelegationInfo implements Serializable {

    /*
    委托人id
     */
    private long delegationUserId;

    /*
    委托人ITCode
     */
    private String delegationUserCode;

    /*
    委托结束时间
     */
    private Date delegationEndTime;

    /*
    委托类型
     */
    private int delegationType;

}
