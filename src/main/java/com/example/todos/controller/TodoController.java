package com.example.todos.controller;

import com.example.todos.model.Todo;
import com.example.todos.service.TodoService;
import com.example.todos.todoInputDto.TodoUserRequest;
import com.example.todos.todoResponseDto.TodoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponse create(@RequestBody TodoUserRequest dto){
        return todoService.createTodo(dto);
    }

    @GetMapping
    public List<TodoResponse> getAll(){
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public TodoResponse getById(@PathVariable Long id){
        return todoService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        todoService.delete(id);
    }

    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable Long id, @RequestBody TodoUserRequest dto){
        return todoService.update(id, dto);
    }

}
