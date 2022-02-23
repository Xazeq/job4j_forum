package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.forum.model.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Override
    @Query("select p from Post p "
            + "order by p.created desc")
    Iterable<Post> findAll();
}
