package demo.rabbitmq.starter.util;

public final class RabbitMqUtil {

    private static final String EXCHANGE = "my-excchange";
    private static final String TYPE = "direct";
    private static final String ROUTING_KEY = "my-routing-key";
    private static final String QUEUE_NAME = "my-queue";

    private RabbitMqUtil(){

    }

    public static String getExchange(){
        return EXCHANGE;
    }
    public static String getType(){
        return TYPE;
    }
    public static String getRoutingKey(){
        return ROUTING_KEY;
    }
    public static String getQueueName(){
        return QUEUE_NAME;
    }

}
