package com.mdg.dao;

import com.mdg.entity.TablesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TablesMapper {

    /**
     * 查询所有表的信息通过数据库名
     */
    @Select("select table_name,table_comment from tables where table_schema = #{databaseName}")
    List<TablesEntity> listTablesByDatabaseName(String databaseName);


    /**
     * 查询所有的表字段通过表名
     */
    @Select("SHOW FULL FIELDS FROM ${databaseName}.${tableName}")
    List<Map<String,String>> listTableFieldByTableName(String databaseName, String tableName);

}
