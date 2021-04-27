package cbd.lab3;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class Query {
    public void vidAuthor(String author){
        String query = "select * from video where author = '" + author + "';";

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("cbd");

        ResultSet result = session.execute(query);
        System.out.println(result.all());
    }

    public void commentUser(String user){
        String query = "select * from comment where author = '" + user + "';";

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("cbd");

        ResultSet result = session.execute(query);
        System.out.println(result.all());
    }

    public void commentVideo(int video){
        String query = "select * from comment where video_id = " + video + ";";

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("cbd");

        ResultSet result = session.execute(query);
        System.out.println(result.all());
    }

    public void ratingVideo(int video){
        String query = "select video_id, avg(value) as avg_value, count(video_id) as no_reviews from rating where video_id=" + video + ";";

        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        Session session = cluster.connect("cbd");

        ResultSet result = session.execute(query);
        System.out.println(result.all());
    }
}
