
#### 文档地址

- [中文文档](https://www.xuxueli.com/xxl-job/)
- [English Documentation](https://www.xuxueli.com/xxl-job/en/)

#### 源码仓库地址

源码仓库地址 | Release Download
--- | ---
[https://github.com/xuxueli/xxl-job](https://github.com/xuxueli/xxl-job) | [Download](https://github.com/xuxueli/xxl-job/releases)  
[http://gitee.com/xuxueli0323/xxl-job](http://gitee.com/xuxueli0323/xxl-job) | [Download](http://gitee.com/xuxueli0323/xxl-job/releases)


#### 中央仓库地址

```
<!-- http://repo1.maven.org/maven2/com/xuxueli/xxl-job-core/ -->
<dependency>
    <groupId>com.xuxueli</groupId>
    <artifactId>xxl-job-core</artifactId>
    <version>${最新稳定版本}</version>
</dependency>
```


### 1.6 环境
- Maven3+
- Jdk1.8+
- Mysql8.0+


## 二、快速入门

### 2.1 初始化“调度数据库”
请下载项目源码并解压，获取 "调度数据库初始化SQL脚本" 并执行即可。

"调度数据库初始化SQL脚本" 位置为:

    /xxl-job/doc/db/tables_xxl_job.sql

调度中心支持集群部署，集群情况下各节点务必连接同一个mysql实例;

如果mysql做主从,调度中心集群节点务必强制走主库;

### 2.2 编译源码
解压源码,按照maven格式将源码导入IDE, 使用maven进行编译即可，源码结构如下：

    xxl-job-admin：调度中心
    xxl-job-core：公共依赖
    xxl-job-executor-samples：执行器Sample示例（选择合适的版本执行器，可直接使用，也可以参考其并将现有项目改造成执行器）
        ：xxl-job-executor-sample-springboot：Springboot版本，通过Springboot管理执行器，推荐这种方式；
        ：xxl-job-executor-sample-frameless：无框架版本；
        

### 2.3 配置部署“调度中心”

    调度中心项目：xxl-job-admin
    作用：统一管理任务调度平台上调度任务，负责触发调度执行，并且提供任务管理平台。

#### 步骤一：调度中心配置：
调度中心配置文件地址：

    /xxl-job/xxl-job-admin/src/main/resources/application.properties


调度中心配置内容说明：

    ### 调度中心JDBC链接：链接地址请保持和 2.1章节 所创建的调度数据库的地址一致
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai
    spring.datasource.username=root
    spring.datasource.password=root_pwd
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    
    ### 报警邮箱
    spring.mail.host=smtp.qq.com
    spring.mail.port=25
    spring.mail.username=xxx@qq.com
    spring.mail.password=xxx
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    spring.mail.properties.mail.smtp.starttls.required=true
    spring.mail.properties.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
    
    ### 调度中心通讯TOKEN [选填]：非空时启用；
    xxl.job.accessToken=
    
    ### 调度中心国际化配置 [必填]： 默认为 "zh_CN"/中文简体, 可选范围为 "zh_CN"/中文简体, "zh_TC"/中文繁体 and "en"/英文；
    xxl.job.i18n=zh_CN
    
    ## 调度线程池最大线程配置【必填】
    xxl.job.triggerpool.fast.max=200
    xxl.job.triggerpool.slow.max=100
    
    ### 调度中心日志表数据保存天数 [必填]：过期日志自动清理；限制大于等于7时生效，否则, 如-1，关闭自动清理功能；
    xxl.job.logretentiondays=30
    
    

#### 步骤二：部署项目：
如果已经正确进行上述配置，可将项目编译打包部署。

调度中心访问地址：http://localhost:8080/xxl-job-admin (该地址执行器将会使用到，作为回调地址)

默认登录账号 "admin/123456", 登录后运行界面如下图所示。

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_6yC0.png "在这里输入图片标题")

至此“调度中心”项目已经部署成功。

#### 步骤三：调度中心集群（可选）：
调度中心支持集群部署，提升调度系统容灾和可用性。

调度中心集群部署时，几点要求和建议：
- DB配置保持一致；
- 集群机器时钟保持一致（单机集群忽视）；
- 建议：推荐通过nginx为调度中心集群做负载均衡，分配域名。调度中心访问、执行器回调配置、调用API服务等操作均通过该域名进行。


#### 其他：Docker 镜像方式搭建调度中心：

- 下载镜像

```
// Docker地址：https://hub.docker.com/r/xuxueli/xxl-job-admin/     (建议指定版本号)
docker pull xuxueli/xxl-job-admin
```

- 创建容器并运行

```
/**
* 如需自定义 mysql 等配置，可通过 "-e PARAMS" 指定，参数格式 PARAMS="--key=value  --key2=value2" ；
* 配置项参考文件：/xxl-job/xxl-job-admin/src/main/resources/application.properties
* 如需自定义 JVM内存参数 等配置，可通过 "-e JAVA_OPTS" 指定，参数格式 JAVA_OPTS="-Xmx512m" ；
*/
docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://127.0.0.1:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai" -p 8080:8080 -v /tmp:/data/applogs --name xxl-job-admin  -d xuxueli/xxl-job-admin:{指定版本}
```


### 2.4 配置部署“执行器项目”

    “执行器”项目：xxl-job-executor-sample-springboot (提供多种版本执行器供选择，现以 springboot 版本为例，可直接使用，也可以参考其并将现有项目改造成执行器)
    作用：负责接收“调度中心”的调度并执行；可直接部署执行器，也可以将执行器集成到现有业务项目中。
    
#### 步骤一：maven依赖
确认pom文件中引入了 "xxl-job-core" 的maven依赖；
    
#### 步骤二：执行器配置
执行器配置，配置文件地址：

    /xxl-job/xxl-job-executor-samples/xxl-job-executor-sample-springboot/src/main/resources/application.properties

执行器配置，配置内容说明：

    ### 调度中心部署根地址 [选填]：如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；
    xxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin
    
    ### 执行器通讯TOKEN [选填]：非空时启用；
    xxl.job.accessToken=
    
    ### 执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
    xxl.job.executor.appname=xxl-job-executor-sample
    ### 执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
    xxl.job.executor.address=
    ### 执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
    xxl.job.executor.ip=
    ### 执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
    xxl.job.executor.port=9999
    ### 执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
    xxl.job.executor.logpath=/data/applogs/xxl-job/jobhandler
    ### 执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
    xxl.job.executor.logretentiondays=30
    

#### 步骤三：执行器组件配置

执行器组件，配置文件地址：

    /xxl-job/xxl-job-executor-samples/xxl-job-executor-sample-springboot/src/main/java/com/xxl/job/executor/core/config/XxlJobConfig.java

执行器组件，配置内容说明：

```
@Bean
public XxlJobSpringExecutor xxlJobExecutor() {
    logger.info(">>>>>>>>>>> xxl-job config init.");
    XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
    xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
    xxlJobSpringExecutor.setAppname(appname);
    xxlJobSpringExecutor.setIp(ip);
    xxlJobSpringExecutor.setPort(port);
    xxlJobSpringExecutor.setAccessToken(accessToken);
    xxlJobSpringExecutor.setLogPath(logPath);
    xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);

    return xxlJobSpringExecutor;
}
```

#### 步骤四：部署执行器项目：
如果已经正确进行上述配置，可将执行器项目编译打部署，系统提供多种执行器Sample示例项目，选择其中一个即可，各自的部署方式如下。

    xxl-job-executor-sample-springboot：项目编译打包成springboot类型的可执行JAR包，命令启动即可；
    xxl-job-executor-sample-frameless：项目编译打包成JAR包，命令启动即可；
    

至此“执行器”项目已经部署结束。

#### 步骤五：执行器集群（可选）：
执行器支持集群部署，提升调度系统可用性，同时提升任务处理能力。

执行器集群部署时，几点要求和建议：
- 执行器回调地址（xxl.job.admin.addresses）需要保持一致；执行器根据该配置进行执行器自动注册等操作。 
- 同一个执行器集群内AppName（xxl.job.executor.appname）需要保持一致；调度中心根据该配置动态发现不同集群的在线执行器列表。


### 2.5 开发第一个任务“Hello World”       
本示例以新建一个 “GLUE模式(Java)” 运行模式的任务为例。更多有关任务的详细配置，请查看“章节三：任务详解”。
（ “GLUE模式(Java)”的执行代码托管到调度中心在线维护，相比“Bean模式任务”需要在执行器项目开发部署上线，更加简便轻量）

> 前提：请确认“调度中心”和“执行器”项目已经成功部署并启动；

#### 步骤一：新建任务：
登录调度中心，点击下图所示“新建任务”按钮，新建示例任务。然后，参考下面截图中任务的参数配置，点击保存。

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_o8HQ.png "在这里输入图片标题")

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_ZAsz.png "在这里输入图片标题")


#### 步骤二：“GLUE模式(Java)” 任务开发：
请点击任务右侧 “GLUE” 按钮，进入 “GLUE编辑器开发界面” ，见下图。“GLUE模式(Java)” 运行模式的任务默认已经初始化了示例任务代码，即打印Hello World。
（ “GLUE模式(Java)” 运行模式的任务实际上是一段继承自IJobHandler的Java类代码，它在执行器项目中运行，可使用@Resource/@Autowire注入执行器里中的其他服务，详细介绍请查看第三章节）

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_Fgql.png "在这里输入图片标题")

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_dNUJ.png "在这里输入图片标题")

