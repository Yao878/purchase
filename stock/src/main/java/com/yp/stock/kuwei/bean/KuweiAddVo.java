package com.yp.stock.kuwei.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 库位新增bean
 */

@Data
@Accessors(chain = true)
public class KuweiAddVo {
    private List<Kuwei> list;

}

