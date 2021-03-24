package client;

import code.Command;
import code.Converter;
import code.Processable;
import code.util.Coordinates;
import code.util.FuelType;
import code.util.Vehicle;
import code.util.VehicleType;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {


    private static Vehicle createVehicle(){
        Scanner reader = new Scanner(System.in);
        String line="";
        while(line.equals("")||line.startsWith(" ")) {
            System.out.println("Введите имя:");
            line = reader.nextLine();
        }
        String name=line;
        line="";
        Coordinates coordinates = new Coordinates();
        while(coordinates.getX()==null){
            try{
                System.out.println("Введите координату x (тип double):");
                line=reader.nextLine();
                if(coordinates.setX(Double.parseDouble(line))){
                    break;
                }
                System.out.println("Число должно быть больше -750");
            }
            catch (NumberFormatException e){
                System.out.println("Неверный формат числа");
            }
        }
        while(coordinates.getY()==null){
            try{
                System.out.println("Введите координату y (тип long):");
                line = reader.nextLine();
                coordinates.setY(Long.parseLong(line));
            }
            catch (NumberFormatException e){
                System.out.println("Неверный формат числа");
            }
        }
        Long enginePower=null;
        while(enginePower==null){
            try{
                System.out.println("Введите мощность двигателя (тип long):");
                line = reader.nextLine();
                enginePower = Long.parseLong(line);
                if(enginePower<=0){
                    System.out.println("Число должно быть больше 0");
                    enginePower=null;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Неверный формат числа");
            }
        }
        Integer distanceTravelled=null;
        while (distanceTravelled==null){
            try{
                System.out.println("Введите пройденное расстояние (тип int):");
                line = reader.nextLine();
                distanceTravelled = Integer.parseInt(line);
                if(distanceTravelled<=0){
                    System.out.println("Число должно быть больше 0");
                    distanceTravelled=null;
                }
            }
            catch (NumberFormatException e){
                System.out.println("Неверный формат числа");
            }
        }
        VehicleType vehicleType = null;
        while(vehicleType==null){
            System.out.println("Введите тип транспорта:\n" +
                    "[SubMarine, Ship, Chopper, SpaceShip]");
            line=reader.nextLine();
            if(line.toLowerCase().equals(VehicleType.CHOPPER.toString().toLowerCase())){
                vehicleType = VehicleType.CHOPPER;
            }
            else if(line.toLowerCase().equals(VehicleType.SUBMARINE.toString().toLowerCase())){
                vehicleType = VehicleType.SUBMARINE;
            }
            else if(line.toLowerCase().equals(VehicleType.SHIP.toString().toLowerCase())){
                vehicleType = VehicleType.SHIP;
            }
            else if(line.toLowerCase().equals(VehicleType.SPACESHIP.toString().toLowerCase())){
                vehicleType = VehicleType.SPACESHIP;
            }
            else if(line.equals("")) break;
            else
                System.out.println("Такого транспорта не существует");
        }
        FuelType fuelType = null;
        while (fuelType==null) {
            System.out.println("Введите тип топлива:\n" +
                    "[Gasoline, Kerosene, Plasma, Antimatter]");
            line = reader.nextLine();
            if (line.toLowerCase().equals(FuelType.GASOLINE.toString().toLowerCase())) {
                fuelType = FuelType.GASOLINE;
            } else if (line.toLowerCase().equals(FuelType.KEROSENE.toString().toLowerCase())) {
                fuelType = FuelType.KEROSENE;
            } else if (line.toLowerCase().equals(FuelType.PLASMA.toString().toLowerCase())) {
                fuelType = FuelType.PLASMA;
            } else if (line.toLowerCase().equals(FuelType.ANTIMATTER.toString().toLowerCase())) {
                fuelType = FuelType.ANTIMATTER;
            } else if (line.equals("")) break;
            else
                System.out.println("Такого типа топлива не существует");
        }
        return new Vehicle(name,coordinates,enginePower,distanceTravelled,vehicleType,fuelType);
    }

    private static Vehicle createVehicleScript(BufferedReader reader)throws IOException{

        String line = reader.readLine();
        String name;
        if(line.equals("")||line.startsWith(" "))
            name="default";
        else name=line;

        line = reader.readLine();
        Coordinates coordinates = new Coordinates();
        Double x;
        try{
            x = Double.parseDouble(line);
            if(x<=750) x = 750d;
        }
        catch (NumberFormatException e){
            x = 750d;
        }
        coordinates.setX(x);
        line = reader.readLine();
        Long y;
        try{
            y = Long.parseLong(line);
        }
        catch (NumberFormatException e){
            y=0l;
        }
        coordinates.setY(y);

        line = reader.readLine();
        Long enginePower;
        try{
            enginePower = Long.parseLong(line);
            if(enginePower<=0) enginePower=1l;
        }
        catch (NumberFormatException e){
            enginePower=1l;
        }

        line = reader.readLine();
        Integer distanceTravelled;
        try{
            distanceTravelled = Integer.parseInt(line);
            if(distanceTravelled<=0) distanceTravelled=1;
        }
        catch (NumberFormatException e){
            distanceTravelled=1;
        }

        line = reader.readLine();
        VehicleType vehicleType;
        if(line.toLowerCase().equals(VehicleType.CHOPPER.toString().toLowerCase())) vehicleType = VehicleType.CHOPPER;
        else if(line.toLowerCase().equals(VehicleType.SHIP.toString().toLowerCase())) vehicleType = VehicleType.SHIP;
        else if(line.toLowerCase().equals(VehicleType.SPACESHIP.toString().toLowerCase())) vehicleType = VehicleType.SPACESHIP;
        else if(line.toLowerCase().equals(VehicleType.SUBMARINE.toString().toLowerCase())) vehicleType = VehicleType.SUBMARINE;
        else vehicleType=null;

        line = reader.readLine();
        FuelType fuelType;
        if(line.toLowerCase().equals(FuelType.ANTIMATTER.toString().toLowerCase())) fuelType = FuelType.ANTIMATTER;
        else if(line.toLowerCase().equals(FuelType.GASOLINE.toString().toLowerCase())) fuelType = FuelType.GASOLINE;
        else if(line.toLowerCase().equals(FuelType.KEROSENE.toString().toLowerCase())) fuelType = FuelType.KEROSENE;
        else if(line.toLowerCase().equals(FuelType.PLASMA.toString().toLowerCase())) fuelType = FuelType.PLASMA;
        else fuelType=null;

        return new Vehicle(name,coordinates,enginePower,distanceTravelled,vehicleType,fuelType);
    }

    private static void scriptWork(String fileName){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = ")";
            while(!line.equals(null)){
                line = reader.readLine();
                if(line.equals("help")){
                    sendMsg(Command.help());
                }
                else if(line.equals("show")){
                    sendMsg(Command.show());
                }
                else if(line.equals("info")){
                    sendMsg(Command.info());
                }
                else if(line.startsWith("update ")){
                    line =
                            line.replaceAll("update","").replaceAll(" ","");
                    sendMsg(Command.updateId(argToInt(line)));
                }
                else if(line.startsWith("remove_by_id ")){
                    line =
                            line.replaceAll("remove_by_id","").replaceAll(" ","");
                    sendMsg(Command.removeId(argToInt(line)));
                }
                else if(line.equals("clear")){
                    sendMsg(Command.clear());
                }
                else if(line.equals("save")){
                    sendMsg(Command.save());
                }
                else if(line.startsWith("execute_script ")){
                    System.out.println("Вызов скрипта в скрипте запрещен");
                    continue;
                }
                else if(line.equals("exit")){
                    System.out.println("Процесс завершен");
                    return;
                }
                else if(line.equals("remove_first")){
                    sendMsg(Command.removeFirst());
                }
                else if(line.equals("head")){
                    sendMsg(Command.head());
                }
                else if(line.equals("remove_head")){
                    sendMsg(Command.removeHead());
                }
                else if(line.equals("average_of_engine_power")){
                    sendMsg(Command.averageOfEnginePower());
                }
                else if(line.equals("print_ascending")){
                    sendMsg(Command.printAscending());
                }
                else if(line.equals("print_field_descending_fuel_type")){
                    sendMsg(Command.printFieldDescendingFuelType());
                }
                else if(line.equals("add")) {
                    sendMsg(Command.add(createVehicleScript(reader)));
                }
                else{
                    System.out.println("Такой команды не существует\nВведите \'help\', чтобы получить весь список комманд\n");
                    continue;
                }

                DatagramPacket packet = new DatagramPacket(bufIn, bufIn.length);
                socket.receive(packet);
                System.out.println(new String(packet.getData(),0, packet.getLength())+"\n");
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Файл не найден");
            return;
        } catch (IOException e) {
            System.out.println("IOException");
            return;
        }
        catch (NullPointerException e){
            System.out.println("Скрипт завершен");
            return;
        }
//        catch (EOFException e){
//            System.out.println("Конец файла");
//            return;
//        }
    }

    private static Integer argToInt(String s){
        try{
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e){
            System.out.println("Неверный аргумент");
            System.out.println(s);
            return null;
        }
    }

    private static DatagramSocket socket;
    private static InetAddress address;
    private static byte[] bufIn= new byte[65*1024];
    private static byte[] bufOut;

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            Scanner sc = new Scanner(System.in);
            String line;

            while (true){
                line = sc.nextLine();
                if(line.equals("help")){
                    sendMsg(Command.help());
                }
                else if(line.equals("show")){
                    sendMsg(Command.show());
                }
                else if(line.equals("info")){
                    sendMsg(Command.info());
                }
                else if(line.equals("add")) {
                    sendMsg(Command.add(createVehicle()));
                }
                else if(line.startsWith("update ")){
                    line =
                            line.replaceAll("update","").replaceAll(" ","");
                    sendMsg(Command.updateId(argToInt(line)));
                }
                else if(line.startsWith("remove_by_id ")){
                    line =
                            line.replaceAll("remove_by_id","").replaceAll(" ","");
                    sendMsg(Command.removeId(argToInt(line)));
                }
                else if(line.equals("clear")){
                    sendMsg(Command.clear());
                }
                else if(line.equals("save")){
                    sendMsg(Command.save());
                }
                else if(line.startsWith("execute_script ")){
                    line = line.replaceAll("execute_script","").replaceAll(" ","");
                    scriptWork(line);
                    continue;
                }
                else if(line.equals("exit")){
                    System.out.println("Процесс завершен");
                    return;
                }
                else if(line.equals("remove_first")){
                    sendMsg(Command.removeFirst());
                }
                else if(line.equals("head")){
                    sendMsg(Command.head());
                }
                else if(line.equals("remove_head")){
                    sendMsg(Command.removeHead());
                }
                else if(line.equals("average_of_engine_power")){
                    sendMsg(Command.averageOfEnginePower());
                }
                else if(line.equals("print_ascending")){
                    sendMsg(Command.printAscending());
                }
                else if(line.equals("print_field_descending_fuel_type")){
                    sendMsg(Command.printFieldDescendingFuelType());
                }
                else{
                    System.out.println("Такой команды не существует\nВведите \'help\', чтобы получить весь список комманд");
                    continue;
                }

                DatagramPacket packet = new DatagramPacket(bufIn, bufIn.length);
                socket.receive(packet);
                System.out.println(new String(packet.getData(),0, packet.getLength()));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void sendMsg(Processable command)throws IOException {
        bufOut = Converter.convertToBytes(command);
        DatagramPacket packet = new DatagramPacket(bufOut, bufOut.length,address,4221);
        socket.send(packet);
    }
}
