package interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 *  该拦截器实现的功能：
 *  统计发送成功和失败的消息个数
 *
 */
public class CountInterceptor implements ProducerInterceptor<String,String> {
    private int success;
    private int fail;
    public static void main(String[] args) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if (e!=null){
            fail++;
        }else {
            success++;
        }
    }

    @Override
    public void close() {
        System.out.println("SUCCESS: "+success);
        System.out.println("FAIL: "+fail);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
