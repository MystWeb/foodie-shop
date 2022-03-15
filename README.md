# foodie-shop
## 前言

天天吃货在线购物平台，采用现阶段流行技术实现。

## 项目介绍

foodie-shop项目是一套前后端分离电商系统，包括前台购物系统及用户中心后台系统，基于SpringBoot+MyBatis实现，采用云原生方式部署。购物平台系统包含分类、推荐、搜索、评价、购物车、地址、订单、支付、定时任务、用户中心、订单管理模块。

后端实现springboot+mybatis 结合了elascticsearch+logstash实现与数据库中商品表的实现同步。 采用了redisson（redis客户端）或curator（zookeeper客户端）实现的分布式锁

![image-20210809143510552](README.assets/image-20210809143510552.png)

## 组织结构

```
foodie-shop
├── db -- 数据库文件与数据库pdman原型图
├── foodie-shop-api -- 前台购物系统与用户中心Controller接口
├── foodie-shop-common -- 工具类及通用代码
├── foodie-shop-mapper -- 数据库交互层Mapper
├── foodie-shop-pojo -- entity、bo、vo实体类
├── foodie-shop-search -- 前台商品信息展示的相关接口
├── foodie-shop-service -- Service接口与实现
├── foodie-shop-sso -- 前台用户中心-CAS单点登录Controller接口
├── source -- 源码等文件
├── source├── foodie-frontend -- 购物系统与用户系统前端源码
├── source├── 支付中心源码 -- 需要商户资质才可有效使用
├── Dockerfile -- 容器镜像构建
├── Jenkinsfile -- Jenkins流水线
```

## 必备工具

