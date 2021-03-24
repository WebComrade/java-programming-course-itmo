package code;

import code.util.FuelType;
import code.util.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Command implements Processable, Serializable {
    private static VehicleCollection collection=new VehicleCollection();

    public static void setCollection(VehicleCollection col) {
        collection = col;
    }

    private Command(){

    }

    private Command(Vehicle veh){

    }

    public static Processable help(){
        return new Command() {
            private static final long serialVersionUID=10L;
            @Override
            public String handle() {
                return "help : вывести справку по доступным командам\n" +
                        "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                        "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "add {element} : добавить новый элемент в коллекцию\n" +
                        "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                        "remove_by_id id : удалить элемент из коллекции по его id\n" +
                        "clear : очистить коллекцию\n" +
                        "save : сохранить коллекцию в файл\n" +
                        "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                        "exit : завершить программу (без сохранения в файл)\n" +
                        "remove_first : удалить первый элемент из коллекции\n" +
                        "head : вывести первый элемент коллекции\n" +
                        "remove_head : вывести первый элемент коллекции и удалить его\n" +
                        "average_of_engine_power : вывести среднее значение поля enginePower для всех элементов коллекции\n" +
                        "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                        "print_field_descending_fuel_type : вывести значения поля fuelType всех элементов в порядке убывания";
            }
        };
    }

    public static Processable info(){
        return new Command() {
            private static final long serialVersionUID=11L;
            @Override
            public String handle() {
                return "Collection: "+collection.getClass().getSimpleName()+ "\n"+
                       "InitializationDate: "+collection.getCreationDate()+"\n"+
                       "ElementsCount: "+collection.getList().size()+"";
            }
        };
    }

    public static Processable show(){
        return new Command() {
            private static final long serialVersionUID=12L;
            @Override
            public String handle() {
                String text="";
                for(Vehicle vehicle:collection.getList()){
                    text+=vehicle.toString()+"\n";
                }
                if(text.equals("")) return "Коллекция пуста";
                return text;
            }
        };
    }
    public static Processable add(Vehicle vehicle){
        return new Command() {
            private static final long serialVersionUID=13L;
            private Vehicle veh=vehicle;


            @Override
            public String handle() {
                collection.add(veh);
                return "Элемент успешно добавлен";
            }
        };
    }

    public static Processable updateId(Integer id){
        return new Command() {
            private static final long serialVersionUID=14L;
            Integer arg = id;
            @Override
            public String handle() {
                try {
                    Vehicle vehicle =
                            getCollection().remove(collection.getList().stream().filter(v -> v.getId().equals(arg)).findFirst().get());
                    if (vehicle != null) {
                        vehicle.setId();
                        collection.add(vehicle);
                        return "Элемент успешно обновлен";
                    } else return "Такого элемента не существует";
                }
                catch (NoSuchElementException e){
                    return "Такого элемента не существует";
                }
            }
        };
    }

    public static Processable removeId(Integer id){
        return new Command() {
            private static final long serialVersionUID=15L;
            Integer arg = id;
            @Override
            public String handle() {
                try {
                    if (getCollection().remove(collection.getList().stream().filter(v -> v.getId().equals(arg)).findFirst().get()) != null)
                        return "Элемент успешно удален";
                    else return "Такого элемента не существует";
                }
                catch (NoSuchElementException e){
                    return "Такого элемента не существует";
                }
            }
        };
    }

    public static Processable clear(){
        return new Command() {
            private static final long serialVersionUID=16L;
            @Override
            public String handle() {
                List<Vehicle> list = collection.getList();
                list.clear();
                collection.setList(list);
                return "Коллекция очищена";
            }
        };
    }

    public static Processable removeFirst(){
        return new Command() {
            private static final long serialVersionUID=17L;
            @Override
            public String handle() {
                if(!collection.getList().isEmpty()) {
                    collection.remove(collection.getList().get(0));
                    return "Элемент успешно удален";
                }
                else return "Коллекция пуста";
            }
        };
    }

    public static Processable head(){
        return new Command() {
            @Override
            public String handle() {
                if(!collection.getList().isEmpty())
                return getCollection().getList().get(0).toString();
                else return "Коллекция пуста";
            }
        };
    }

    public static Processable removeHead(){
        return new Command() {
            private static final long serialVersionUID=18L;
            @Override
            public String handle() {
                if(!collection.getList().isEmpty())
                return collection.remove(collection.getList().get(0))+"\nЭлемент успешно удален";
                else return "Коллекция пуста";
            }
        };
    }

    public static Processable averageOfEnginePower(){
        return new Command() {
            private static final long serialVersionUID=19L;
            @Override
            public String handle() {
                if(!collection.getList().isEmpty())
                return "Среднее значение Engine Power: "+
                        getCollection().getList().stream().map(Vehicle::getEnginePower).mapToLong(x -> x).average().getAsDouble()+"";
                else return "Коллекция пуста";
            }
        };
    }

    public static Processable printAscending(){
        return new Command() {
            private static final long serialVersionUID=20L;
            @Override
            public String handle() {
                String txt="";
                List<Vehicle> list =
                        getCollection().getList().stream().sorted(Comparator.comparing(Vehicle::getId)).collect(Collectors.toList());
                for(Vehicle vehicle:list){
                    txt+=vehicle.toString()+"\n";
                }
                return txt;
            }
        };
    }

    public static Processable printFieldDescendingFuelType(){
        return new Command() {
            private static final long serialVersionUID=21L;
            @Override
            public String handle() {
                String txt="";
                List<FuelType> list =
                        getCollection().getList().stream().sorted(Comparator.comparing(Vehicle::getId).reversed()).map(x->x.getFuelType()).collect(Collectors.toList());
                for(FuelType fl:list){
                    txt+=fl.toString()+"\n";
                }
                return txt;
            }
        };
    }

    public static Processable save(){
        return new Command() {
            @Override
            public String handle() {
                try {
                    Gson GSON = new GsonBuilder().setPrettyPrinting().create();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("data.json"));
                    String res = GSON.toJson(getCollection());
                    writer.write(res);
                    writer.close();
                    return "Коллекция успешно сохранена";
                }
                catch (IOException e){
                    return "IOEXCEPTION";
                }
            }
        };
    }

    public static VehicleCollection getCollection() {
        return collection;
    }
}
