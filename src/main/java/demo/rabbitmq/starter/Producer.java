package demo.rabbitmq.starter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.corba.se.pept.transport.ConnectionCache;
import demo.rabbitmq.starter.util.RabbitMqUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Component
public class Producer {

   private static final Logger log = LoggerFactory.getLogger(Producer.class);

   @PostConstruct
   private void init(){
      final Scanner scanner = new  Scanner(System.in);
      getMessages(scanner);
   }

   private void sendMessage(final String message){
      final ConnectionFactory factory = new ConnectionFactory();
      try(final Connection connection = factory.newConnection()){

         final Channel chanel = connection.createChannel();
         chanel.exchangeDeclare(RabbitMqUtil.getExchange(),RabbitMqUtil.getType());
         //publish message
         chanel.basicPublish(RabbitMqUtil.getExchange(),RabbitMqUtil.getRoutingKey(),false,null,message.getBytes());
      }catch (TimeoutException | IOException e){
         e.printStackTrace();
         log.error(e.getMessage(),e);
      }
   }

   private void getMessages(final  Scanner scanner){
      log.info("write message : ");
      final String ms= scanner.nextLine()+ " "+ LocalDateTime.now();
      sendMessage(ms);
      getMessages(scanner);
   }
}
