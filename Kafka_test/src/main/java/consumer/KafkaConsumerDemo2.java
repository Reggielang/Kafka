package consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *kafka消费者 offset提交问题
 * 1.自动提交
 * "enable.auto.commit", "true"
 * auto.commit.interval.ms", "1000"
 *
 *2.手动提交
 * "enable.auto.commit", "false"
 * 1)同步提交
 *
 * 2）异步提交
 *
 */
public class KafkaConsumerDemo2 {
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
            System.out.println("进行下一次消费");
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(2));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("消费到"+record.topic()+
                        ":"+record.partition()+
                        ":"+record.offset()+
                        ":"+record.key()+
                        ":"+record.value());
            }

            //同步提交offset
            //System.out.println("同步提交offset");
            //kafkaConsumer.commitSync();
            //System.out.println();

            //异步提交offset
            kafkaConsumer.commitAsync(new OffsetCommitCallback() {
                //当offset提交完成后，会调用该方法
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
                    if (e != null){
                        System.out.println("提交失败");
                    }else {
                        System.out.println("提交后的结果："+offsets);
                    }

                }
            });
        }
            //关闭消费者对象
        //kafkaConsumer.close();
    }
}
