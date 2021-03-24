package code.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Vehicle implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long enginePower; //Значение поля должно быть больше 0
    private int distanceTravelled; //Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле может быть null

    public Vehicle(String name, Coordinates coordinates, long enginePower, int distanceTravelled, VehicleType type, FuelType fuelType){
        setId();
        this.name = name;
        this.coordinates = coordinates;
        creationDate = new Date();
        this.enginePower = enginePower;
        this.distanceTravelled = distanceTravelled;
        this.type = type;
        this.fuelType = fuelType;

    }

    public Integer getId() {
        return id;
    }

    public void setId() {
        id = GenerateNum.id();
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getEnginePower() {
        return enginePower;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "\nid=" + id +
                "\nname='" + name + '\'' +
                "\ncoordinates=" + coordinates +
                "\ncreationDate=" + creationDate +
                "\nenginePower=" + enginePower +
                "\ndistanceTravelled=" + distanceTravelled +
                "\ntype=" + type +
                "\nfuelType=" + fuelType +
                "\n}";
    }
}




