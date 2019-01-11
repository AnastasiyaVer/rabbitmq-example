package myexample.rabbitmq.repository;

import myexample.rabbitmq.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserInfo,Long> {
}
