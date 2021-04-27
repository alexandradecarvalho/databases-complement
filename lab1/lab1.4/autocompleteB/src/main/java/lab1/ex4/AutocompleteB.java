package lab1.ex4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamInfo;

public class AutocompleteB {
    private Jedis jedis;

    public AutocompleteB() {
        this.jedis = new Jedis("localhost");    // new redis connection
    }

    private void saveUser(String username, int c) {
        jedis.zadd("female_names", c, username); // saving name in sorted set female_names with score=0
    }

    public Set<String> getSet(String search){
        return jedis.zrangeByLex("female_names", "[{search}", "[{search}z"); // searching in female_names by search string
    }
    public Set<String> getAllKeys(){
        return jedis.keys("*");
    }

    public long count(){
        return jedis.zcount("female-names","-inf","+inf");
    }

    public static void main(String[] args){
        AutocompleteB board = new AutocompleteB(); // new jedis connection

        File f = new File("nomes-registados-2020.csv"); //file to get the names from

        //trying to pass the names from file to list or manually
        try {
            Scanner sc = new Scanner(f);

            while(sc.hasNext()){
                String[] line = sc.nextLine().split("\t");
                board.saveUser(line[0], Integer.parseInt(line[2]));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            board.saveUser("Maria", 5640);
            board.saveUser("Matilde", 1835);
            board.saveUser("Leonor", 1783);

            System.out.println("NAMES INSERTED: " + board.count());
        }

        //reading input
        Scanner sc = new Scanner(System.in);
        System.out.println("Search for: ");
        String search = sc.next();
        sc.close();

        int nextIndex = 0;

        int c = (int)board.getSet(search).size();          // number of results found
        String[] results = new String[c];                  // our list of results

        //searching for names and saving them in our results list
        for(String name: board.getSet(search)) {
            results[nextIndex] = name;
            nextIndex++;
        }

        // printing results or warning of 0 results
        if (c == 0){
            System.out.println("0 results found");
        }
        else{
            for(int elem = c-1; elem >= 0; elem--){
                System.out.println(results[elem]);
            }
        }

    }

}
