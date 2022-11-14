package ru.job4j.controller;

import lombok.AllArgsConstructor;

import net.jcip.annotations.ThreadSafe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.job4j.model.Category;
import ru.job4j.model.Task;
import ru.job4j.model.User;
import ru.job4j.service.CategoryService;
import ru.job4j.service.PriorityService;
import ru.job4j.service.TaskService;
import ru.job4j.utility.Utility;

import javax.servlet.http.HttpSession;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@ThreadSafe
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String listTaskGet(Model model, HttpSession httpSession) {
        User user = Utility.userGet(model, httpSession);
        System.out.println(user.getTimeZone());
        List<Task> tasks = taskService.findAll();
        for (Task task : tasks) {
            ZonedDateTime time;
            if (user.getTimeZone() == null) {
                time = task.getCreated().withZoneSameInstant(ZoneId.of("UTC"))
                        .truncatedTo(ChronoUnit.SECONDS);
            } else {
                time = task.getCreated().withZoneSameInstant(ZoneId.of(user.getTimeZone()))
                        .truncatedTo(ChronoUnit.SECONDS);
            }
            task.setCreated(time);
        }
        model.addAttribute("tasks", tasks);
        return "task/taskList";
    }

    @GetMapping("/done")
    public String listTaskDoneGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", taskService.findDone(true));
        return "task/taskList";
    }

    @GetMapping("/undone")
    public String listTaskNewGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("tasks", taskService.findDone(false));
        return "task/taskList";
    }

    @GetMapping("/{id}")
    public String taskGet(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", taskService.findById(id));
        return "task/task";
    }

    @GetMapping("/update/{id}")
    public String updateTaskGet(Model model, @PathVariable("id") int id, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", taskService.findById(id));
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("allCategories", categoryService.findAll());
        return "task/updateTask";
    }

    @PostMapping("/update")
    public String updateTaskPost(@ModelAttribute Task task,
                                 @ModelAttribute User user,
                                 @RequestParam("categoryIds") String categoryIds
    ) {
        fullTask(task, categoryIds, user);
        taskService.replace(task);
        return "redirect:/tasks/list";
    }

    @GetMapping("/create")
    public String addTaskGet(Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        model.addAttribute("task", new Task(0, "Заполните поле",
                null, false, null, null, null));
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("allCategories", categoryService.findAll());
        return "task/newTask";
    }

    @PostMapping("/create")
    public String addTaskPost(@ModelAttribute Task task,
                              @ModelAttribute User user,
                              @RequestParam("categoryIds") String categoryIds
    ) {
        fullTask(task, categoryIds, user);
        taskService.add(task);
        return "redirect:/tasks/list";
    }

    @GetMapping("/done/{id}")
    public String doneTaskGet(@PathVariable("id") int id,Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        taskService.replaceDone(id);
        return "redirect:/tasks/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskGet(@PathVariable("id") int id, Model model, HttpSession httpSession) {
        Utility.userGet(model, httpSession);
        taskService.delete(id);
        return "redirect:/tasks/list";
    }

    private void fullTask(Task task, String categoryIds, User user) {
        List<Category> categories = new ArrayList<>();
        String[] categoryIdsArray = categoryIds.split(",");
        List<Integer> categoryIdList = new ArrayList<>();
        for (String str : categoryIdsArray) {
            categoryIdList.add(Integer.parseInt(str));
        }
        for (int id : categoryIdList) {
            categories.add(categoryService.findById(id));
        }
        task.setUser(user);
        task.setCategories(categories);
        task.setCreated(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}