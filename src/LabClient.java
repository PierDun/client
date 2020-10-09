import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LabClient {

    private static final int PORT = 8128;
    private static final String HOST = "localhost";

    private static String[] readAndParseCommand() {
        StringBuilder command = new StringBuilder();
        Scanner scan = new Scanner(System.in);
        String nextline;
        try {
            int counter;
            do {
                counter = 0;
                nextline = scan.nextLine();
                command.append(nextline);
                char[] commands = command.toString().toCharArray();
                for (char symbol : commands) {
                    if (symbol == '{') counter++;
                    if (symbol == '}') counter--;
                }
            } while (counter != 0 && counter > 0);
        } catch (NullPointerException | NoSuchElementException ex) {
            command = new StringBuilder("null null");
        }
        if (command.toString().trim().split(" ", 2).length > 1) {return command.toString().trim().split(" ", 2);}
        else {return command.append(" null").toString().trim().split(" ", 2);}
    }

    public static void main(String[] args){

        ArrayList<Chest> mainSet;
        Socket socket;

        try{
                while(true){
                    System.out.println("Введите команду (info - команда для справки):");
                    String[] comands = readAndParseCommand();

                    switch(comands[0]) {
                        case "exit":
                            System.exit(0);
                            break;
                        case "info":
                            System.out.println("exit - Завершение работы.");
                            System.out.println("clear - Очистить коллекцию.");
                            System.out.println("add {JsonObj} - Добавить в коллекцию элемент.");
                            System.out.println("add_if_max {JsonObj} - Добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.");
                            System.out.println("add_if_min {JsonObj} - Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
                            System.out.println("remove_lower {JsonObj} - Удалить из коллекции все элементы, меньшие, чем заданный");
                            break;
                        case "null":
                            System.exit(0);
                            break;
                        default:
                            socket = new Socket(HOST,PORT);
                            try(OutputStream out = socket.getOutputStream();
                            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                            out.write((comands[0] + " " + comands[1]).getBytes());
                            out.flush();
                            try {
                                mainSet = (ArrayList<Chest>) in.readObject();
                                mainSet.forEach(Chest::printInfo);

                            } catch (ClassNotFoundException | EOFException e) {
                                System.out.println("Не удалось считиать коллекцию.");
                                break;
                            }
                            }
                            break;
                    }
            }

        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу.");
        }
    }
}