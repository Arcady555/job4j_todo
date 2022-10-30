package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Task;
import ru.job4j.service.TaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService service;

    @GetMapping("/List")
    public String listTaskGet(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "taskList";
    }

    @GetMapping("/ListDone")
    public String listTaskDoneGet(Model model) {
        model.addAttribute("tasks", service.findDone(true));
        return "taskList";
    }

    @GetMapping("/ListNew")
    public String listTaskNewGet(Model model) {
        model.addAttribute("tasks", service.findDone(false));
        return "taskList";
    }

    @GetMapping("//{taskId}")
    public String taskGet(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
        return "task";
    }

    @GetMapping("/update/{taskId}")
    public String updateTaskGet(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
        return "updateTask";
    }

    @PostMapping("/update")
    public String updateTaskPost(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/taskList";
    }

    @GetMapping("/new")
    public String addTaskGet(Model model) {
        model.addAttribute("task", new Task(0, "Заполните поле",
                null, false));
        return "newTask";
    }

    @PostMapping("/new")
    public String addTaskPost(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/taskList";
    }

    @GetMapping("/done/{taskId}")
    public String doneTaskGet(@PathVariable("taskId") int id) {
        service.replaceDone(id);
        return "redirect:/taskList";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTaskGet(@PathVariable("taskId") int id) {
        Task task = service.findById(id);
        service.delete(id);
        return "redirect:/taskList";
    }
}
