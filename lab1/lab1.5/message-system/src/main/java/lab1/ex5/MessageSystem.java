package lab1.ex5;

import java.util.*;
import redis.clients.jedis.Jedis;

public class MessageSystem {
    private Jedis jedis;
    public MessageSystem() {
        this.jedis = new Jedis("localhost");
    }

    public void saveUser(String username) {
        jedis.sadd("broker", username);
    }

    public List<String> getUser() {
        ArrayList<String> userList = new ArrayList<String>();
        Set<String> userSet = jedis.smembers("brokerUsers");
        for(String user: userSet)
            userList.add(user);
        return userList;
    }

    public void sub(String username, int subTo, MessageSystem server) {
        String subToUser = server.getUser().get(subTo);
        jedis.sadd("set"+username+"Subs", subToUser);
    }

    public void write(String username, String mensagem) {
        jedis.sadd("setMessage"+username, mensagem);
    }

    public void printMessages(String user) {
        Set<String> messSet = jedis.smembers("setMessage"+user);
        for(String message: messSet)
            System.out.println(user + ": " + message);
    }

    public List<String> getSubList(String user){
        ArrayList<String> subList = new ArrayList<String>();
        Set<String> subSet = jedis.smembers("set"+user+"Subs");
        for(String sub: subSet)
            subList.add(sub);
        return subList;
    }

    public List<String> getAllKeys() {
        List<String> keyList = new ArrayList<String>();
        Set<String> keySet = jedis.keys("*");
        for(String key: keySet)
            keyList.add(key);
        return keyList;

    }

    public static void getMess(String user, MessageSystem server) {
        for(String subUser : server.getSubList(user)) {
            server.printMessages(subUser);
        }
    }

    public static void writeMess(String user, MessageSystem server, Scanner s) {
        System.out.println("Mensagem:");
        String mess = s.nextLine();
        server.write(user, mess);
    }

    //Subscrever um utilizador
    public static void subUser(String user, MessageSystem server, Scanner s) {
        System.out.println("Subscrever:");
        for(int i = 0; i<server.getUser().size(); i++) {
            System.out.println(Integer.toString(i) + ": " + server.getUser().get(i));
        }
        int subTo = Integer.parseInt(s.nextLine());
        server.sub(user, subTo, server);
    }

    //Menu de seleção de utilizador
    public static void userMenu(MessageSystem j) {
        System.out.println("Escolha o utilizador:");
        int finalI = j.getUser().size();
        for(int i = 0; i<finalI; i++) {
            System.out.println(Integer.toString(i) + ": " + j.getUser().get(i));
        }
        System.out.println(Integer.toString(finalI) + ": Adicionar novo utilizador");
        System.out.println(Integer.toString(finalI + 1) + ": Sair");
    }

    //Menu de seleção de ações que um utilizador pode fazer
    public static void actionMenu() {
        System.out.println("Deseja: \n"
                + "0: Ver mensagens \n"
                + "1: Escrever mensagem \n"
                + "2: Subscrever utilizador \n"
                + "3: Logout");


    }

    public static void main(String[] args) {

        MessageSystem server = new MessageSystem();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        String curentUser = "";

        while(true) {

            //Escolher utilizador
            userMenu(server);
            choice = Integer.parseInt(sc.nextLine());

            //Opção para sair
            if(choice == server.getUser().size()+1) {
                sc.close();
                System.exit(1);

                //Opção de adicionar um novo utilizador
            }else if(choice == server.getUser().size()){
                System.out.println("Insira o nome do novo utilizador:");
                String newUserName = sc.nextLine();
                server.saveUser(newUserName);

                //Opção de ter entrado no perfil de utilizador
            }else {

                //Mensagem de entrada
                curentUser = server.getUser().get(choice);
                System.out.println("Bem vindo(a) " + curentUser);

                //Ciclo while de atividade de um utilizador
                while(true) {

                    //Apresentação e escolha de ação a ser tomada
                    actionMenu();
                    choice = Integer.parseInt(sc.nextLine());

                    //Opção para sair deste utilizador e voltar ao menu de utilizadores
                    if(choice == 3) {
                        break;
                    }
                    switch(choice) {

                        //Opção de leitura das mensagens
                        case 0:
                            getMess(curentUser, server);
                            break;

                        //Opção de escrita de mensagens
                        case 1:
                            writeMess(curentUser, server, sc);
                            break;

                        //Opção de subscrição a um utilizador
                        case 2:
                            subUser(curentUser, server, sc);
                            break;


                    }
                }
            }
        }
    }
}
