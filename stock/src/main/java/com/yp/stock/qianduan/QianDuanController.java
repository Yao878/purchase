package com.yp.stock.qianduan;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 86151 on 2018/12/17.
 */
@RestController
public class QianDuanController {


    @GetMapping("test")
    public String test() {
        return "test";
    }


    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/loginStatus", method = RequestMethod.GET)
    public ResultVO<LoginStatus> getStatus() {
        List<String> permissions = new ArrayList<>();
        permissions.add("ROLE_security_systemSetting");
        permissions.add("ROLE_security_managementUnit");
        permissions.add("ROLE_security_security_userSetting");
        permissions.add("ROLE_security_personnelOfDepartment");
        permissions.add("ROLE_security_businessCooperationPersonnel");
        permissions.add("ROLE_security_serviceSystemPersonnel");
        permissions.add("ROLE_security_authorityManagement");
        permissions.add("ROLE_security_roleManagement");
        permissions.add("ROLE_security_personnelEmpowerment");
        permissions.add("ROLE_security_roleQuery");
        permissions.add("ROLE_security_security_delegationAuthorization");
        permissions.add("ROLE_security_basicData");
        permissions.add("ROLE_security_maretialArchives");
        permissions.add("ROLE_security_maretialPrice");
        permissions.add("ROLE_security_taxRate");
        permissions.add("ROLE_security_legalPersonFactory");
        permissions.add("ROLE_security_inquiryAndQuote");
        permissions.add("ROLE_security_inquiryList");
        permissions.add("ROLE_security_quotationList");
        permissions.add("ROLE_security_quotationSheet");
        permissions.add("ROLE_security_quotationConfirmation");
        permissions.add("ROLE_security_procurementPlan");
        permissions.add("ROLE_security_materialPlan");
        permissions.add("ROLE_security_planSummary");
        permissions.add("ROLE_security_planDistribution");
        permissions.add("ROLE_security_replaceConfirmation");
        permissions.add("ROLE_security_planExecutionTracking");
        permissions.add("ROLE_security_procurementManagement");
        permissions.add("ROLE_security_materialOrder");
        permissions.add("ROLE_security_poTracking");
        permissions.add("ROLE_security_notificationDelivery");
        permissions.add("ROLE_security_deliveryNotification");
        permissions.add("ROLE_security_preInventory");
        permissions.add("ROLE_security_help");
        permissions.add("ROLE_security_templateDownload");
        permissions.add("ROLE_security_about");


        List<UserLegalPerson> userLegalPersonList = new ArrayList<>();
        UserLegalPerson userLegalPerson = new UserLegalPerson();
        userLegalPerson.setId(1L);
        userLegalPerson.setEnabled(true);
        userLegalPerson.setLegalPersonCode("TC001");
        userLegalPerson.setLegalPersonName("测试法人");
        userLegalPersonList.add(userLegalPerson);
        List<AuthorityDelegationInfo> authorityDelegationInfoList = new ArrayList<>();
        AuthorityDelegationInfo authorityDelegationInfo = new AuthorityDelegationInfo();
        authorityDelegationInfo.setDelegationEndTime(new Date());
        authorityDelegationInfo.setDelegationType(1);
        authorityDelegationInfo.setDelegationUserCode("TC002");
        authorityDelegationInfo.setDelegationUserId(10);
        SupplierVo supplierVo = new SupplierVo();
        supplierVo.setPartnerCode("Test001");
        supplierVo.setPartnerId(10L);
        supplierVo.setPartnerName("测试父类");
        supplierVo.setUserId(10L);

        List<StationVo> stationVoList = new ArrayList<>();
        StationVo stationVo = new StationVo();
        stationVo.setId(1L);
        stationVo.setMasterStationId(2L);
        stationVo.setStationCode("编码");
        stationVo.setStationName("测试编码");
        stationVoList.add(stationVo);
        LoginStatus loginStatus = new LoginStatus(true, "zhaosi");
        loginStatus.setUserId(110L);
        loginStatus.setStaffType(1);
        loginStatus.setPersonName("张三");
        loginStatus.setPermissions(permissions);
        loginStatus.setLegalPersons(userLegalPersonList);
        loginStatus.setAuthorityDelegationInfos(authorityDelegationInfoList);
        loginStatus.setSupplierVo(supplierVo);
        loginStatus.setStationVos(stationVoList);

        return ResultVOBuilder.success(loginStatus);
    }

    @GetMapping("/queryAllIncreasementMaterialCount")
    @CrossOrigin
    public ResultVO queryAllIncreasementMaterialCount() {


        return ResultVOBuilder.success(null);
    }

    @GetMapping("/login")
    @CrossOrigin
    public ResultVO login() {
        return ResultVOBuilder.success(null);
    }
}
