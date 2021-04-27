package cbd.lab1;

import java.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamInfo;

public class ReadPostList {
    private Jedis jedis;

    public static String USERS_LIST = "users_list";

    public ReadPostList(){
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String username){
        jedis.rpush(USERS_LIST, username);
    }

    public List<String> getUsers(){
        return jedis.lrange(USERS_LIST,0,1000);
    }

    public String[] getAllKeys(){

        return (String[])jedis.keys("*").toArray();
    }

    public static void main(String[] args){
        SimplePost board = new SimplePost();

        ArrayList<String> usersList = new ArrayList<>();

        usersList.add("Maria");
        usersList.add("Miquelina");
        usersList.add("Francisca");

        for (String user:usersList) {
            board.saveUser(user);
        }

        board.getAllKeys().stream().forEach(System.out::println);
        board.getUsers().stream().forEach(System.out::println);

    }
}
