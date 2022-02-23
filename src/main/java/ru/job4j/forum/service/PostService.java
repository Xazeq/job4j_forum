package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Comment;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.CommentRepository;
import ru.job4j.forum.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> findAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void saveComment(Comment comment, int postId) {
        Post post = postRepository.findById(postId).get();
        post.addComment(comment);
        postRepository.save(post);
    }

    public Post findPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deletePost(int id) {
        postRepository.deleteById(id);
    }
}
