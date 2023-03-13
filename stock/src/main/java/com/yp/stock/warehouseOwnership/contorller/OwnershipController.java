package com.yp.stock.warehouseOwnership.contorller;

import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.BigBean;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.ShipVo;
import com.yp.stock.warehouseOwnership.service.OwnershipService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/8 11:39
 * 版本号:1.0
 */
@RestController
@RequestMapping("ownership")
@CrossOrigin
public class OwnershipController {
    @Autowired
    private OwnershipService ownershipService;

    @RequestMapping("/queryFirstPage")
    @ApiOperation(value = "库房归属第一层分页查询")
    public ResultVo queryFirstPage(@RequestBody ShipVo shipVo) {
        return ownershipService.queryFirstPage(shipVo);
    }


    @RequestMapping("/querySecondPageByStorehouse")
    @ApiOperation(value = "通过库房条件查询第二层分页查询")
    public ResultVo querySecondPageByStorehouse(@RequestBody BigBean bigBean) {
        return ownershipService.querySecondPageByStorehouse(bigBean);
    }


    @RequestMapping("/addOwnership")
    @ApiOperation(value = "添加库房归属")
    public ResultVo addOwnership(@RequestBody Ownership ownership) {
        return ownershipService.addOwnership(ownership);
    }

    @RequestMapping("/queryBusiness")
    @ApiOperation(value = "查询库区业务")
    public ResultVo queryBusiness(@RequestBody String areaCode, String businessCode) {
        return ownershipService.queryBusiness(areaCode, businessCode);
    }

    @RequestMapping("/updateStatus")
    @ApiOperation(value = "修改库区状态")
    public ResultVo updateStatus(@RequestBody Ownership ownership) {
        return ownershipService.updateStatus(ownership);
    }

    @RequestMapping("/update")
    @ApiOperation(value = "修改库位归属")
    public ResultVo update(@RequestBody BigBean bigBean) {
        return ownershipService.update(bigBean);
    }

}
