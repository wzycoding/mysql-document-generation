package com.mdg.entity;

import lombok.Data;

/**
 * 数据表字段信息实体类
 */
@Data
public class TableFieldsEntity {
    private String fieldId;
    private String Type;
    private String collation;
    private String isNull;
    private String key;
    private String defaultValue;

}
