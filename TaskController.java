package com.hotel.controller;

import com.hotel.entity.EmTask;
import com.hotel.entity.User;
import com.hotel.respository.UserRepository;
import com.hotel.service.TaskService;
import com.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody EmTask emTask) {

        // Fetch the user by ID from the createdBy field
        if (emTask.getCreatedBy() == null || emTask.getCreatedBy().getId() == null) {
            return new ResponseEntity<>("User ID is required in createdBy", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(emTask.getCreatedBy().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        emTask.setCreatedBy(user);
        //emTask.setCreatedAt(LocalDateTime.now());

        EmTask savedTask = taskService.addTask(emTask);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    /*@PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(
            @PathVariable long taskId,
            @RequestBody EmployeeTask updatedTask
    ) {
        // Get logged-in username from JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        //String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.print(loggedInUsername);
        // Fetch logged-in user safely
        User loggedInUser;
        try {
            loggedInUser = userService.findByUsername(loggedInUsername);
            System.out.print(loggedInUser.getId());
        } catch (Exception e) {
            return new ResponseEntity<>("Logged-in user not found", HttpStatus.UNAUTHORIZED);
        }
        System.out.print(loggedInUser.getId());
        // Fetch task
        Optional<EmployeeTask> optionalTask = taskService.findTaskById(taskId);
        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }

        EmployeeTask existingTask = optionalTask.get();

        // Check task owner
        User taskOwner = existingTask.getCreatedBy();
        if (taskOwner == null) {
            return new ResponseEntity<>("Task owner not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!loggedInUser.getId().equals(taskOwner.getId())) {
            return new ResponseEntity<>("You can only update your own tasks", HttpStatus.FORBIDDEN);
        }

        // Update task
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        EmployeeTask savedTask = taskService.saveTask(existingTask);

        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }*/

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long taskId,
            @RequestBody EmTask updatedTask
    ) {

        // 1. Load task
        EmTask existingTask = taskService.findTaskById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 2. Update fields
        existingTask.setTitle(updatedTask.getTitle());
        //existingTask.setDescription(updatedTask.getDescription());

        // 3. Save updated task
        EmTask savedTask = taskService.saveTask(existingTask);

        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }





    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks() {
        List<EmTask> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {

        boolean deleted = taskService.deleteTaskById(taskId);

        if (deleted) {
            return new ResponseEntity<>("Task deleted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Task not found!", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/has-task/{userId}")
    public ResponseEntity<Boolean> hasUserCreatedTask(@PathVariable Long userId) {
        boolean result = userService.hasUserCreatedTask(userId);
        return ResponseEntity.ok(result);
    }

}

