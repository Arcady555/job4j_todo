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
@RequestMapping("/task")
public class TaskController {
    private final TaskService service;

    @GetMapping("/list")
    public String listTaskGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", service.findAll());
        return "task/taskList";
    }

    @GetMapping("/listDone")
    public String listTaskDoneGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", service.findDone(true));
        return "task/taskList";
    }

    @GetMapping("/listNew")
    public String listTaskNewGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", service.findDone(false));
        return "task/taskList";
    }

    @GetMapping("/{taskId}")
    public String taskGet(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", service.findById(id));
        return "task/task";
    }

    @GetMapping("/update/{taskId}")
    public String updateTaskGet(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", service.findById(id));
        return "task/updateTask";
    }

    @PostMapping("/update")
    public String updateTaskPost(@ModelAttribute Task task) {
        service.replace(task.getId(), task);
        return "redirect:/task/list";
    }

    @GetMapping("/new")
    public String addTaskGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", new Task(0, "Заполните поле",
                null, false));
        return "task/newTask";
    }

    @PostMapping("/new")
    public String addTaskPost(@ModelAttribute Task task) {
        service.add(task);
        return "redirect:/task/list";
    }

    @GetMapping("/done/{taskId}")
    public String doneTaskGet(@PathVariable("taskId") int id,Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        service.replaceDone(id);
        return "redirect:/task/list";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTaskGet(@PathVariable("taskId") int id, Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        Task task = service.findById(id);
        service.delete(id);
        return "redirect:/task/list";
    }
}
