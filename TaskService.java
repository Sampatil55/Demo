package com.hotel.service;

import com.hotel.entity.EmTask;
import com.hotel.respository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public EmTask addTask(EmTask emTask) {
        return taskRepository.save(emTask);
    }

    //newly added
    // Fetch task by ID
    public Optional<EmTask> findTaskById(long taskId) {
        return taskRepository.findById(taskId);
    }

    // Save/update task
    public EmTask saveTask(EmTask task) {
        return taskRepository.save(task);
    }

    //
    public Optional<EmTask> updateTaskById(long id) {
        return taskRepository.findById(id);
    }


    public List<EmTask> getAllTasks() {
        return taskRepository.findAll();
    }


    public boolean deleteTaskById(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }




    }

