package com.example.todos.controller;

import com.example.todos.model.Todo;
import com.example.todos.repository.TodoRepository;
import com.example.todos.repository.UserRepository;
import com.example.todos.security.Jwtservices;
import com.example.todos.service.TodoService;
import com.example.todos.todoInputDto.TodoUserRequest;
import com.example.todos.todoResponseDto.TodoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jwtservices jwtservices;

    @Autowired
    private HttpServletRequest request;

    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

//    private Long getUserIdFromHeader(String authHeader){
//        if( authHeader != null && authHeader.startsWith("Bearer ")){
//            String token = authHeader.substring(7);
//            return jwtservices.extractUserId(token);
//        }throw new RuntimeException("invalid token");
//    }

    @PostMapping
    public TodoResponse create(@RequestBody TodoUserRequest dto){
//        Long userId = getUserIdFromHeader(authHeader);
//        TodoResponse response = todoService.createTodo(dto, userId);
//        return ResponseEntity.ok(response);
        Long userId = (Long) request.getAttribute("userId");
        return todoService.createTodo(dto, userId);

    }

//    @RequestHeader("Authorization") String authHeader
    @GetMapping
    public List<TodoResponse> getAll(){
        Long userId = (Long) request.getAttribute("userId");
        return todoService.getAll(userId);

//        Long userId = getUserIdFromHeader(authHeader);
//        List<TodoResponse> todos = todoService.getAll(userId);
//        return ResponseEntity.ok(todos);
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
