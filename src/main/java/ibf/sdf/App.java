package ibf.sdf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public final class App {

    public static void main(String[] args) throws NumberFormatException, IOException {
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
        System.out.println("You are X while computer is O.\n");
        for (int i = 0; i < 5; i++) {
            response = dis.readUTF();
            System.out.println(response);
        }

        while (!inputString.equalsIgnoreCase("quit")) {
            System.out.print("\n> ");
            inputString = scan.nextLine();
            dos.writeUTF(inputString);
            dos.flush();
            response = dis.readUTF();
            System.out.println(response);
        }

        dis.close();
        bis.close();
        is.close();
        dos.close();
        bos.close();
        os.close();
    }
}
