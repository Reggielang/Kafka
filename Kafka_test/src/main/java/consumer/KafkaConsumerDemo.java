package consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *kafka消费者
 *
 */
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        //0.创建配置对象
        Properties props = new Properties();
        //Kafka集群位置
        props.put("bootstrap.servers", "hadoop102:9092");
        //消费者组id
        props.put("group.id", "suibian");
        //自动提交offset
        props.put("enable.auto.commit", "true");
        //offset提交的间隔
        props.put("auto.commit.interval.ms", "1000");
        //k,v的反序列化器
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

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
