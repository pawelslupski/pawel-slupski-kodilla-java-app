package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void shouldMapToTask() {
        TaskDto taskDto = new TaskDto(
                1L,
                "task",
                "test object"
        );
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(1L, (long)task.getId());
        assertEquals("task", task.getTitle());
        assertEquals("test object", task.getContent());
        assertTrue(task.getClass().equals(Task.class));
    }

    @Test
    public void shouldMapToTaskDto() {
        Task task = new Task(
                100L,
                "my_task",
                "another tested task"
        );
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(100L, (long)taskDto.getId());
        assertEquals("my_task", taskDto.getTitle());
        assertEquals("another tested task", taskDto.getContent());
        assertTrue(taskDto.getClass().equals(TaskDto.class));
    }

    @Test
    public void shouldMapToTaskDtoList() {
        List<Task>taskList = new ArrayList<>();
        Task task = new Task(
                18L,
                "my_task",
                "test task"
        );
        taskList.add(task);
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertNotNull(taskDtoList);
        assertEquals(1, taskDtoList.size());
        assertEquals(18L, (long)taskDtoList.get(0).getId());
        assertEquals("my_task", taskDtoList.get(0).getTitle());
        assertEquals("test task", taskDtoList.get(0).getContent());
        assertTrue(taskDtoList.get(0).getClass().equals(TaskDto.class));
    }
}
