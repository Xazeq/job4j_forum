package ru.job4j.forum.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.SecurityService;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class RegControlTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityService securityService;

    @Test
    @WithMockUser
    public void shouldReturnRegPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    @WithMockUser
    public void whenRegUserWithExistsUsernameThenShouldReturnStatusOk() throws Exception {
        when(securityService.existsByUsername("user")).thenReturn(true);
        this.mockMvc.perform(post("/reg")
                        .param("username", "user")
                        .param("password", "123456"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser
    public void whenRegUserThenShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(post("/reg")
                        .param("username", "user")
                        .param("password", "123456"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(securityService).saveUser(argument.capture());
        assertThat(argument.getValue().getUsername(), is("user"));
    }
}