#### 步骤三：触发执行：
请点击任务右侧 “执行” 按钮，可手动触发一次任务执行（通常情况下，通过配置Cron表达式进行任务调度触发）。

#### 步骤四：查看日志： 
请点击任务右侧 “日志” 按钮，可前往任务日志界面查看任务日志。
在任务日志界面中，可查看该任务的历史调度记录以及每一次调度的任务调度信息、执行参数和执行信息。运行中的任务点击右侧的“执行日志”按钮，可进入日志控制台查看实时执行日志。

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_inc8.png "在这里输入图片标题")

在日志控制台，可以Rolling方式实时查看任务在执行器一侧运行输出的日志信息，实时监控任务进度；

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_eYrv.png "在这里输入图片标题")

## 三、任务详解

### 配置属性详细说明：

    基础配置：
        - 执行器：任务的绑定的执行器，任务触发调度时将会自动发现注册成功的执行器, 实现任务自动发现功能; 另一方面也可以方便的进行任务分组。每个任务必须绑定一个执行器, 可在 "执行器管理" 进行设置;
        - 任务描述：任务的描述信息，便于任务管理；
        - 负责人：任务的负责人；
        - 报警邮件：任务调度失败时邮件通知的邮箱地址，支持配置多邮箱地址，配置多个邮箱地址时用逗号分隔；
        
    触发配置：
        - 调度类型：
            无：该类型不会主动触发调度；
            CRON：该类型将会通过CRON，触发任务调度；
            固定速度：该类型将会以固定速度，触发任务调度；按照固定的间隔时间，周期性触发；
            固定延迟：该类型将会以固定延迟，触发任务调度；按照固定的延迟时间，从上次调度结束后开始计算延迟时间，到达延迟时间后触发下次调度；
        - CRON：触发任务执行的Cron表达式；
        - 固定速度：固定速度的时间间隔，单位为秒；
        - 固定延迟：固定延迟的时间间隔，单位为秒；
        
    任务配置：
        - 运行模式：
            BEAN模式：任务以JobHandler方式维护在执行器端；需要结合 "JobHandler" 属性匹配执行器中任务；
            GLUE模式(Java)：任务以源码方式维护在调度中心；该模式的任务实际上是一段继承自IJobHandler的Java类代码并 "groovy" 源码方式维护，它在执行器项目中运行，可使用@Resource/@Autowire注入执行器里中的其他服务；
            GLUE模式(Shell)：任务以源码方式维护在调度中心；该模式的任务实际上是一段 "shell" 脚本；
            GLUE模式(Python)：任务以源码方式维护在调度中心；该模式的任务实际上是一段 "python" 脚本；
            GLUE模式(PHP)：任务以源码方式维护在调度中心；该模式的任务实际上是一段 "php" 脚本；
            GLUE模式(NodeJS)：任务以源码方式维护在调度中心；该模式的任务实际上是一段 "nodejs" 脚本；
            GLUE模式(PowerShell)：任务以源码方式维护在调度中心；该模式的任务实际上是一段 "PowerShell" 脚本；
        - JobHandler：运行模式为 "BEAN模式" 时生效，对应执行器中新开发的JobHandler类“@JobHandler”注解自定义的value值；
        - 执行参数：任务执行所需的参数；     
        
    高级配置：
        - 路由策略：当执行器集群部署时，提供丰富的路由策略，包括；
            FIRST（第一个）：固定选择第一个机器；
            LAST（最后一个）：固定选择最后一个机器；
            ROUND（轮询）：；
            RANDOM（随机）：随机选择在线的机器；
            CONSISTENT_HASH（一致性HASH）：每个任务按照Hash算法固定选择某一台机器，且所有任务均匀散列在不同机器上。
            LEAST_FREQUENTLY_USED（最不经常使用）：使用频率最低的机器优先被选举；
            LEAST_RECENTLY_USED（最近最久未使用）：最久未使用的机器优先被选举；
            FAILOVER（故障转移）：按照顺序依次进行心跳检测，第一个心跳检测成功的机器选定为目标执行器并发起调度；
            BUSYOVER（忙碌转移）：按照顺序依次进行空闲检测，第一个空闲检测成功的机器选定为目标执行器并发起调度；
            SHARDING_BROADCAST(分片广播)：广播触发对应集群中所有机器执行一次任务，同时系统自动传递分片参数；可根据分片参数开发分片任务；
        - 子任务：每个任务都拥有一个唯一的任务ID(任务ID可以从任务列表获取)，当本任务执行结束并且执行成功时，将会触发子任务ID所对应的任务的一次主动调度。
        - 调度过期策略：
            - 忽略：调度过期后，忽略过期的任务，从当前时间开始重新计算下次触发时间；
            - 立即执行一次：调度过期后，立即执行一次，并从当前时间开始重新计算下次触发时间；
        - 阻塞处理策略：调度过于密集执行器来不及处理时的处理策略；
            单机串行（默认）：调度请求进入单机执行器后，调度请求进入FIFO队列并以串行方式运行；
            丢弃后续调度：调度请求进入单机执行器后，发现执行器存在运行的调度任务，本次请求将会被丢弃并标记为失败；
            覆盖之前调度：调度请求进入单机执行器后，发现执行器存在运行的调度任务，将会终止运行中的调度任务并清空队列，然后运行本地调度任务；
        - 任务超时时间：支持自定义任务超时时间，任务运行超时将会主动中断任务；
        - 失败重试次数；支持自定义任务失败重试次数，当任务失败时将会按照预设的失败重试次数主动进行重试；
    
    
    

    
