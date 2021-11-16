package Flumeinterceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Datavalueinterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取headers
        Map<String, String> headers = event.getHeaders();
        //获取bodys
        String body = new String(event.getBody(), StandardCharsets.UTF_8);
        //判断处理
        if (body.contains("honglang")){
            headers.put("topic","topicA");
        }else if (body.contains("bushi")){
            headers.put("topic","topicB");
        }
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class MyBuilder implements Builder{
        @Override
        public Interceptor build() {
            return new Datavalueinterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
