package demo.rabbitmq.starter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import demo.rabbitmq.starter.util.RabbitMqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class Consumer {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @PostConstruct
    private void init(){
        messageListenner();
    }
    private void messageListenner(){
        try {
            final ConnectionFactory factory = new ConnectionFactory();
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RabbitMqUtil.getQueueName(),false,false,false,null);
            //bind queue
            channel.queueBind(RabbitMqUtil.getQueueName(),RabbitMqUtil.getExchange(),RabbitMqUtil.getRoutingKey());
            // consume
            channel.basicConsume(RabbitMqUtil.getQueueName(),true,// false : requeue consumed message   , true : once consumed ignored
                    ((consumerTag,message)->{
                        log.info("new message !! : '{}'", new String(message.getBody(), StandardCharsets.UTF_8));
                    }),//when we consume
                    (consumerTag,sig)->{
                        log.error(sig.getMessage());
                    });//when connection canceled

        }catch (final Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