### 3.1 BEAN模式（类形式）

Bean模式任务，支持基于类的开发方式，每个任务对应一个Java类。

- 优点：不限制项目环境，兼容性好。即使是无框架项目，如main方法直接启动的项目也可以提供支持，可以参考示例项目 "xxl-job-executor-sample-frameless"；
- 缺点：
    - 每个任务需要占用一个Java类，造成类的浪费；
    - 不支持自动扫描任务并注入到执行器容器，需要手动注入。

#### 步骤一：执行器项目中，开发Job类：

    1、开发一个继承自"com.xxl.job.core.handler.IJobHandler"的JobHandler类，实现其中任务方法。
    2、手动通过如下方式注入到执行器容器。
    ```
    XxlJobExecutor.registJobHandler("demoJobHandler", new DemoJobHandler());
    ```

#### 步骤二：调度中心，新建调度任务
后续步骤和 "3.2 BEAN模式（方法形式）"一致，可以前往参考。


### 3.2 BEAN模式（方法形式）

Bean模式任务，支持基于方法的开发方式，每个任务对应一个方法。

- 优点：
    - 每个任务只需要开发一个方法，并添加"@XxlJob"注解即可，更加方便、快速。
    - 支持自动扫描任务并注入到执行器容器。
- 缺点：略。

