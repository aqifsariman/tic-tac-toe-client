package ibf.sdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public final class App {

    public static void main(String[] args) throws NumberFormatException, IOException, EOFException {
        Socket socket = new Socket("localhost", Integer.parseInt(args[0]));

        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bis);

        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bos);

        Scanner scan = new Scanner(System.in);
        String inputString = "";
        String response = "";
        String winner = "";
        System.out.println("TIC TAC TOE GAME");
        System.out.println("You are X while computer is O.");
        // ADDING SOME RED COLOR TO PRINT FOR WARNING
        System.out.println("\u001B[31mIf you choose a spot that is already taken, you will miss a turn.\u001B[0m");
        System.out.println("\u001B[31mInvalid inputs outside the range 1 - 9 will not be entertained.\u001B[0m\n");

        // READING INPUTS FOR PRINTED BOARD
        for (int i = 0; i < 3; i++) {
            response = dis.readUTF();
            System.out.println(response);
        }

        while (!inputString.equalsIgnoreCase("quit")) {
            System.out.print("\n> ");
            inputString = scan.nextLine();
            if (inputString.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            // TAKING INPUT FROM CONSOLE TO SEND TO SERVER
            dos.writeUTF(inputString);
            dos.flush();
            System.out.println("\n");

            // TAKING INPUT FROM SERVER AND PRINTING ONTO CLIENT CONSOLE
            for (int i = 0; i < 4; i++) {
                response = dis.readUTF();
                System.out.println(response);
            }
            if (response.contains("Goodbye!")) {
                System.exit(0);
            }
        }

        // CLOSING ALL STREAMS
        dis.close();
        bis.close();
        is.close();
        dos.close();
        bos.close();
        os.close();
    }
}
