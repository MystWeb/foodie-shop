# Golang 编译后生成的是一个二进制文件，可以直接执行，所以底层镜像设置为 alpine 或者其它的小镜像即可
FROM alpine:3.15
# 如果定义了单独的配置文件，可能需要拷贝到镜像中
COPY conf/ ./conf
# 包名按照实际情况进行修改
COPY ./go-project ./
# 启动该应用
ENTRYPOINT [ "./go-project"]