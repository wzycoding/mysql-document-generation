package com.mdg.entity;

import lombok.Data;

/**
 * 每个表的信息
 */
@Data
public class TablesEntity {
    /**
     * 表的名称
     */
    private String tableName;
    /**
     * 表的注释
     */
    private String tableComment;
}
