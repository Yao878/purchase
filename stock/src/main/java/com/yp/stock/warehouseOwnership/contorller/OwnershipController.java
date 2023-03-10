package com.yp.stock.warehouseOwnership.contorller;

import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.QueryPageVo;
import com.yp.stock.warehouseOwnership.bean.SecendPageVo;
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
    public ResultVo queryFirstPage(@RequestBody QueryPageVo queryPageVo) {
        if (queryPageVo.getPageSize() == 0) {
            queryPageVo.setPageSize(10);
        }
        return ownershipService.queryFirstPage(queryPageVo);
    }

    @RequestMapping("/querySecondPage")
    @ApiOperation(value = "库房归属第二层分页查询")
    public ResultVo querySecondPage(@RequestBody SecendPageVo secendPageVo) {
        return ownershipService.querySecondPage(secendPageVo);
    }

    @RequestMapping("/querySecondPageByStorehouse")
    @ApiOperation(value = "通过库房条件查询第二层分页查询")
    public ResultVo querySecondPageByStorehouse(@RequestBody SecendPageVo secendPageVo) {
        return ownershipService.querySecondPageByStorehouse(secendPageVo);
    }

    @RequestMapping("/updateSituation")
    @ApiOperation(value = "修改页面选取信息")
    public ResultVo updateSituation(@RequestBody Ownership ownership) {
        return ownershipService.updateSituation(ownership);
    }

    @RequestMapping("/addOwnership")
    @ApiOperation(value = "添加库房归属")
    public ResultVo addOwnership(@RequestBody Ownership ownership) {
        return ownershipService.addOwnership(ownership);
    }


}
