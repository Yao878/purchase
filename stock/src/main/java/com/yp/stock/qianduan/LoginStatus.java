package com.yp.stock.qianduan;

import lombok.Data;

import java.util.List;

/**
 * Created by 86151 on 2018/12/17.
 */
@Data
public class LoginStatus {
    private Long userId;
    private List<String> permissions;
    private int staffType;
    private boolean loggedIn;
    private String username;
    private String personName;
    private List<UserLegalPerson> legalPersons;
    private SupplierVo supplierVo;
    private List<StationVo> stationVos;

    private List<AuthorityDelegationInfo> authorityDelegationInfos;

    public LoginStatus(boolean loggedIn, String username) {
        this.loggedIn = loggedIn;
        this.username = username;
    }


}
