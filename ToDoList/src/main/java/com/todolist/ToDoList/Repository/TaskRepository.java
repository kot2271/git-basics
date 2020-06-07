package com.todolist.ToDoList.Repository;

import com.todolist.ToDoList.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {
  @Query(value = "from Task tasks where tasks.title like concat('%', :title, '%')")
  List<Task> searchByTitle(@Param("title") String text);
}
