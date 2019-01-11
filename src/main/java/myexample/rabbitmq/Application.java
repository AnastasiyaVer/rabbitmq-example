package myexample.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public final static String MESSAGE_QUEUE = "message-queue";
    public final static String USER_QUEUE = "user-queue";
    public final static String SONG_QUEUE = "song-queue";
    public final static String MOVIE_QUEUE = "movie-queue";



    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        return rabbitAdmin;
    }

   @Bean
    public RabbitTemplate rabbitTemplate() {
       RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("spring-boot-exchange");
        return rabbitTemplate;
    }

    @Bean
    Queue queueMessage() {
        return new Queue(MESSAGE_QUEUE, false);
    }

    @Bean
    Queue queueUser() {
        return new Queue(USER_QUEUE, false);
    }
    @Bean
    Queue queueSong() {
        return new Queue(SONG_QUEUE, false);
    }

    @Bean
    Queue queueMovie() {
        return new Queue(MOVIE_QUEUE, false);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("spring-boot-exchange");
    }

    @Bean
    public Binding messageBinding(){
        return BindingBuilder.bind(queueMessage()).to(directExchange()).with("message");
    }

    @Bean
    public Binding userBinding(){
        return BindingBuilder.bind(queueUser()).to(directExchange()).with("user");
    }

    @Bean
    public Binding movieBinding(){
        return BindingBuilder.bind(queueMovie()).to(directExchange()).with("movie");
    }

    @Bean
    public Binding songBinding(){
        return BindingBuilder.bind(queueSong()).to(directExchange()).with("song");
    }


    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
