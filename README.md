# foodie-shop
## 前言

天天吃货在线购物平台，采用现阶段流行技术实现。

## 项目介绍

foodie-shop项目是一套前后端分离电商系统，包括前台购物系统及用户中心后台系统，基于SpringBoot+MyBatis实现，采用Docker容器化部署。购物平台系统包含分类、推荐、搜索、评价、购物车、地址、订单、支付、定时任务、用户中心、订单管理模块。

后端实现springboot+mybatis 结合了elascticsearch+logstash实现与数据库中商品表的实现同步。 采用了redisson（redis客户端）或curator（zookeeper客户端）实现的分布式锁

![image-20210809143510552](README.assets/image-20210809143510552.png)

## 组织结构

```
foodie-shop
├── db -- 数据库文件与数据库pdman原型图
├── foodie-shop-api -- 前天购物系统与用户中心Controller接口
├── foodie-shop-common -- 工具类及通用代码
├── foodie-shop-mapper -- 数据库交互层Mapper
├── foodie-shop-pojo -- entity、bo、vo实体类
├── foodie-shop-service -- Service接口与实现
├── source -- 源码等文件
├── source├── foodie-frontend -- 购物系统与用户系统前端源码
├── source├── 支付中心源码 -- 需要商户资质才可有效使用
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
	变量值：D:\Program Files (x86)\Java\jdk1.8.0_301
	2. 编辑系统变量 Path
	新建：%JAVA_HOME%\bin
	3. 测试是否配置正确，打开CMD命令行窗口，依次输入：java、javac、Java -version 执行成功并版本号一致即可
二、 配置MAVEN环境变量
	1. 系统变量新建：
	变量名：M2_HOME
	变量值：D:\Program Files\apache-maven-3.8.1
	2. 编辑系统变量 Path
	新建：%M2_HOME%\bin
	3. 测试是否配置正确，打开CMD命令行窗口，输入：mvn -v 执行成功并版本号一致即可
三、 配置TOMCAT环境变量
	1. 系统变量新建：
	变量名：CATALINA_HOME
	变量值：D:\Program Files\apache-tomcat-9.0.50
	2. 编辑系统变量 Path
	新建：%CATALINA_HOME%\bin
	3. 测试是否配置正确，打开CMD命令行窗口
	启动Tomcat命令：startup
	停止Tomcat命令：shutdown.bat
```

> Windows环境启动项目

- 注意：只启动FoodieShopApplication，仅需安装mysql、redis即可
- 前端源码foodie-shop\foodie-shop-frontend\放入apache-tomcat-9.0.50\webapps\
- 前端访问入口：http://localhost:8080/foodie-shop-frontend/
- 后端Swagger UI：http://localhost:8088/swagger-ui/

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

# 关闭NetworkManager
systemctl disable NetworkManager && systemctl stop NetworkManager
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
	"iptables": false
}
EOF

systemctl restart docker
```

#### 安装Redis

[Redis configuration](https://redis.io/topics/config)

[Redis 6.2 configuration](https://raw.githubusercontent.com/redis/redis/6.2/redis.conf)

- 创建`redis.conf`配置文件

```sh
mkdir -p /home/foodie-shop/redis-master01/conf
vim /home/foodie-shop/redis-master01/conf/redis.conf

mkdir -p /home/foodie-shop/redis-slave01/conf
vim /home/foodie-shop/redis-slave01/conf/redis.conf

mkdir -p /home/foodie-shop/redis-slave02/conf
cp /home/foodie-shop/redis-slave01/conf/redis.conf /home/foodie-shop/redis-slave02/conf/
```

```sh
# RDB保存机制
save 900 1          #如果1个缓存更新，则15分钟后备份
save 300 10         #如果10个缓存更新，则5分钟后备份
save 60 10000       #如果10000个缓存更新，则1分钟后备份
#save 10 3           # 如果更新3个缓存更新，则10秒后备份

# 设置所有主机都可以连接到redis
bind 0.0.0.0

# 开启AOF
appendonly yes

# redis slave节点配置redis master连接信息
replicaof <masterip> <masterport>
masterauth <master-password>
```

> 不使用自定义配置文件的默认启动方式（无配置文件）：redis-server *:6379

```sh
# redis-master01
docker run -dit -p 6379:6379 --name redis-master01 \
--restart=always --privileged=true \
-v /home/foodie-shop/redis-master01/data:/data \
-v /home/foodie-shop/redis-master01/conf:/usr/local/etc/redis \
redis:6.2 \
redis-server /usr/local/etc/redis/redis.conf --requirepass "proaim@2013"

# redis-slave01
docker run -dit -p 6380:6379 --name redis-slave01 \
--restart=always --privileged=true \
-v /home/foodie-shop/redis-slave01/data:/data \
-v /home/foodie-shop/redis-slave01/conf:/usr/local/etc/redis \
redis:6.2 \
redis-server /usr/local/etc/redis/redis.conf  --requirepass "proaim@2013"

# redis-slave02
docker run -dit -p 6381:6379 --name redis-slave02 \
--restart=always --privileged=true \
-v /home/foodie-shop/redis-slave02/data:/data \
-v /home/foodie-shop/redis-slave02/conf:/usr/local/etc/redis \
redis:6.2 \
redis-server /usr/local/etc/redis/redis.conf  --requirepass "proaim@2013"
```

## 技术选型

