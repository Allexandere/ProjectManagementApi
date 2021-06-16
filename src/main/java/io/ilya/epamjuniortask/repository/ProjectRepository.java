package io.ilya.epamjuniortask.repository;

import io.ilya.epamjuniortask.entity.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Query("select p from Project p where (:title is null or lower(p.title) like lower(concat('%',:title,'%')))")
    List<Project> filter(String title);
}
