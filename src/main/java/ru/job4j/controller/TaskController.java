package ru.job4j.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Task;
import ru.job4j.service.TaskService;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@ThreadSafe
public class TaskController {
    private final TaskService service;

    @GetMapping("/taskList")
    public String listTaskGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", service.findAll());
        return "task/taskList";
    }

    @GetMapping("/taskListDone")
    public String listTaskDoneGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", service.findDone(true));
        return "task/taskList";
    }

    @GetMapping("/taskListNew")
    public String listTaskNewGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", service.findDone(false));
        return "task/taskList";
    }

    @GetMapping("/task/{taskId}")
    public String taskGet(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", service.findById(id));
        return "task/task";
    }

    @GetMapping("/updateTask/{taskId}")
    public String updateTaskGet(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", service.findById(id));
        return "task/updateTask";
    }

    @PostMapping("/updateTask")
    public String updateTaskPost(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/taskList";
    }

    @GetMapping("/newTask")
    public String addTaskGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", new Task(0, "Заполните поле",
                null, false));
        return "task/newTask";
    }

    @PostMapping("/newTask")
    public String addTaskPost(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/taskList";
    }

    @GetMapping("/doneTask/{taskId}")
    public String doneTaskGet(@PathVariable("taskId") int id,Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        service.replaceDone(id);
        return "redirect:/taskList";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTaskGet(@PathVariable("taskId") int id, Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        Task task = service.findById(id);
        service.delete(id);
        return "redirect:/taskList";
    }
}
