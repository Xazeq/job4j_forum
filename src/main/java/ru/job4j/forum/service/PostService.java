package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAllPosts() {
        return postRepository.findAllPosts();
    }

    public void savePost(Post post) {
        postRepository.savePost(post);
    }

    public void saveComment(Comment comment, int postId) {
        postRepository.saveComment(comment, postId);
    }

    public Post findPostById(int id) {
        return postRepository.findPostById(id);
    }

    public void deletePost(int id) {
        postRepository.deletePost(id);
    }
}
