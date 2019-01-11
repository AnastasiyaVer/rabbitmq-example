package myexample.rabbitmq.listener;

import myexample.rabbitmq.domain.Message;
import myexample.rabbitmq.domain.Movie;
import myexample.rabbitmq.domain.Song;
import myexample.rabbitmq.domain.UserInfo;
import myexample.rabbitmq.repository.MessageRepo;
import myexample.rabbitmq.repository.MovieRepo;
import myexample.rabbitmq.repository.SongRepo;
import myexample.rabbitmq.repository.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageListener {

    private MessageRepo messageRepo;
    private UserRepo userRepo;
    private SongRepo songRepo;
    private MovieRepo movieRepo;

    private static final Logger log = LogManager.getLogger(MessageListener.class);

    public MessageListener(MessageRepo messageRepo, UserRepo userRepo, SongRepo songRepo,MovieRepo movieRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.songRepo = songRepo;
        this.movieRepo = movieRepo;
    }

    @RabbitListener(queues = "message-queue")
    public void receiveMessage(Map<String, String> message) {
        log.info("Received <" + message + ">");
        Message mes = new Message();
        mes.setTag(message.get("tag"));
        mes.setText(message.get("text"));
        messageRepo.save(mes);
        log.info("Message save...");
    }

    @RabbitListener(queues = "user-queue")
    public void receiveUser(Map<String, String> mes) {
        log.info("Received <" + mes + ">");
        UserInfo us = new UserInfo();
        us.setName(mes.get("name"));
        us.setAddress(mes.get("address"));
        us.setEmail(mes.get("email"));
        userRepo.save(us);
        log.info("UserInfo save...");
    }

    @RabbitListener(queues = "song-queue")
    public void receiveSong(Map<String, String> mes) {
        log.info("Received <" + mes + ">");
        Song song = new Song();
        song.setName(mes.get("name"));
        song.setArtist(mes.get("artist"));
        song.setGenre(mes.get("genre"));
        songRepo.save(song);
        log.info("Song save...");
    }

    @RabbitListener(queues = "movie-queue")
    public void receiveMovie(Map<String, String> mes) {
        log.info("Received <" + mes + ">");
        Movie movie = new Movie();
        movie.setName(mes.get("name"));
        movie.setYear(Integer.valueOf(mes.get("year")));
        movie.setGenre(mes.get("genre"));
        movieRepo.save(movie);
        log.info("Movie save...");
    }
}

