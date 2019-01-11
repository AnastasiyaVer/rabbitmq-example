package myexample.rabbitmq.repository;

import myexample.rabbitmq.domain.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepo extends CrudRepository<Song,Long> {
}
