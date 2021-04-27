package lab1.ex4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamInfo;

public class Autocomplete {
    private Jedis jedis;

    public Autocomplete() {
        this.jedis = new Jedis("localhost");    // new redis connection
    }

    private void saveUser(String username) {
        jedis.zadd("female_names", 0, username); // saving name in sorted set female_names with score=0
    }

    public Set<String> getSet(String search){
        return jedis.zrangeByLex("female_names", "[aa", "[zz"); // searching in female_names by search string
    }
    public Set<String> getAllKeys(){
        return jedis.keys("*");
    }

    public long count(){
        return jedis.zcount("female-names","-inf","+inf");
    }

    public static void main(String[] args){
        Autocomplete board = new Autocomplete(); // new jedis connection

        File f = new File("female_names.txt"); //file to get the names from

        //trying to pass the names from file to list
        try {
            Scanner sc = new Scanner(f);

            while(sc.hasNext()){
                board.saveUser(sc.next());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            board.saveUser("aaren");
            board.saveUser("aarika");
            board.saveUser("abagael");

            System.out.println("NAMES INSERTED: " + board.count());
        }

        //reading input
        Scanner sc = new Scanner(System.in);
        System.out.println("Search for: ");
        String search = sc.next();
        sc.close();

        //searching for names and printing them
        for(String name: board.getSet(search)) {
            System.out.println(name);
        }
        if (board.getSet(search).size() == 0){
            System.out.println("0 results found");
        }

    }
}
