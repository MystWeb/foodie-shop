package cn.myst.web.tcc.demo.task;

import cn.myst.web.tcc.demo.domain.db131.PaymentMsg;
import cn.myst.web.tcc.demo.mapper.db131.PaymentMsgMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
public class OrderScheduler {
    @Resource
    private PaymentMsgMapper paymentMsgMapper;

    @Scheduled(cron = "0/10 * * * * ?")
    public void orderNotify() throws IOException {
        LambdaQueryWrapper<PaymentMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaymentMsg::getStatus, 0); // 未发送
        List<PaymentMsg> paymentMsgList = paymentMsgMapper.selectList(queryWrapper);
        if (paymentMsgList == null || paymentMsgList.size() == 0) return;

        for (PaymentMsg paymentMsg : paymentMsgList) {
            int order = paymentMsg.getOrderId();

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://localhost:8080/handleOrder");
            NameValuePair orderIdPair = new BasicNameValuePair("orderId", order + "");
            List<NameValuePair> list = new ArrayList<>();
            list.add(orderIdPair);
            HttpEntity httpEntity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            String s = EntityUtils.toString(response.getEntity());
            response.close();

            // 系统更新
            if ("success".equals(s)) {
                paymentMsg.setStatus(1); // 发送成功
            } else {
                Integer failureCount = paymentMsg.getFailureCount();
                failureCount++;
                paymentMsg.setFailureCount(failureCount);
                if (failureCount > 5) {
                    paymentMsg.setStatus(2); // 失败
                }
            }
            paymentMsg.setUpdateTime(new Date());
            paymentMsg.setUpdateUser(0); // 系统更新
            paymentMsgMapper.updateById(paymentMsg);
        }
    }

}