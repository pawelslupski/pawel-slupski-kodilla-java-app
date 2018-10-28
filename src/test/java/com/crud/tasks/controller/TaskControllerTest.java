package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskMapper taskMapper;

    @MockBean
    DbService service;

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        Task task = new Task(
                1L,
                "test task",
                "task content");
        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "task content");

        when(service.getTask(1L)).thenReturn(Optional.ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When&Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1) ))
                .andExpect(jsonPath("$.title", is("test task")))
                .andExpect(jsonPath("$.content", is("task content")));
    }

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
        Task task = new Task(
                1L,
                "test task",
                "task content");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "task content");
        List<TaskDto> taskListDto = new ArrayList<>();
        taskListDto.add(taskDto);

        when(service.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(taskListDto);

        //When&Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test task")))
                .andExpect(jsonPath("$[0].content", is("task content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(
                1L,
                "new task",
                "first created test task");
        Task task = new Task(
                757L,
                "new task",
                "newly created test task");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        //When&Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(
                13L,
                "confirming task",
                "v1 update");
        Task task = new Task(
                757L,
                "new task",
                "newly created test task");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(service.saveTask(task)).thenReturn(task);

        //When&Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(13L)))
                .andExpect(jsonPath("$.title", is("confirming task")))
                .andExpect(jsonPath("$.content", is("v1 update")));
    }
}


