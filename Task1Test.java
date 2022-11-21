package module13;

import module13.dto.Address;
import module13.dto.Company;
import module13.dto.Geo;
import module13.dto.User;

import java.io.IOException;


public class Task1Test {

    public static void main(String[] args) throws IOException, InterruptedException {

        // new user

        User user = new User(1, "Ivan", "Ivan", "Ivan@gmail.com",
                new Address("Qwerty str.", "apt. 300", "City13", "66954-555",
                        new Geo("56,5555","65,566")),
                "1-770-736-8031 x56442", "www.ivan.com",
                new Company("CH company", "Proactive didactic contingency", "harness real-time e-markets"));

        //Task 1:

        System.out.println("Jsonplaceholder.postUser(user) = " + Jsonplaceholder.postUser(user));

        System.out.println("Jsonplaceholder.putUser(user) = " + Jsonplaceholder.putUser(user));

        System.out.println("Jsonplaceholder.deleteUser(3) = " + Jsonplaceholder.deleteUser(3));

        System.out.println("Jsonplaceholder.getAllUsers() = " + Jsonplaceholder.getAllUsers());

        System.out.println("Jsonplaceholder.getUserById(5) = " + Jsonplaceholder.getUserById(5));

        System.out.println("Jsonplaceholder.getUserByUsername(\"Antonette\") = " + Jsonplaceholder.getUserByUsername("Antonette"));

        //Task 2:

        System.out.println("Jsonplaceholder.lastCommentsUserById(2) = " + Jsonplaceholder.lastCommentsUserById(2));

        //Task 3:

        System.out.println("Jsonplaceholder.userTask(2) = " + Jsonplaceholder.userTask(2));


    }
}