>基于方法开发的任务，底层会生成JobHandler代理，和基于类的方式一样，任务也会以JobHandler的形式存在于执行器任务容器中。

#### 步骤一：执行器项目中，开发Job方法：

    1、任务开发：在Spring Bean实例中，开发Job方法；
    2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
    3、执行日志：需要通过 "log.info" 打印执行日志；
    4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
    
```
// 可参考Sample示例执行器中的 "com.xxl.job.executor.service.jobhandler.SampleXxlJob" ，如下：
@XxlJob("demoJobHandler")
public void demoJobHandler() throws Exception {
    log.info("XXL-JOB, Hello World.");
}
```

#### 步骤二：调度中心，新建调度任务
参考上文“配置属性详细说明”对新建的任务进行参数配置，运行模式选中 "BEAN模式"，JobHandler属性填写任务注解“@XxlJob”中定义的值；

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_ZAsz.png "在这里输入图片标题")

#### 原生内置Bean模式任务
为方便用户参考与快速实用，示例执行器内原生提供多个Bean模式任务Handler，可以直接配置实用，如下：

- demoJobHandler：简单示例任务，任务内部模拟耗时任务逻辑，用户可在线体验Rolling Log等功能；
- shardingJobHandler：分片示例任务，任务内部模拟处理分片参数，可参考熟悉分片任务；
- httpJobHandler：通用HTTP任务Handler；业务方只需要提供HTTP链接等信息即可，不限制语言、平台。示例任务入参如下：
    ```
    url: http://www.xxx.com
    method: get 或 post
    data: post-data
    ```
