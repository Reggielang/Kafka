package producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 生产者--同步发送---带回调
 *配置类
 *  CommonClientConfigs:通用的配置类
 *  ProducerConfig：生产者的配置类
 *  ConsumerConfig:消费者的配置类
 *
 *
 */
public class KafkaProducerdemo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //0.创建配置对象
        Properties props = new Properties();

        //kafka集群，broker-list
        props.put("bootstrap.servers", "hadoop102:9092");
        //ack的级别
        props.put("acks", "all");
        //重试次数
        props.put("retries", 3);
        //批次大小
        props.put("batch.size", 16384);
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator缓冲区大小
        props.put("buffer.memory", 33554432); //32M
        //k,v的序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //1.创建生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);

        //2.生产数据
        for (int i = 0; i < 10000; i++) {
            System.out.println("*********开始发送消息***********");
            Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<String, String>("first", "honglang###" + i)
                    , new Callback() {
                        /**
                         * 当消息发送完成后会调用该方法
                         * @param recordMetadata 消息的元数据信息，
                         * @param e 当消息发送过程中，如果抛出异常，会传入到该方法
                         */
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            if (e != null) {
                                System.out.println("消息发送失败" + e.getMessage());
                            } else {
                                System.out.println("消息发送成功" + recordMetadata.topic() +
                                        ":" + recordMetadata.partition() +
                                        ":" + recordMetadata.offset());
                            }
                        }
                    });
            System.out.println("***********消息发送出去了*********");
            //同步发送
            future.get(); //会阻塞当前线程，一直会等到该方法的结果返回为止。

            System.out.println("***********消息发送完成*********");

        }

        // 3.关闭对象
        kafkaProducer.close();
    }
}
