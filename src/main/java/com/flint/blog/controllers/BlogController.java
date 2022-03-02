package com.flint.blog.controllers;

import com.flint.blog.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.flint.blog.repo.PostRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository PostRepository;

    // Главная страница сайта -
    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = PostRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog_main";
    }

    // Выводимм  содержимое на страницу
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    // То что ввели при создании статьи пишем в базу данных
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
       // Работа с бд
        Post post = new Post(title, anons, full_text);
        PostRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value="id") long id, Model model){
        if(!PostRepository.existsById(id)){
            return "redirect:/blog";

        }
        Optional<Post> post = PostRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

}