- commandJobHandler：通用命令行任务Handler；业务方只需要提供命令行即可；如 “pwd”命令；


### 3.3 GLUE模式(Java)
任务以源码方式维护在调度中心，支持通过Web IDE在线更新，实时编译和生效，因此不需要指定JobHandler。开发流程如下：

#### 步骤一：调度中心，新建调度任务：
参考上文“配置属性详细说明”对新建的任务进行参数配置，运行模式选中 "GLUE模式(Java)"；

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_tJOq.png "在这里输入图片标题")

#### 步骤二：开发任务代码：
选中指定任务，点击该任务右侧“GLUE”按钮，将会前往GLUE任务的Web IDE界面，在该界面支持对任务代码进行开发（也可以在IDE中开发完成后，复制粘贴到编辑中）。

版本回溯功能（支持30个版本的版本回溯）：在GLUE任务的Web IDE界面，选择右上角下拉框“版本回溯”，会列出该GLUE的更新历史，选择相应版本即可显示该版本代码，保存后GLUE代码即回退到对应的历史版本；

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_dNUJ.png "在这里输入图片标题")

### 3.4 GLUE模式(Shell)

#### 步骤一：调度中心，新建调度任务   
参考上文“配置属性详细说明”对新建的任务进行参数配置，运行模式选中 "GLUE模式(Shell)"；

#### 步骤二：开发任务代码：
选中指定任务，点击该任务右侧“GLUE”按钮，将会前往GLUE任务的Web IDE界面，在该界面支持对任务代码进行开发（也可以在IDE中开发完成后，复制粘贴到编辑中）。

该模式的任务实际上是一段 "shell" 脚本；

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_iUw0.png "在这里输入图片标题")

### 3.4 GLUE模式(Python)

#### 步骤一：调度中心，新建调度任务   
参考上文“配置属性详细说明”对新建的任务进行参数配置，运行模式选中 "GLUE模式(Python)"；

#### 步骤二：开发任务代码：
选中指定任务，点击该任务右侧“GLUE”按钮，将会前往GLUE任务的Web IDE界面，在该界面支持对任务代码进行开发（也可以在IDE中开发完成后，复制粘贴到编辑中）。

该模式的任务实际上是一段 "python" 脚本；

![输入图片说明](https://www.xuxueli.com/doc/static/xxl-job/images/img_BPLG.png "在这里输入图片标题")

### 3.5 GLUE模式(NodeJS)

#### 步骤一：调度中心，新建调度任务   
参考上文“配置属性详细说明”对新建的任务进行参数配置，运行模式选中 "GLUE模式(NodeJS)"；

#### 步骤二：开发任务代码：
选中指定任务，点击该任务右侧“GLUE”按钮，将会前往GLUE任务的Web IDE界面，在该界面支持对任务代码进行开发（也可以在IDE中开发完成后，复制粘贴到编辑中）。

该模式的任务实际上是一段 "nodeJS" 脚本；

### 3.6 GLUE模式(PHP)
同上

### 3.7 GLUE模式(PowerShell)
同上

