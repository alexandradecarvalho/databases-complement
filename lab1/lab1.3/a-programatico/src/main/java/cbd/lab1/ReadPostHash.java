package cbd.lab1;

import java.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamInfo;

public class ReadPostHash {
    private Jedis jedis;

    public static String USERS_HASH = "users_hash";

    public ReadPostHash(){
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String username, String username2){
        jedis.hset(USERS_HASH, username, username2);
    }

    public Map<String,String> getUsers(){
        return jedis.hgetAll(USERS_HASH);
    }

    public Set<String> getAllKeys(){

        return jedis.keys("*");
    }
    public static void main(String[] args){
        ReadPostHash board = new ReadPostHash();

        Map<String,String> usersHash = new HashMap<>();

        usersHash.put("Anthony", "Anthony");
        usersHash.put("Gonçalo", "Gonçalo");
        usersHash.put("Inês", "Inês");

        for(Map.Entry<String, String> m : usersHash.entrySet()){
            board.saveUser(m.getKey(), m.getValue());
        }

        for(Map.Entry<String, String> m : usersHash.entrySet()){
            System.out.printf(" %s : %s \n", m.getKey(), m.getValue());
        }

    }
}
