package interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 *  该拦截器实现的功能：
 *  将时间戳添加到消息的前面，
 *
 */
public class TimeStampInterceptor implements ProducerInterceptor<String,String> {
    public static void main(String[] args) {

    }

    /**
     * 拦截器的核心处理方法
     * @param record 被拦截处理的消息
     * @return
     */
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        //1.获取消息的Value
        String value = record.value();
        String result = System.currentTimeMillis()+"->"+value;
        //2.重新构建一个新的消息对象
        ProducerRecord<String, String> newRecord =
                new ProducerRecord<>(record.topic(), record.partition(), record.key(), result);
        return newRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
