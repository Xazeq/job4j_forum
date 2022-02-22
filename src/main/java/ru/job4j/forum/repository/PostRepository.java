package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Map<Integer, Post> posts = new HashMap<>();
    private final AtomicInteger postId = new AtomicInteger(0);

    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(postId.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void saveComment(Comment comment, int postId) {
        if (posts.containsKey(postId)) {
            comment.setId(posts.get(postId).getCommentId().incrementAndGet());
            posts.get(postId).addComment(comment);
        }
    }

    public List<Post> findAllPosts() {
        return posts.values().stream()
                .sorted((p1, p2) -> p2.getCreated().compareTo(p1.getCreated()))
                .collect(Collectors.toList());
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public void deletePost(int id) {
        posts.remove(id);
    }
}
