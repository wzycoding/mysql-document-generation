这是一个自动生成mysql数据库设计文档的工具。

在application.yml文件当中配置你的数据库用户名和密码

启动服务器之后以get请求方式访问，并且需要传递一个数据名称的参数，
将会自动在/Users/sql-document.xls，建立数据库文档文件。
http://localhost:8999/api/v1.0/tables/genDocument?databaseName=seckill_data

