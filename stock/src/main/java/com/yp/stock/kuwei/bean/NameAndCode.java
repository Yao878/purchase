package com.yp.stock.kuwei.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NameAndCode {
    @NotBlank(message = "code不为空")
    private String code;
    @NotBlank(message = "name不为空")
    private String name;
}
