package code;

import java.io.*;

public class Converter {
    public static byte[] convertToBytes(Object obj) throws IOException {
        try(ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut)){
            objOut.writeObject(obj);
            return byteOut.toByteArray();
        }
    }

    public static Object convertFromBytes(byte[] buf) throws IOException, ClassNotFoundException{
        try(ByteArrayInputStream byteInput = new ByteArrayInputStream(buf);
            ObjectInputStream objInput = new ObjectInputStream(byteInput)){
            return objInput.readObject();
        }
    }
}
