package io.ilya.epamjuniortask.repository;

import io.ilya.epamjuniortask.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("select t from Task t where (:title is null or lower(t.title) like lower(concat('%',:title,'%')))")
    List<Task> filter(String title);
}
