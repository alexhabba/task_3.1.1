package web;

import org.springframework.security.crypto.password.PasswordEncoder;
import web.model.Role;
import web.model.User;
import web.repository.RoleDao;
import web.repository.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TestMappingApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestMappingApplication.class, args);

        RoleDao roleRepo = context.getBean(RoleDao.class);
        UserDao userRepo = context.getBean(UserDao.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        User user1 = new User("admin", passwordEncoder.encode("123"));
        User user2 = new User("user", passwordEncoder.encode("123"));

        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        Set<Role> roles = new HashSet<>(Arrays.asList(role1, role2));

        roleRepo.saveAll(roles);

        user1.setRoles(roles);
        user2.setRoles(new HashSet<>(Collections.singletonList(role2)));

        userRepo.save(user1);
        userRepo.save(user2);


    }

}
