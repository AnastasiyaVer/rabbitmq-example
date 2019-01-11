package myexample.rabbitmq.controllers;

import myexample.rabbitmq.domain.Message;
import myexample.rabbitmq.domain.Movie;
import myexample.rabbitmq.domain.Song;
import myexample.rabbitmq.domain.UserInfo;
import myexample.rabbitmq.repository.MessageRepo;
import myexample.rabbitmq.repository.MovieRepo;
import myexample.rabbitmq.repository.SongRepo;
import myexample.rabbitmq.repository.UserRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;
    private UserRepo userRepo;
    private SongRepo songRepo;
    private MovieRepo movieRepo;
    private RabbitTemplate rabbitTemplate;


    public MainController(MessageRepo messageRepo, RabbitTemplate rabbitTemplate, UserRepo userRepo, SongRepo songRepo,
                          MovieRepo movieRepo) {
        this.messageRepo = messageRepo;
        this.rabbitTemplate = rabbitTemplate;
        this.userRepo = userRepo;
        this.songRepo = songRepo;
        this.movieRepo = movieRepo;
    }

    @GetMapping
    public String main(Map<String,Object>model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages",messages);
        Iterable<UserInfo> users = userRepo.findAll();
        model.put("users",users);
        Iterable<Song> songs = songRepo.findAll();
        model.put("songs",songs);
        Iterable<Movie> movies = movieRepo.findAll();
        model.put("movies",movies);
        return "main";
    }

    @PostMapping("message")
    public String addMessage(@RequestParam String text, @RequestParam String tag, Map<String,Object> model){
        Message message = new Message(text,tag);
        Map<String,String>actionmap = new HashMap<>();
        actionmap.put("text",message.getText());
        actionmap.put("tag",message.getTag());
        rabbitTemplate.convertAndSend("message",actionmap);
        main(model);
        return "main";
    }

    @PostMapping("user")
    public String addUser(@RequestParam String name, @RequestParam String address, @RequestParam String email,
                          Map<String,Object> model){
        UserInfo user = new UserInfo(name, address, email);
        Map<String,String>actionmap = new HashMap<>();
        actionmap.put("name",user.getName());
        actionmap.put("address",user.getAddress());
        actionmap.put("email",user.getEmail());
        rabbitTemplate.convertAndSend("user",actionmap);
        main(model);
        return "main";
    }

    @PostMapping("song")
    public String addSong(@RequestParam String name, @RequestParam String artist, @RequestParam String genre,
                          Map<String,Object> model){
        Song song = new Song(name, artist ,genre);
        Map<String,String>actionmap = new HashMap<>();
        actionmap.put("name",song.getName());
        actionmap.put("artist",song.getArtist());
        actionmap.put("genre",song.getGenre());
        rabbitTemplate.convertAndSend("song",actionmap);
        main(model);
        return "main";
    }

    @PostMapping("movie")
    public String addMovie(@RequestParam String name, @RequestParam String year, @RequestParam String genre,
                          Map<String,Object> model){
        Movie movie = new Movie(name, Integer.valueOf(year) ,genre);
        Map<String,String>actionmap = new HashMap<>();
        actionmap.put("name",movie.getName());
        actionmap.put("year",String.valueOf(movie.getYear()));
        actionmap.put("genre",movie.getGenre());
        rabbitTemplate.convertAndSend("movie",actionmap);
        main(model);
        return "main";
    }
}
