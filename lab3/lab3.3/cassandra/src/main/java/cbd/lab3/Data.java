package cbd.lab3;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import java.util.ArrayList;

public class Data {
    public void createData(String table, ArrayList<String> cols, ArrayList<String> vals){
        // Query
        String query = "INSERT INTO " + table + " (";
        boolean isFirst = true;
        for(String col : cols)
            if(isFirst){
                query = query + col;
                isFirst = false;
            }
            else
                query = query + ", " + col;

        query = query + ") VALUES (";
        isFirst = true;
        for(String val : vals)
            if(isFirst){
                query = query + val;
                isFirst = false;
            }
            else
                query = query + ", " + val;

        query = query + ");" ;

        //Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        //Creating Session object
        Session session = cluster.connect("cbd");

        //Executing the query
        session.execute(query);
        System.out.println("Data created in table " + table);
    }

    public void readData(String table){
        // Query
        String query = "SELECT * FROM " + table;

        //Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        //Creating Session object
        Session session = cluster.connect("cbd");

        //Getting the ResultSet
        ResultSet result = session.execute(query);

        System.out.println(result.all());
    }

    public void updateData(String table, String col_name, String col_val, String decisive_col, String decisive_value){
        // Query
        String query = " UPDATE " + table + " SET " + col_name + "=" + col_val + " WHERE " + decisive_col + "=" + decisive_value + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Data updated");
    }

    public void deleteData(String table, String col_name, String col_val){
        // Query
        String query = "DELETE FROM " + table + " WHERE " + col_name + "=" + col_val + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        //Executing the query
        session.execute(query);
        System.out.println("Data deleted");
    }
}
