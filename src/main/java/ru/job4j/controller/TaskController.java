package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.model.Task;
import ru.job4j.service.TaskService;

@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService service;

    @GetMapping("/taskList")
    public String listTaskGet(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "taskList";
    }

    @GetMapping("/taskListDone")
    public String listTaskDoneGet(Model model) {
        model.addAttribute("tasks", service.findDone());
        return "taskList";
    }

    @GetMapping("/taskListNew")
    public String listTaskNewGet(Model model) {
        model.addAttribute("tasks", service.findNew());
        return "taskList";
    }

    @GetMapping("/task/{taskId}")
    public String taskGet(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
        return "task";
    }

    @GetMapping("/updateTask/{taskId}")
    public String updateTaskGet(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", service.findById(id));
        return "updateTask";
    }

    @PostMapping("/updateTask")
    public String updateTaskPost(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/taskList";
    }

    @GetMapping("/newTask")
    public String addTaskGet(Model model) {
        model.addAttribute("task", new Task(0, "Заполните поле",
                null, false));
        return "newTask";
    }

    @PostMapping("/newTask")
    public String addTaskPost(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/taskList";
    }

    @GetMapping("/doneTask/{taskId}")
    public String doneTaskGet(@PathVariable("taskId") int id) {
        Task task = service.findById(id);
        task.setDone(true);
        service.replace(id, task);
        return "redirect:/taskList";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTaskGet(@PathVariable("taskId") int id) {
        Task task = service.findById(id);
        service.delete(id);
        return "redirect:/taskList";
    }
}
