package cbd.lab2;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

// We never learnt any of this in class, so it was all taken from: http://zetcode.com/java/mongodb/

public class Mongo {
    private MongoClient mongoClient;
    private MongoDatabase mongoDB;
    private MongoCollection mongoColl;

    public Mongo(){
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.mongoDB = mongoClient.getDatabase("cbd");
        this.mongoColl = mongoDB.getCollection("rest");
    }

    public MongoDatabase getMongoDB() {
        return mongoDB;
    }

    public MongoCollection getMongoColl() {
        return mongoColl;
    }

    public static void main( String[] args )
    {
        Mongo mongo = new Mongo();
        MongoDatabase db = mongo.getMongoDB();

        // printing collections
        for (String name : db.listCollectionNames()) {
            System.out.println(name);
        }

        mongo.insert(100, 29.239329, -49.239329, "Rua da Amizade", 33404, "Porto", "Portuguese", "A Casa do Zé");
        Document found = mongo.find("rua", "Rua da Amizade");
        if(found != null){
            System.out.println(new ArrayList<>(found.values()));
        }
        mongo.update("rua", "Rua da Amizade", "Rua dos Smurfs");
        Document f2 = mongo.find("rua", "Rua dos Smurfs");
        if(f2 != null){
            System.out.println(new ArrayList<>(found.values()));
        }

        // b) was taken from https://mongodb.github.io/mongo-java-driver/3.6/driver/tutorials/indexes/
        mongo.getMongoColl().createIndex(Indexes.ascending("localidade"));
        mongo.getMongoColl().createIndex(Indexes.ascending("gastronomia"));
        mongo.getMongoColl().createIndex(Indexes.text("nome"));

        // c)
        int count_locs = mongo.countLocalidades();
        System.out.println("Numero de localidades distintas: " + count_locs);

        Map<String, Integer> count_rests_by_loc = mongo.countRestByLocalidade();
        System.out.println("Numero de restaurantes por localidade:");
        for(Map.Entry loc : count_rests_by_loc.entrySet()){
            System.out.println("-> " + loc.getKey() + " - " + loc.getValue());
        }

        Map<String, Integer> count_rests_by_loc_and_gastro = mongo.countRestByLocalidadeByGastronomia();
        System.out.println("Numero de restaurantes por localidade e gastronomia:");
        for(Map.Entry loc : count_rests_by_loc_and_gastro.entrySet()){
            System.out.println("-> " + loc.getKey().toString().split(" ")[0] + " | " + loc.getKey().toString().split(" ")[1] + " - " + loc.getValue());
        }

        String nome = "A Casa do Zé";
        List<String> matchingRest = mongo.getRestWithNameCloserTo(nome);
        System.out.println("Nome de restaurantes contendo \"" + nome + "\" no nome: ");
        for(String s : matchingRest){
            System.out.println("-> " + s);
        }

    }

    public void insert(int building, double latitude, double longitude, String rua, int zipcode, String
            localidade, String gastronomia, String nome){
        Random rest_id = new Random();
        Document doc = new Document("_id", new ObjectId());
        double[] coord = new double[] {latitude, longitude};
        Document address = new Document();
        address.append("building", building).append("coord", coord).append("rua", rua)
                .append("zipcode", zipcode);
        doc.append("address", address).append("localidade", localidade)
                .append("gastronomia", gastronomia).append("nome", nome)
                .append("restaurant_id", rest_id.nextInt(50000000));

        getMongoColl().insertOne(doc);
    }

    public Document find(String field, String value){
        BasicDBObject search = new BasicDBObject(field, value);
        MongoCursor cursor = getMongoColl().find(search).iterator();

        try {
            while (cursor.hasNext()){
                Document d = (Document) cursor.next();
                if(d.get(field) == value){
                    return d;
                }
            }
        }
        finally {
            cursor.close();
        }
        return null;
    }

    public void update(String field, String value, String new_value){
        BasicDBObject search = new BasicDBObject(field, value);
        getMongoColl().updateOne(search, new Document("$set", new Document(field, new_value)));

    }

    // c) https://www.baeldung.com/java-mongodb-aggregations
    public int countLocalidades(){
        MongoCursor cursor = getMongoColl().distinct("localidade", String.class).iterator();
        int count = 0;
        try {
            while (cursor.hasNext()){
                cursor.next();
                count++;
            }
        }
        finally {
            cursor.close();
        }
        return count;
    }

    public Map<String, Integer> countRestByLocalidade(){
        AggregateIterable<Document> restsByLocal = getMongoColl().aggregate(Arrays.asList(Aggregates.group("$localidade",
                Accumulators.sum("total", 1))));

        HashMap<String, Integer> res = new HashMap<>();

        for(Document d : restsByLocal){

            String localidade = (String) d.get("localidade");
            if(!res.containsKey(localidade)) {
                res.put(localidade, 0);
            }
            res.put(localidade, res.get(localidade)+1 );
        }

        return res;

    }

    public Map<String,Integer> countRestByLocalidadeByGastronomia(){

        Map<String, Object> filter = new HashMap<>();
        filter.put("localidade", "$localidade");
        filter.put("gastronomia", "$gastronomia");

        AggregateIterable<Document> restsByLocal = getMongoColl().aggregate(Arrays
                .asList(Aggregates.group(new Document(filter), Accumulators.sum("total", 1))));

        HashMap<String, Integer> res = new HashMap<>();

        for(Document d : restsByLocal){

            String chave = (String) d.get("localidade") + " " + (String) d.get("gastronomia");

            if(!res.containsKey(chave)) {
                res.put(chave, 0);
            }
            res.put(chave, res.get(chave)+1 );
        }

        return res;
    }

    public List<String> getRestWithNameCloserTo(String name){
        List<String> res = new ArrayList<>();
        Document query = new Document("$regex", ".*" + name + ".*");
        BasicDBObject search = new BasicDBObject("nome", query);

        MongoCursor cursor = getMongoColl().find(search).iterator();

        try {
            while (cursor.hasNext()){
                Document d = (Document) cursor.next();
                res.add((String) d.get("nome"));
            }
        }
        finally {
            cursor.close();
        }
        return null;
    }

}
