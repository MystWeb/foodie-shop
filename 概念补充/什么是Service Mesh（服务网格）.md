# 什么是Service Mesh（服务网格）

Service Mesh（服务网格）是由Buoyant公司的CEO William Morgan发起，目标为解决微服务之间复杂的链路关系。

Service Mesh将程序开发的网络功能和程序本身解耦，网络功能下沉到基础架构，由服务网格实现服务之间的负载均衡等功能，并且除网络功能：**负载均衡、服务发现、熔断降级、动态路由、故障注入、错误重试、安全通信、语言无关** 外，也提供了其它更高级的功能，比如全链路加密、监控、链路追踪等

## Service Mesh 产品

- Linkerd：Buoyant公司在2016年率先开源的高性能网络代理程序，它的出现标志着ServiceMesh时代的开始
- **Envoy**：同Linkerd一样，Envoy也是一款高性能的网络代理程序，为云原生应用而设计
- **Istio**：受Google、IBM、Lyft及RedHat等公司的大力支持和推广，于2017年5月发布，底层为Envoy
- Conduit：2017年12月发布，是Buoyant公司的第二款Service Mesh产品根据Linkerd在生产线上的实际使用经验而设计，并以最小复杂性作为设计基础
- **Kuma**：由Kong开发并提供支持，一个通用的现代服务网格控制平面，基于Envoy构建。Kuma高效的数据平面和先进的控制平面，极大地降低了各团队使用的难度



## Istio控制平台和数据平面

### Istio数据平面-Envoy

Envoy：Istio的数据平面使用的是Envoy，Envoy是以C++开发的高性能代理，用于调解服务网格中所有服务的所有入站和出站流量。Envoy代理是唯一一个与数据平面流量交互的Istio组件。

- 动态服务发现
- 负载均衡
- HTTP/2 & gRPC代理
- 熔断器
- 健康检查、基于百分比流量拆分的灰度发布
- 故障注入
- 丰富的度量指标

### Istio控制平面-Istiod

Istiod为Istio的控制平面，提供服务发现、配置、证书管理、加密通信和认证。早期的Istio控制平台并没有Istiod这个容器，而是由Mixer（新版本已废弃）、Pilot、 Citadel共同组成，后来为了简化Istio的架构，将其合并为Istiod，所以对于新版本的Istio（v1.5+），部署后仅能看到Istiod一类Pod。

- Pilot：为Envoy Sidecar提供服务发现的功能，为智能路由（例如A/B测试、金丝雀部署等）和弹性（超时、重试、熔断器等）提供流量管理功能。
- Citadel：通过内置身份和凭证管理可以提供强大的服务与服务之间的最终用户身份验证，可用于升级服务网格中未加密的流量。
- Galley：负责配置管理的组件，用于验证配置信息的格式和正确性。Galley使用网格配置协议（Mesh Configuration Protocol）和其它组件进行配置的交互。

### Istio东西流量管理-VirtualService

VirtualService：VirtualService（虚拟服务）基于Istio和对应平台提供的基本连通性和服务发现能力，将请求路由到对应的目标。每一个VirtualService包含一组路由规则，Istio将每个请求根据路由匹配到指定的目标地址。

[VirtualService配置示例](https://istio.io/latest/docs/concepts/traffic-management/#virtual-service-example)

```yaml
apiVersion: networking.istio.io/v1beta1
kind: VirtualService
metadata:
  name: reviews
spec:
  hosts:
    - reviews
  http:
    - match:
        - headers:
            end-user:
              exact: jason
      route:
        - destination:
            host: reviews
            subset: v2
    - route:
        - destination:
            host: reviews
            subset: v3
```

➢ apiVersion：对应的API版本；

➢ kind：创建的资源类型，和Kubernetes的Service类似；

➢ metadata：元数据，和Kubernetes资源类似，可以定义annotations、labels、name等；

➢ metadata.name：VirtualService的名称；

➢ spec：spec是关于VirtualService的定义

➢ spec.hosts：VirtualService的主机，即用户指定的目标或路由规则的目标，客户端向服务端发送请求时使用的一个或多个地址。

➢ spec.http：路由规则配置，用来指定流量的路由行为，通过该处的配置将HTTP/1.1、HTTP2和gRPC等流量发送到hosts字段指定的目标。

➢ spec.http[].match：路由规则的条件，可以根据一些条件制定更加细粒度的路由。

➢ route：路由规则，destination字段指定了符合此条件的流量的实际目标地址。

### Istio细粒度流量管理-DestinationRule

DestinationRule：DestinationRule（目标规则）用于后端真实的服务再做进一步
的划分。

[DestinationRule配置示例](https://istio.io/latest/docs/concepts/traffic-management/#destination-rule-example)

```yaml
apiVersion: networking.istio.io/v1beta1
kind: DestinationRule
metadata:
  name: reviews-rule
spec:
  host: reviews
  subsets:
    - name: v1
      labels:
        version: v1
    - name: v2
      labels:
        version: v2
    - name: v3
      labels:
        version: v3
```

➢ apiVersion：对于的API版本；

➢ kind：创建的资源类型，和Kubernetes的Service类似；

➢ metadata：元数据，和Kubernetes资源类似，可以定义annotations、labels、name等；

➢ metadata.name：DestinationRule的名称；

➢ spec：spec是关于DestinationRule的定义

➢ spec.host：DestinationRule的主机，即用户指定的目标或路由规则的目标，客户端向服务端发送请求时使用的地址。

➢ spec.subnets：版本划分，可以将Pod划分为不同的版本，进行细粒度的路由管控。

### Istio南北流量管理-Gateway

Gateway：Istio网关功能，可以使用Gateway在网格最外层接收HTTP/TCP流量，并将流量转发到网格内的某个服务。同时支持出口流量的管控，可以将出口的流量固定从EgressGateway的服务中代理出去。

[Gateway配置示例](https://istio.io/latest/docs/reference/config/networking/gateway/#Server)

```
apiVersion: networking.istio.io/v1beta1
kind: Gateway
metadata:
  name: my-ingress
spec:
  selector:
    app: my-ingressgateway
  servers:
    - port:
        number: 80
        name: http2
        protocol: HTTP2
      hosts:
        - "*"
```