| title(标题)                                                  | description(描述)                                            |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Java SE Development Kit 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) | Java SE开发工具包 8                                          |
| [Maven](http://maven.apache.org/download.cgi)                | Apache Maven是一个软件项目管理和理解工具。基于项目对象模型（POM）的概念，Maven可以从中央信息管理项目的构建，报告和文档。 |
| [Git](https://git-scm.com/download/win)                      | 分布式版本控制系统                                           |
| [Tomcat](https://tomcat.apache.org/)                         | Apache Tomcat是由Apache Software Foundation（ASF）开发的一个开源Java WEB应用服务器 |
| [PDMan](http://pdman.cn/)                                    | 数据库建模工具，参见db内foodie-dev.pdman.json文件            |

## 软件部署

### Windows

**环境变量**

```wiki
一、 配置JDK环境变量
	1. 系统变量新建：
	变量名：JAVA_HOME
	变量值：D:\Program Files (x86)\Java\jdk
	2. 编辑系统变量 Path
	新建：%JAVA_HOME%\bin
	3. 测试是否配置正确，打开CMD命令行窗口，依次输入：java、javac、Java -version 执行成功并版本号一致即可
二、 配置MAVEN环境变量
	1. 系统变量新建：
	变量名：M2_HOME
	变量值：D:\Program Files\apache-maven
	2. 编辑系统变量 Path
	新建：%M2_HOME%\bin
	3. 测试是否配置正确，打开CMD命令行窗口，输入：mvn -v 执行成功并版本号一致即可
三、 配置TOMCAT环境变量
	1. 系统变量新建：
	变量名：CATALINA_HOME
	变量值：D:\Program Files\apache-tomcat
	2. 编辑系统变量 Path
	新建：%CATALINA_HOME%\bin
	3. 测试是否配置正确，打开CMD命令行窗口
	启动Tomcat命令：startup
	停止Tomcat命令：shutdown.bat
```

> Windows环境启动项目

- 注意：只启动FoodieShopApplication，仅需安装mysql、redis即可
- 前端源码foodie-shop\foodie-shop-frontend\放入apache-tomcat\webapps\
- 前端访问入口：http://localhost:8080/foodie-shop-frontend/
- 后端Swagger UI
  - 商品购物系统：http://localhost:8088/swagger-ui/
  - 用户登录系统：http://localhost:8090/swagger-ui/
  - 商品搜索系统：http://localhost:8091/swagger-ui/


### Linux CentOS 7

#### 系统初始化配置

```sh
添加aliyun yum源：
# 备份源文件
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bak$(date '+%Y%m%d%H%M%S')
# centos 7版本
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo

# 缓存服务器包信息 && 安装epel源
yum makecache && yum -y install epel-release

系统更新（建议执行）：
yum update -y && yum upgrade -y

必备工具安装：
yum install wget jq psmisc vim net-tools telnet yum-utils device-mapper-persistent-data lvm2 git lrzsz unzip zip tree epel-release -y

关闭防火墙（可选执行）：
systemctl stop firewalld && systemctl disable firewalld

关闭selinux：
# 临时关闭
setenforce 0
# 备份文件
cp -p /etc/selinux/config /etc/selinux/config.bak$(date '+%Y%m%d%H%M%S')
# 永久关闭
sed -i 's/enforcing/disabled/' /etc/selinux/config

关闭swap：
# 临时关闭
swapoff -a
# 备份文件
cp -p /etc/fstab /etc/fstab.bak$(date '+%Y%m%d%H%M%S')
# 永久关闭
sed -i "s/\/dev\/mapper\/cl-swap/\#\/dev\/mapper\/cl-swap/g" /etc/fstab

方式二：
vi /etc/fstab
# 注释swap
#/dev/mapper/cl-swap     swap                    swap    defaults        0 0
```

#### 安装docker

```bash
# 设置存储库安装
yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 安装docker
yum -y install docker-ce docker-ce-cli bash-completion

# 设置docker开机自启并且启动docker(C-N)
systemctl enable docker && systemctl start docker
```

- 通过运行`hello-world` 映像来验证是否正确安装了Docker Engine 

```shell
docker run --rm hello-world
```

- Docker镜像加速器

```shell
mkdir -p /etc/docker/

cat <<EOF>> /etc/docker/daemon.json
{
    "registry-mirrors": ["https://hub-mirror.c.163.com"],
    "log-driver": "json-file",
    "log-opts": {
        "max-size": "500m",
        "max-file": "3"
    },
    # 如果启用firewalld，需增加该配置
    "iptables": false
}
EOF

systemctl daemon-reload && systemctl restart docker && systemctl enable docker
```

### Kubernetes

```bash
# 设置node01节点作为构建节点
kubectl label node k8s-node01 build=true
# 设置node01节点宿主机工作目录文件夹权限
[root@k8s-node01 ~]# mkdir -p /opt/workspace/ && chmod -R 777 /opt/workspace/
# 创建部署应用的命名空间
kubectl create ns foodie-shop
# 创建CRI（容器运行时）连接Harbor的Secret
kubectl create secret docker-registry harborkey \
  --docker-server=CHANGE_HERE_FOR_YOUR_HARBOR_URL \
  --docker-username=admin --docker-password=Harbor12345 \
  --docker-email=your@email.com \
  -n foodie-shop
# 创建Maven配置文件的ConfigMap
kubectl create configmap maven-settings.xml \
--from-file=settings.xml \
-n default
```

## 技术选型



## 概念补充

### [Kubernetes是什么](http://docs.kubernetes.org.cn/227.html)

Kubernetes是容器集群管理系统，是一个开源的平台，可以实现容器集群的自动化部署、自动扩缩容、维护等功能。

通过Kubernetes你可以：

- 快速部署应用
- 快速扩展应用
- 无缝对接新的应用功能
- 节省资源，优化硬件资源的使用

我们的目标是促进完善组件和工具的生态系统，以减轻应用程序在公有云或私有云中运行的负担。

**Kubernetes 特点**

- **可移植**：支持公有云，私有云，混合云，多重云（multi-cloud）
- **可扩展**：模块化, 插件化, 可挂载, 可组合
- **自动化**：自动部署，自动重启，自动复制，自动伸缩/扩展

Kubernetes是Google 2014年创建管理的，是Google 10多年大规模容器管理技术Borg的开源版本。

### Service Mesh（服务网格）是什么

Service Mesh（服务网格）是由Buoyant公司的CEO William Morgan发起，目标为解决微服务之间复杂的链路关系。

Service Mesh将程序开发的网络功能和程序本身解耦，网络功能下沉到基础架构，由服务网格实现服务之间的负载均衡等功能，并且除网络功能：**负载均衡、服务发现、熔断降级、动态路由、故障注入、错误重试、安全通信、语言无关** 外，也提供了其它更高级的功能，比如全链路加密、监控、链路追踪等

**Service Mesh 产品**

- Linkerd：Buoyant公司在2016年率先开源的高性能网络代理程序，它的出现标志着ServiceMesh时代的开始
- **Envoy**：同Linkerd一样，Envoy也是一款高性能的网络代理程序，为云原生应用而设计
- **Istio**：受Google、IBM、Lyft及RedHat等公司的大力支持和推广，于2017年5月发布，底层为Envoy
- Conduit：2017年12月发布，是Buoyant公司的第二款Service Mesh产品根据Linkerd在生产线上的实际使用经验而设计，并以最小复杂性作为设计基础
- **Kuma**：由Kong开发并提供支持，一个通用的现代服务网格控制平面，基于Envoy构建。Kuma高效的数据平面和先进的控制平面，极大地降低了各团队使用的难度
