package myexample.rabbitmq.repository;

import myexample.rabbitmq.domain.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepo extends CrudRepository<Movie,Long> {
}
