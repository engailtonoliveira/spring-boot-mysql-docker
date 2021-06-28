package br.com.agoliveira.springbootcommysql.runner;

import br.com.agoliveira.springbootcommysql.model.Role;
import br.com.agoliveira.springbootcommysql.model.RoleName;
import br.com.agoliveira.springbootcommysql.model.User;
import br.com.agoliveira.springbootcommysql.repository.RoleRepository;
import br.com.agoliveira.springbootcommysql.repository.UserRepository;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataBaseRunner implements ApplicationRunner {

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private Random random = new Random();

    private Lorem lorem = LoremIpsum.getInstance();

    @Autowired
    public DataBaseRunner(final UserRepository userRepository, final RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void run(final ApplicationArguments applicationArguments) {
        /*try {
            createRoles();
            List<User> userList = createUsers();
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            System.out.print(e.getMessage());
        }*/
    }

    private void createRoles() {
        roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        roleRepository.save(new Role(RoleName.ROLE_USER));
        System.out.print("\n\n\n####################################### Program be stated [BEGIN] ###########################################");
    }

    private List<User> createUsers() {

        //log.info("\n\n\n####################################### INSERTING USERS [BEGIN] ###########################################");
        System.out.print("\n\n\n####################################### INSERTING USERS [BEGIN] ###########################################");
        List<User> userList = new ArrayList<User>();

        for (User user : getUsers()) {
            user = userRepository.save(user);
            userList.add(user);
            System.out.print("User saved: {}."+ userList.get(userList.size() - 1));
            //log.error("User saved: {}.", userList.get(userList.size() - 1));
        }

        System.out.print("\n\n\n####################################### INSERTING USERS [END] ############################################");
        //log.info("\n\n\n####################################### INSERTING USERS [END] ############################################");

        return userList;
    }

    private List<User> getUsers() {

        List<User> userList = new ArrayList<User>();

        for (int i = 0; i < 10; i++) {
            userList.add(new User(lorem.getFirstNameFemale(), lorem.getLastName(), null, lorem.getEmail(), "p4ss!"));
        }

        return userList;
    }

    private Instant getRandomInstant() {

        int min = 1;
        int max = 30;

        int number = random.nextInt(max - min + 1) + min;

        if (number > 12) {
            return Instant.now().plus(number, ChronoUnit.HOURS);
        }

        return Instant.now().plus(number, ChronoUnit.DAYS);

    }
}
