package code.util;

import code.Command;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GenerateNum {
    public static Integer id(){
        Integer id = (new Random()).nextInt();
        List<Integer> list = Command.getCollection().getList().stream().map(x->x.getId()).collect(Collectors.toList());
        boolean notContains=true;
        for(Integer i:list){
            if(id.equals(i)) notContains=false;
        }
        if(notContains) return id;
        else return id();
    }
}
