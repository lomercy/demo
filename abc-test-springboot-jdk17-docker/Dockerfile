#VERSION 1.1.0
#基础镜像为java21
FROM eclipse-temurin:21.0.3_9-jdk

#作者签名
MAINTAINER tarzan "bootystar@qq.com"

# 添加参数 pom里配置的参数
ARG JAR_FILE

# 删除之前的镜像文件
RUN rm -rf /opt/running/app.jar

#拷贝jar包，到容器内的指定位置
ADD ./target/${JAR_FILE}  /opt/running/app.jar

#容器对外映射端口
EXPOSE 80

# 切换到jar包文件夹下
WORKDIR /opt/running/

#运行启动命令
#CMD ["java", "-jar","-Dfile.encoding=UTF-8","app.jar","--spring.profiles.active=test"]
CMD ["java", "-jar","-Dfile.encoding=UTF-8","app.jar"]