package io.ilya.epamjuniortask.repository;

import io.ilya.epamjuniortask.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where (:name is null or lower(u.name) like lower(concat('%',:name,'%')))")
    List<User> filter(String name);
}
