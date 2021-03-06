package Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Json {

    public static Gson gson;
    static {
        var builder = new GsonBuilder();
        builder.setPrettyPrinting();

        gson = builder.create();
    }


    public static <T> T read(String path, Class<T> type) {
        T object = null;
        try (FileReader fileReader = new FileReader(path)) {
            object = gson.fromJson(fileReader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (object instanceof PostLoadable) {
            ((PostLoadable) object).load();
        }
        return object;
    }

    public static void write(Object object, String path) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(object, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
