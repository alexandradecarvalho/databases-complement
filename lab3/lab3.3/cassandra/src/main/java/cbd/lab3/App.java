package cbd.lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Initializing Variables
        KeySpace ks = new KeySpace();
        Table table = new Table();
        Data data = new Data();
        Query query = new Query();
        String current_ks = "cbd";

        // Menu
        int opcao;
        Scanner sc = new Scanner(System.in);
        // Opening scanners
        Scanner sc2 = new Scanner(System.in);
        Scanner sc3 = new Scanner(System.in);
        Scanner sc4 = new Scanner(System.in);
        Scanner sc5 = new Scanner(System.in);
        Scanner sc7 = new Scanner(System.in);
        Scanner cols_sc = new Scanner(System.in);
        Scanner pksc = new Scanner(System.in);
        Scanner sc8 = new Scanner(System.in);
        Scanner sc9 = new Scanner(System.in);
        Scanner sc10 = new Scanner(System.in);
        Scanner sc11 = new Scanner(System.in);
        Scanner sc12 = new Scanner(System.in);
        Scanner sc13 = new Scanner(System.in);
        Scanner sc14 = new Scanner(System.in);
        Scanner sc15 = new Scanner(System.in);
        Scanner sc16 = new Scanner(System.in);
        Scanner sc17 = new Scanner(System.in);
        Scanner sc18 = new Scanner(System.in);
        Scanner sc19 = new Scanner(System.in);
        Scanner sc20 = new Scanner(System.in);

        do {
            System.out.println("\n\n### CASSANDRA INTERACTION###");
            System.out.println("                  ===============================\n");
            System.out.println("                  |     1 - Listar Keyspaces      |");
            System.out.println("                  |     2 - Criar Keyspace        |");
            System.out.println("                  |     3 - Alterar Keyspace      |");
            System.out.println("                  |     4 - Eliminar Keyspace     |");
            System.out.println("                  |     5 - Usar Keyspace         |");
            System.out.println("                  |     6 - Listar Tabelas        |");
            System.out.println("                  |     7 - Criar Tabela          |");
            System.out.println("                  |     8 - Alterar Tabela        |");
            System.out.println("                  |     9 - Eliminar Tabela       |");
            System.out.println("                  |    10 - Truncar Tabela        |");
            System.out.println("                  |    11 - Criar Índice          |");
            System.out.println("                  |    12 - Eliminar Índice       |");
            System.out.println("                  |    13 - Inserir Valores       |");
            System.out.println("                  |    14 - Ler Valores           |");
            System.out.println("                  |    15 - Atualizar Valores     |");
            System.out.println("                  |    16 - Eliminar Valores      |");
            System.out.println("                  |    17 - Videos P/ Autor       |");
            System.out.println("                  |    18 - Comentários P/ User   |");
            System.out.println("                  |    19 - Comentários P/ Vídeo  |");
            System.out.println("                  |    20 - Rating P/ Vídeo       |");
            System.out.println("                  |     0 - Sair                  |");
            System.out.println("                  ===============================\n");

            System.out.println("\n Opção: ");
            opcao = sc.nextInt();
            System.out.print("\n");

            switch (opcao) {
                case 1:
                    ks.describeKeySpaces();
                    break;
                case 2:
                    System.out.println("Insert the name of the new keyspace: ");
                    String newkeyspace_name = sc2.next();
                    ks.createKeySpace(newkeyspace_name);
                    break;
                case 3:
                    System.out.println("Insert the name of the keyspace to be altered: ");
                    String alteredkeyspace_name = sc3.next();
                    System.out.println("Insert the new replication factor: ");
                    int replication_factor = sc3.nextInt();
                    ks.alterKeySpace(alteredkeyspace_name, replication_factor);
                    break;
                case 4:
                    System.out.println("Insert the name of the keyspace to be deleted: ");
                    String deletedkeyspace_name = sc4.next();
                    ks.deleteKeySpace(deletedkeyspace_name);
                    break;
                case 5:
                    System.out.println("Insert the name of the keyspace you want to use: ");
                    String usingkeyspace = sc5.next();
                    ks.useKeySpace(usingkeyspace);
                    current_ks = usingkeyspace;
                    break;
                case 6:
                    table.describeColumnFamilies(current_ks);
                    break;
                case 7:
                    System.out.println("Insert the name of the new table: ");
                    String newtable_name = sc7.next();
                    HashMap<String, String> columns = new HashMap<>();
                    String col_name;
                    String col_type;
                    do{
                        System.out.println("Insert table's column, a space and its type, and then write \"out\" to finish: ");
                        col_name = cols_sc.next();
                        col_type = cols_sc.next();
                        if (!col_name.equals("out") && !col_type.equals("out"))
                            columns.put(col_name, col_type);
                    }while(!col_name.equals("out") && !col_type.equals("out"));

                    ArrayList<String> pks = new ArrayList<>();
                    System.out.println("Insert one primary key at a time and then \"out\" to finish: ");
                    String pk;
                    do{
                        pk = pksc.next();
                        if(!pk.equals("out"))
                            pks.add(pk);
                    }while(!pk.equals("out"));
                    table.createTable(newtable_name, columns, pks);
                    break;
                case 8:
                    System.out.println("Insert the name of the table to be altered: ");
                    String name = sc8.next();
                    System.out.println("Insert type of alteration (add/delete): ");
                    String alteration = sc8.next();
                    System.out.println("Insert the name of the column: ");
                    String col = sc8.next();
                    if(alteration.equals("add")){
                        System.out.println("Insert type of the column: ");
                        String type = sc8.next();
                        table.addCol(name, col, type);
                    }
                    else if(alteration.equals("delete"))
                        table.deleteCol(name, col);
                    else
                        System.out.println("Invalid alteration.");
                    break;
                case 9:
                    System.out.println("Insert the name of the table to be deleted: ");
                    String tablename = sc9.next();
                    table.dropTable(tablename);
                    break;
                case 10:
                    System.out.println("Insert the name of the table to be truncated: ");
                    String truncatedname = sc10.next();
                    table.truncateTable(truncatedname);
                    break;
                case 11:
                    System.out.println("Insert the name of the index to be created: ");
                    String idx_name = sc11.next();
                    System.out.println("Insert the name of the table: ");
                    String idx_table = sc11.next();
                    System.out.println("Insert the name of the column: ");
                    String idx_col = sc11.next();
                    table.createIndex(idx_name, idx_table, idx_col);
                    break;
                case 12:
                    System.out.println("Insert the name of the index to be deleted: ");
                    String idx = sc12.next();
                    table.dropIndex(idx);
                    break;
                case 13:
                    System.out.println("Insert the name of the table: ");
                    String newdata_table = sc13.nextLine();
                    ArrayList<String> table_columns = new ArrayList<>();
                    String cols;
                    do{
                        System.out.println("Insert one table's column at a time, and then write \"out\" to finish: ");
                        cols = sc13.nextLine();
                        if(!cols.equals("out"))
                            table_columns.add(cols);
                    }while(!cols.equals("out"));

                    ArrayList<String> values = new ArrayList<>();
                    String datavalue;
                    do{
                        System.out.println("Insert one value at a time and then \"out\" to finish: ");
                        datavalue = sc13.nextLine();
                        if(!datavalue.equals("out"))
                            values.add(datavalue);
                    }while(!datavalue.equals("out"));
                    data.createData(newdata_table, table_columns, values);
                    break;
                case 14:
                    System.out.println("Insert the name of the table to be read: ");
                    String readtable = sc14.nextLine();
                    data.readData(readtable);
                    break;
                case 15:
                    System.out.println("Insert the name of the table to be updated: ");
                    String uptable = sc15.nextLine();
                    System.out.println("Insert the name of the column to be updated: ");
                    String upcol = sc15.nextLine();
                    System.out.println("Insert the new value of the column: ");
                    String upval = sc15.nextLine();
                    System.out.println("Insert the name of the column to be verified: ");
                    String dcol = sc15.nextLine();
                    System.out.println("Insert the value of the column that triggers the change: ");
                    String dval = sc15.nextLine();
                    data.updateData(uptable, upcol, upval, dcol, dval);
                    break;
                case 16:
                    System.out.println("Insert the name of the table to delete data from: ");
                    String deltable = sc16.nextLine();
                    System.out.println("Insert the name of the column to be deleted: ");
                    String delcol = sc16.nextLine();
                    System.out.println("Insert the value of the column to delete: ");
                    String delval = sc16.nextLine();
                    data.deleteData(deltable, delcol, delval);
                    break;
                case 17:
                    System.out.println("Insert the email of the author: ");
                    String author = sc17.next();
                    query.vidAuthor(author);
                    break;
                case 18:
                    System.out.println("Insert the email of the user: ");
                    String user = sc18.next();
                    query.commentUser(user);
                    break;
                case 19:
                    System.out.println("Insert the id of the video: ");
                    int id = sc19.nextInt();
                    query.commentVideo(id);
                    break;
                case 20:
                    System.out.println("Insert the id of the video: ");
                    int rid = sc20.nextInt();
                    query.ratingVideo(rid);
                    break;
                case 0:
                    sc.close();
                    sc2.close();
                    sc3.close();
                    sc4.close();
                    sc5.close();
                    sc7.close();
                    cols_sc.close();
                    pksc.close();
                    sc8.close();
                    sc9.close();
                    sc10.close();
                    sc11.close();
                    sc12.close();
                    sc13.close();
                    sc14.close();
                    sc15.close();
                    sc16.close();
                    sc17.close();
                    sc18.close();
                    sc19.close();
                    sc20.close();
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        } while (opcao != 0);
        sc.close();
        System.exit(0);
    }
}
