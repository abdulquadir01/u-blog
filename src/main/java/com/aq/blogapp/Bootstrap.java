package com.aq.blogapp;

import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.User;
import com.aq.blogapp.respositories.CategoryRepository;
import com.aq.blogapp.respositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        loadUsers();
//        loadCategories();
//        loadBlogs();
    }

    private void loadBlogs() {
        Blog blog1 = new Blog();


    }

    private void loadCategories() {
        Category sports = new Category();
        sports.setCategoryTitle("Sports");
        sports.setCategoryDescription("In this section we'll be having all the blogs related to world sports");
        categoryRepository.save(sports);

        Category tech = new Category();
        tech.setCategoryTitle("Technology");
        tech.setCategoryDescription("This section is dedicated to the all the happenings in the tech world");
        categoryRepository.save(tech);

        Category movies = new Category();
        movies.setCategoryTitle("Movies");
        movies.setCategoryDescription("We'll be talking about the best upcoming movies as well as the all time favourites");
        categoryRepository.save(movies);

        System.out.println("Number of Categories in DB : " + categoryRepository.count());
    }

    private void loadUsers() {

        User johnCena = new User();
        johnCena.setFirstName("Jhon");
        johnCena.setLastName("Cena");
        johnCena.setEmail("johncena@wwe.org");
        johnCena.setPassword("pwd");
        johnCena.setAbout("This guy is a wrestler & an actor.");
        userRepository.save(johnCena);

        User johnDoe = new User();
        johnDoe.setFirstName("Jhon");
        johnDoe.setLastName("Doe");
        johnDoe.setEmail("jdoe@wwe.org");
        johnDoe.setPassword("pwd");
        johnDoe.setAbout("This guy is a Cybersecurity Professional");
        userRepository.save(johnDoe);

        User johnWick = new User();
        johnWick.setFirstName("Jhon");
        johnWick.setLastName("Wick");
        johnWick.setEmail("jwick@wwe.org");
        johnWick.setPassword("pwd");
        johnWick.setAbout("This guy is a Missionary");
        userRepository.save(johnWick);

        System.out.println("Number of Users in DB : " + userRepository.count());
    }
}
