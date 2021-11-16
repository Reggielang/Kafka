package consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *kafka消费者 offset重置问题
 * auto.offset.reset= earliest(重置到当前分区的最早的offset) | latest(最后的offset) | none |
 *
 * 重置的情况：1.新的组：当前的消费者组在Kafka中没有消费记录
 * 2.要消费的offset对应的消息已经被删除
 *
 */
public class KafkaConsumerDemo1 {
    public static void main(String[] args) {
        //0.创建配置对象
        Properties props = new Properties();
        //Kafka集群位置
        props.put("bootstrap.servers", "hadoop102:9092");
        //消费者组id
        props.put("group.id", "lisi");
        //自动提交offset
        props.put("enable.auto.commit", "true");
        //offset提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        //k,v的反序列化器
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //重置offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //1.创建消费者对象
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String,String>(props);
        //2.订阅主题
        List<String> topics = new ArrayList<>();
        topics.add("first");
        topics.add("hello");
        //topics.add("second");
        kafkaConsumer.subscribe(topics);
        //3.持续消费数据
        while (true){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(2));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("消费到"+record.topic()+
                        ":"+record.partition()+
                        ":"+record.offset()+
                        ":"+record.key()+
                        ":"+record.value());
            }
        }
            //关闭消费者对象
        //kafkaConsumer.close();
    }
}
