package ru.job4j.forum.controller;

import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class PostControlTest {
    @MockBean
    private PostService posts;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenGetCreatePageThenShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/create"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    public void whenGetUpdatePageThenShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/update?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/edit"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    public void whenGetUpdatePageWithoutIdThenShouldReturnStatus400() throws Exception {
        this.mockMvc.perform(get("/update"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void whenGetPostPageThenShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/post?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/post"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser
    public void whenGetPostPageWithoutIdThenShouldReturnStatus400() throws Exception {
        this.mockMvc.perform(get("/update"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void whenSavePostThenShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(post("/save")
                        .param("name", "Куплю ладу гранту. Дорого."))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).savePost(argument.capture());
        assertThat(argument.getValue().getName(), is("Куплю ладу гранту. Дорого."));
    }
}