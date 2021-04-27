package cbd.lab1;

import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamInfo;

public class SimplePost {
    private Jedis jedis;

    public static String USERS = "users";

    public SimplePost(){
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String username){
        jedis.sadd(USERS, username);
    }

    public Set<String> getUsers(){
        return jedis.smembers(USERS);
    }

    public Set<String> getAllKeys(){
        return jedis.keys("*");
    }

    public static void main(String[] args){
        SimplePost board = new SimplePost();

        String[] users = {"Ana", "Pedro", "Maria", "Alexis"};

        for (String user:users) {
            board.saveUser(user);
        }
        board.getAllKeys().stream().forEach(System.out::println);
        board.getUsers().stream().forEach(System.out::println);
    }
}
