# dream-trade
线上交易平台
### 配置&运行
>1、 `使用jdk8`
    2、 `application.yml` 为基础配置 application-* 为各个环境的配置 dev为开发环境 pro为线上环境 test为测试环境  
    3、 BootdoApplication 中的main方法运行  
    4、 

### 规范&介绍
##### 介绍
>1、 项目架构  springboot+mybatis+druid+thymeleaf

#####  规范
>1、 敏捷式开发 每个模块为一个package  
        2、 controller->service-dao 如 customer模块只包括客户相关信息 package `api` 为所有对外提供接口的controller层，所有的对外接口均卸载此package中。  
        3、 controller中 严禁写入过多业务，`HttpServletRequest` `HttpservletResponse` 严禁传入到service层中。  
        4、 多表操作必须要使用事务控制。  
        6、 主键 一律使用Long 包装。  
        7、 对象转换 请使用 `com.dm.trade.common.utils.BeanUtils` 进行转换  
        8、 其它未尽事项 请参考 customer 模块  
### 
    
### 打包  
maven install  为 jar 如果有新增加的 jar 包 则需要找到对应的 jar 包并上传到服务器 /opt/dm_trade/lib 中





