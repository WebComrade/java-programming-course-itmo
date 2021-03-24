package code.util;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private static final long serialVersionUID=2L;
    private Double x; //Значение поля должно быть больше -750
    private Long y;

    public Coordinates(){

    }

    public Coordinates(double x, long y){
        setX(x);
        setY(y);
    }

    public Double getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public boolean setX(double x){
        if(x<=-750){
            return false;
        }
        this.x = x;
        return true;
    }

    public void setY(long y){
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
