package com.example.todos.service;

import com.example.todos.model.Todo;
import com.example.todos.repository.TodoRepository;
import com.example.todos.todoInputDto.TodoUserRequest;
import com.example.todos.todoResponseDto.TodoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    public TodoResponse createTodo(TodoUserRequest dto){
        Todo todo = new Todo();
        todo.setTask_name(dto.getTask_name());
        todo.setDescription(dto.getDescription());
        todo.setDate(LocalDate.now());
        todo.setCompleted(false);

        Todo saved = todoRepository.save(todo);

        return new TodoResponse(
                saved.getTask_name(),
                saved.getDescription(),
                saved.getDate(),
                saved.getCompleted()
        );

    }

    public List<TodoResponse> getAll() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponse> responseList = new ArrayList<>();

        for (Todo todo : todos) {
            TodoResponse dto = mapToDto(todo);
            responseList.add(dto);
        }
        return responseList;
    }

    public TodoResponse getById(Long id){
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isEmpty()){
            throw new RuntimeException("Todo not found with ID: "+ id);
        }

        Todo todo = optionalTodo.get();
        return new TodoResponse(
                todo.getTask_name(),
                todo.getDescription(),
                todo.getDate(),
                todo.getCompleted()
        );
    }

    public void delete(Long id){
        todoRepository.deleteById(id);
    }

    public TodoResponse update(Long id, TodoUserRequest dto){
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isEmpty()){
            throw new RuntimeException("Todo not found with ID: "+ id);
        }

        Todo todo = optionalTodo.get();
        todo.setTask_name(dto.getTask_name());
        todo.setDescription(dto.getDescription());

        Todo updatedTodo = todoRepository.save(todo);
        return mapToDto(updatedTodo);

    }

    private TodoResponse mapToDto(Todo todo){
        return new TodoResponse(
                todo.getTask_name(),
                todo.getDescription(),
                todo.getDate(),
                todo.getCompleted()
        );
    }


}
