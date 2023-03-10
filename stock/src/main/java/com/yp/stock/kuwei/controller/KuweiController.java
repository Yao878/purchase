package com.yp.stock.kuwei.controller;

import com.yp.stock.area.mapper.AreaMapper;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.kuwei.bean.KuweiAddVo;
import com.yp.stock.kuwei.bean.KuweiQueryPageVo;
import com.yp.stock.kuwei.mapper.KuweiMapper;
import com.yp.stock.kuwei.service.KuweiService;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.storeHouse.mapper.StoreHouseMapper;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 库房库位
 */

@CrossOrigin
//@RequestMapping("kuwei")
@RestController
public class KuweiController {
    @Autowired
    private KuweiService kuweiService;
    @Autowired
    private StoreHouseMapper storeHouseMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private KuweiMapper kuweiMapper;

    /**
     * 库房库位分页查询
     *
     * @param queryPageVo
     * @return
     */
    @RequestMapping("StorageBin/queryPage")
    @ApiOperation(value = "库房库位分页查询")
    public ResultVo queryKuweiPage(@RequestBody KuweiQueryPageVo queryPageVo) {
        System.out.println(queryPageVo);
        return kuweiService.queryKuweiPage(queryPageVo);
    }

    /**
     * 添加库位
     *
     * @param
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "StorageBin/add")
    @ApiOperation(value = "添加库位")
    public ResultVo addKuwei(@RequestBody @Valid KuweiAddVo kuweiAddVo, BindingResult bindingResult) {
        System.out.println(kuweiAddVo);
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return kuweiService.addKuwei(kuweiAddVo);

    }

    /**
     * 修改库位状态
     *
     * @param
     * @return
     */
    @RequestMapping("StorageBin/updateStatus")
    @ApiOperation(value = "修改库位状态")
    public ResultVo updateKuweiStatus(@RequestBody Kuwei kuwei) {
        System.out.println(kuwei);
        return kuweiService.updateKuweiStatus(kuwei);
    }

    /**
     * 修改库位
     *
     * @param kuwei
     * @param bindingResult
     * @return
     */
    @PostMapping("StorageBin/update")
    @ApiOperation(value = "修改库位")
    public ResultVo updateKuwei(@RequestBody Kuwei kuwei, BindingResult bindingResult) {
        System.out.println(kuwei);
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return kuweiService.updateKuwei(kuwei);
    }

    /**
     * 导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("StorageBin/import")
    public ResultVo importFile(MultipartFile file) throws Exception {
        return kuweiService.importFile(file);
    }

    @RequestMapping("storageSection/queryWareHouse")
    public ResultVo queryWareHouse() {
        List<StoreHouse> l = storeHouseMapper.getDown();
        return ResultBuildVo.success(l);
    }

    @RequestMapping("StorageBin/queryStorageSection")
    public ResultVo queryStorageSection(int id) {
        System.out.println("****------------" + id);
        return ResultBuildVo.success(areaMapper.getKuquByKufang(id));

    }

//    @RequestMapping("StorageBin/queryStorageBin")
//    public ResultVo queryStorageBin(int storageSectionId){
//        List<Kuwei> l=kuweiMapper.getKuweiByKuqu(storageSectionId);
//        return ResultBuildVo.success(l);
//    }
}