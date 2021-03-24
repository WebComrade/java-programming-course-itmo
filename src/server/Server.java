package server;

import code.Command;
import code.Converter;
import code.Processable;
import code.VehicleCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    private static DatagramSocket socket;
    private static byte[] buf;

    public static void main(String[] args) {
        try {
            run();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
    }

    private static void run() throws SocketException {
        socket = new DatagramSocket(4221);

        //setting up collection
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.json"));
            String str = "", s = null;
            while ((s = reader.readLine()) != null) {
                str += s+"\n";
            }
            Gson GSON = new GsonBuilder().setPrettyPrinting().create();

            VehicleCollection collection = GSON.fromJson(str,VehicleCollection.class);
            Command.setCollection(collection);
        }
        catch (FileNotFoundException e){
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("data.json"));
                writer.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        // client handler
        while (true){
            buf = new byte[65*1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);


            try {
                socket.receive(packet);
                Processable com = (Processable)Converter.convertFromBytes(packet.getData());
                String str = com.handle();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                buf = str.getBytes();
                packet = new DatagramPacket(buf, buf.length,address,port);
                socket.send(packet);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (ClassCastException e){
                e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
