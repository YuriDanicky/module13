package module13;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import module13.dto.Posts;
import module13.dto.Todos;
import module13.dto.User;
import module13.dto.Users;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class Jsonplaceholder {

    /**
     * Make new object
     *
     * @param user
     * @return status code
     * @throws IOException
     * @throws InterruptedException
     */
    public static int postUser(User user) throws IOException, InterruptedException {

        String url1 = "https://jsonplaceholder.typicode.com/users";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url1))
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode();

    }

    /**
     * Update user
     * @param user
     * @return status code
     * @throws IOException
     * @throws InterruptedException
     */

    public static int putUser(User user) throws IOException, InterruptedException {
        String url2 = "https://jsonplaceholder.typicode.com/users/6";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);

        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create(url2))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpClient client2 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

        return response2.statusCode();
    }

    /**
     * Delete user by id
     * @param id user
     * @return status code
     * @throws IOException
     * @throws InterruptedException
     */
    public static int deleteUser(int id) throws IOException, InterruptedException {
        String url3 = "https://jsonplaceholder.typicode.com/users/" + id;

        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(URI.create(url3))
                .DELETE()
                .build();
        HttpClient client3 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());

        return response3.statusCode();
    }

    /**
     * Get all users from https://jsonplaceholder.typicode.com/users
     * @return String in json format
     * @throws IOException
     * @throws InterruptedException
     */

    public static String getAllUsers() throws IOException, InterruptedException {
        String url4 = "https://jsonplaceholder.typicode.com/users";

        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(URI.create(url4))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpClient client4 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response4 = client4.send(request4, HttpResponse.BodyHandlers.ofString());

        return response4.body();
    }

    /**
     * Get user by id
     * @param id user
     * @return String user in json format
     * @throws IOException
     * @throws InterruptedException
     */

    public static String getUserById(int id) throws IOException, InterruptedException {
        String url5 = "https://jsonplaceholder.typicode.com/users/" + id;
        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(URI.create(url5))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpClient client5 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response5 = client5.send(request5, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response5.statusCode());

        return response5.body();
    }

    /**
     * Get user by username
     * @param username
     * @return String user in json format
     * @throws IOException
     * @throws InterruptedException
     */

    public static String getUserByUsername(String username) throws IOException, InterruptedException {
        String url6 = "https://jsonplaceholder.typicode.com/users?username=" + username;
        HttpRequest request6 = HttpRequest.newBuilder()
                .uri(URI.create(url6))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpClient client6 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> response6 = client6.send(request6, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response6.statusCode());

        return response6.body();
    }

    /**
     * Get all last comments user by id
     * @param choiceIdUser
     * @return String comments
     * @throws IOException
     */

    public static String lastCommentsUserById(int choiceIdUser) throws IOException {

        String urlUsers = "https://jsonplaceholder.typicode.com/users/" + choiceIdUser + "/posts";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String text = Jsoup.connect(urlUsers).ignoreContentType(true).get().body().text();

        Type type = TypeToken.getParameterized(List.class, Users.class)
                .getType();

        List<Users> responseUsersPost = gson.fromJson(text, type);

        int maxId = responseUsersPost.stream()
                .mapToInt(Users::getId)
                .max()
                .getAsInt();

        //second response - get posts comments

        String urlPosts = "https://jsonplaceholder.typicode.com/posts/" + maxId + "/comments";

        text = Jsoup.connect(urlPosts).ignoreContentType(true).get().body().text();
        type = TypeToken.getParameterized(List.class, Posts.class)
                .getType();
        List<Posts> responsePosts = gson.fromJson(text, type);

        String json = new GsonBuilder().setPrettyPrinting().create().toJson(responsePosts);

        //write to file

        String fileName = "user-" + choiceIdUser + "-post-" + maxId + "-comments.json";
        File file = new File(fileName);
        try (
                FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return json;
    }

    /**
     * get all pending tasks(completed: false)
     * @param idUser
     * @return String all false completed task(todos)
     * @throws IOException
     */

    public static String userTask(int idUser) throws IOException {

    String url = "https://jsonplaceholder.typicode.com/users/" + idUser + "/todos";

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String response = Jsoup.connect(url)
            .ignoreContentType(true)
            .get()
            .body()
            .text();

    Type type = TypeToken
            .getParameterized(List.class, Todos.class)
            .getType();

    List<Todos> todosResponse = gson.fromJson(response, type);

    List<Todos> todosList = todosResponse.stream()
            .filter(todos -> !todos.isCompleted())
            .collect(Collectors.toList());

    String result = gson.toJson(todosList);
        return result;
    }
}
