package partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区器 需要实现Kafka提供的partitioner接口
 */
public class MyPartitioner implements Partitioner {
    /**
     * 计算分区号
     * 以first主题为例，有两个分区
     * 包含honglang的消息发送0号分区
     * 其他消息发送1号分区
     * @param topic 当前消息发往的主题
     * @param key 当前消息的key
     * @param bytes 当前消息的key序列化后的字节数组
     * @param value 当前消息的值
     * @param valuebytes 当前消息的值序列化后的字节数组
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] valuebytes, Cluster cluster) {
        if(value.toString().contains("honglang")){
            return 0;
        }else {
            return 1;
        }


    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
