package com.aq.blogapp.utils;

import com.aq.blogapp.utils.constants.AppConstants;
import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.Role;
import com.aq.blogapp.model.User;
import com.aq.blogapp.respositories.BlogRepository;
import com.aq.blogapp.respositories.CategoryRepository;
import com.aq.blogapp.respositories.RoleRepository;
import com.aq.blogapp.respositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder pwdEncoder;

    public BootstrapData(UserRepository userRepository, CategoryRepository categoryRepository, BlogRepository blogRepository, RoleRepository roleRepository, PasswordEncoder pwdEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.blogRepository = blogRepository;
        this.roleRepository = roleRepository;
        this.pwdEncoder = pwdEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
//        loadUsers();
//        loadCategories();
//        loadBlogs();
//        loadRoles();

        System.out.println("User count in table users : " + userRepository.count());
        System.out.println("Blog count in table blogs : " + blogRepository.count());
        System.out.println("Category count in table categories : " + categoryRepository.count());
        System.out.println("User Role count in table roles : " + roleRepository.count());
    }


    private final User johnCena = new User();
    private final User johnDoe = new User();
    private final User johnWick = new User();
    private final User deadPool = new User();
    private final User byomukesh = new User();

    private final Category sports = new Category();
    private final Category tech = new Category();
    private final Category movies = new Category();
    private final Category novels = new Category();


    private void loadCategories() {
        Category sports = this.sports;
        sports.setCategoryTitle("Sports");
        sports.setCategoryDescription("All the blogs related to sports");


        Category tech = this.tech;
        tech.setCategoryTitle("Technology");
        tech.setCategoryDescription("All the happenings in the tech world");

        Category movies = this.movies;
        movies.setCategoryTitle("Movies");
        movies.setCategoryDescription("Blogs about the best movies of all times");

        Category novels = this.novels;
        novels.setCategoryTitle("Novels");
        novels.setCategoryDescription("Blogs about the best novels of all times");

        List<Category> categories = List.of(sports, tech, movies, novels);
        categoryRepository.saveAll(categories);

    }

    private void loadUsers() {

        Role userNormal = new Role();
        userNormal.setRoleId(AppConstants.NORMAL_USER);
        userNormal.setRole("NORMAL_USER");

        Role userAdmin = new Role();
        userAdmin.setRoleId(AppConstants.ADMIN_USER);
        userAdmin.setRole("ADMIN_USER");

        User johnCena = this.johnCena;
        johnCena.setFirstName("Jhon");
        johnCena.setLastName("Cena");
        johnCena.setEmail("johncena@wwe.org");
        johnCena.setPassword(pwdEncoder.encode("p@ssW0rd"));
        johnCena.setAbout("This guy is a wrestler & an actor.");
        johnCena.getRoles().add(userNormal);

        User johnDoe = this.johnDoe;
        johnDoe.setFirstName("Jhon");
        johnDoe.setLastName("Doe");
        johnDoe.setEmail("jdoe@wwe.org");
        johnDoe.setPassword(pwdEncoder.encode("p@ssW0rd"));
        johnDoe.setAbout("This guy is a Cyber-security Professional");
        johnDoe.getRoles().add(userNormal);

        User johnWick = this.johnWick;
        johnWick.setFirstName("Jhon");
        johnWick.setLastName("Wick");
        johnWick.setEmail("jwick@wwe.org");
        johnWick.setPassword(pwdEncoder.encode("p@ssW0rd"));
        johnWick.setAbout("This guy is a Missionary");
        johnWick.getRoles().add(userNormal);


        User deadPool = this.deadPool;
        deadPool.setFirstName("Dead");
        deadPool.setLastName("Pool");
        deadPool.setEmail("pooldead@gmail.com");
        deadPool.setPassword(pwdEncoder.encode("p@ssW0rd"));
        deadPool.setAbout("This guy is a Mutant");
        deadPool.getRoles().add(userNormal);


        User byomukesh = this.byomukesh;
        byomukesh.setFirstName("Byomukesh");
        byomukesh.setLastName("Bakshi");
        byomukesh.setEmail("bbakshi@gmail.com");
        byomukesh.setPassword(pwdEncoder.encode("p@ssW0rd"));
        byomukesh.setAbout("This guy is a private Detective");
        byomukesh.getRoles().add(userAdmin);


        List<User> users = List.of(johnCena, johnDoe, johnWick, deadPool, byomukesh);
        userRepository.saveAll(users);

    }

    private void loadBlogs() {
        Blog blog1 = new Blog();
        blog1.setTitle("Cricket U19 Champions");
        blog1.setContent("The first month of 2023 has ended with a bang with India winning the ICC U19 Women Cricket World Cup.");
        blog1.setCategory(sports);
        blog1.setUser(byomukesh);

        Blog blog2 = new Blog();
        blog2.setTitle("Intel ARC! is getting better?");
        blog2.setContent("With it's first entry in the discrete GPU market, the intel GPUs were not performant as the user were expecting them to. With the new driver update it is now running DX12 games better and the DX9 games are now perform much better than earlier.");
        blog2.setCategory(tech);
        blog2.setUser(johnDoe);

        Blog blog3 = new Blog();
        blog3.setTitle("Pathan");
        blog3.setContent("SRK's awaited movie Pathan has been release on 16th of Jan,2023 and is has broke all the previous day one Box Office collection records");
        blog3.setCategory(movies);
        blog3.setUser(byomukesh);

        Blog blog4 = new Blog();
        blog4.setTitle("Ronaldo, there is still a fight");
        blog4.setContent("After being dropped from his last club Real Madrid, Ronaldo as accepted the offer from the Dubai's football club and in his first game Ronaldo has performed better than what was expected");
        blog4.setCategory(sports);
        blog4.setUser(johnCena);

        Blog blog5 = new Blog();
        blog5.setTitle("The OceanCleanUp3");
        blog5.setContent("The first month of 2023 has ended with a bang with India winning the ICC U19 Women Cricket World Cup.");
        blog5.setCategory(tech);
        blog5.setUser(byomukesh);

        Blog blog6 = new Blog();
        blog6.setTitle("The OceanCleanUp3");
        blog6.setContent("The CEO and the founder of the OceanCleanUp company has just unrevieled the final prototype of the OceanCleanUp3 system");
        blog6.setCategory(tech);
        blog6.setUser(deadPool);

        Blog blog7 = new Blog();
        blog7.setTitle("Avatar 2, 13 years in making");
        blog7.setContent("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.");
        blog7.setCategory(movies);
        blog7.setUser(johnWick);

        Blog blog8 = new Blog();
        blog8.setTitle("Volleyball");
        blog8.setContent("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.");
        blog8.setCategory(sports);
        blog8.setUser(johnCena);

        Blog blog9 = new Blog();
        blog9.setTitle("Hundai & the future of ICE");
        blog9.setContent("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.");
        blog9.setCategory(tech);
        blog9.setUser(byomukesh);

        Blog blog10 = new Blog();
        blog10.setTitle("Wakanda2");
        blog10.setContent("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.");
        blog10.setCategory(tech);
        blog10.setUser(johnCena);

        Blog blog11 = new Blog();
        blog11.setTitle("The Two Sons");
        blog11.setContent("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.");
        blog11.setCategory(novels);
        blog11.setUser(byomukesh);

        Blog blog12 = new Blog();
        blog12.setTitle("The Green Book");
        blog12.setContent("Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.");
        blog12.setCategory(movies);
        blog12.setUser(johnWick);

        List<Blog> blogs = List.of(blog1, blog2, blog3, blog4, blog5, blog6, blog7, blog8, blog9, blog10, blog11, blog12);

        blogRepository.saveAll(blogs);

    }

    private void loadRoles() {

        try {

            Role userNormal = new Role();
            userNormal.setRoleId(AppConstants.NORMAL_USER);
            userNormal.setRole("NORMAL_USER");

            Role userAdmin = new Role();
            userAdmin.setRoleId(AppConstants.ADMIN_USER);
            userAdmin.setRole("ADMIN_USER");

            List<Role> roles = List.of(userAdmin, userNormal);

            List<Role> result = roleRepository.saveAll(roles);

            System.out.println("Saved Roles");
            result.forEach(r -> System.out.println(r.getRole()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
