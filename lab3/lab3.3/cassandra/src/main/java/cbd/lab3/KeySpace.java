package cbd.lab3;

import com.datastax.driver.core.*;

public class KeySpace {
    public void createKeySpace(String keyspacename){
        // Creating query
        String query = "CREATE KEYSPACE " + keyspacename + " WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':1};";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect();

        // Executing the query
        session.execute(query);
        System.out.println("Keyspace " + keyspacename + " created");
    }

    public void alterKeySpace(String keyspacename, int replication_factor){
        // Creating Query
        String query = "ALTER KEYSPACE " + keyspacename + " WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':"+replication_factor
                + "} AND DURABLE_WRITES = false;";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect();

        // Executing the query
        session.execute(query);
        System.out.println("Keyspace " + keyspacename + " altered");
    }

    public void deleteKeySpace(String keyspacename){

        // Creating Query
        String query = "Drop KEYSPACE " + keyspacename + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect();

        // Executing the query
        session.execute(query);
        System.out.println("Keyspace " + keyspacename + " deleted");
    }

    public void describeKeySpaces(){

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        System.out.println(cluster.getMetadata().getKeyspaces().toString());
    }

    public void useKeySpace(String keyspacename){

        // Creating Query
        String query = "Use " + keyspacename + ";";

        // Creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // Creating Session object
        Session session = cluster.connect();

        // Executing the query
        session.execute(query);
        System.out.println("Using keyspace " + keyspacename);
    }

}
