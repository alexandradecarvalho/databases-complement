package cbd.lab3;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    public void createTable(String tablename, HashMap<String, String> columns, ArrayList<String> primary_keys){
        // Query
        String query = "CREATE TABLE " + tablename + "(";
        for(String column : columns.keySet()){
            query = query + column + " " + columns.get(column);
            if(primary_keys.contains(column))
                query = query + " primary key";
            query = query + ", ";
        }
        query = query + ");";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Table" + tablename +" created");
    }

    public void addCol(String table, String name, String type){
        String query = "ALTER TABLE " + table + " ADD " + name + " " + type + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Column " + name + " added to table " + table);
    }

    public void deleteCol(String table, String name){
        // Query
        String query = "ALTER TABLE " + table + " DROP " + name + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Column " + name + " deleted from table " + table);
    }

    public void createIndex(String name, String table, String col_name){
        // Query
        String query = "CREATE INDEX " + name + " ON " + table + "("+ col_name +");";
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        //Creating Session object
        Session session = cluster.connect("cbd");

        //Executing the query
        session.execute(query);
        System.out.println("Index " + name + " created on " + table);
    }

    public void dropIndex(String name){
        // Query
        String query = "DROP INDEX " + name + ";";

        // Creating cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Index " + name + " dropped");
    }

    public void truncateTable(String name){
        // Query
        String query = "Truncate " + name + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Table " + name + " truncated");
    }

    public void dropTable(String name){
        // Query
        String query = "DROP TABLE " + name + ";";
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect("cbd");

        // Executing the query
        session.execute(query);
        System.out.println("Table " + name + " dropped");
    }

    public void describeColumnFamilies(String keyspace){
        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Collection<TableMetadata> tables = cluster.getMetadata().getKeyspace(keyspace).getTables();

        // Converting to list of the names
        List<String> tableNames = tables.stream().map(tm -> tm.getName()).collect(Collectors.toList());

        // Printing
        for(String name : tableNames)
            System.out.println(name);
    }

}
