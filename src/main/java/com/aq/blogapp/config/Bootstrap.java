package com.aq.blogapp.config;

import com.aq.blogapp.model.User;
import com.aq.blogapp.respositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;




@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
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

        System.out.println("Number of Users in DB : "+userRepository.count());
    }
}
