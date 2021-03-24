package code;

import code.util.Vehicle;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class VehicleCollection implements Serializable {

    private static final long serialVersionUID=3L;

    private Date creationDate;
    private List<Vehicle> list;

    public VehicleCollection(){
        creationDate = new Date();
        list = new LinkedList<>();
    }

    public void setList(List<Vehicle> list) {
        for(Vehicle el:list){
            this.list.add(el);
        }
    }

    public List<Vehicle> getList() {
        return list;
    }

    public void add(Vehicle el){
        list.add(el);
    }

    public Vehicle remove(Vehicle el){
        if(list.remove(el)) return el;
        return null;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
