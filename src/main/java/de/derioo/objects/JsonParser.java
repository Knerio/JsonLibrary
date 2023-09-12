package de.derioo.objects;

import de.derioo.exceptions.InvalidJsonException;
import de.derioo.exceptions.MalformedJsonExeption;
import de.derioo.objects.jsonObjects.JsonArray;
import de.derioo.objects.jsonObjects.JsonElement;
import de.derioo.objects.jsonObjects.JsonObject;
import de.derioo.objects.jsonObjects.JsonSimple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.naming.spi.DirObjectFactory;
import java.nio.charset.MalformedInputException;
import java.util.*;

public class JsonParser {

    public static JsonElement parseString(String json) {
        try {
            return new JsonParser(json).get();
        } catch (Exception e) {
            throw new InvalidJsonException("'" + json +"' is not valid json");
        }
    }

    private String json;

    public JsonParser(String json) {
        this.json = json;
    }

    public JsonElement get() {
        if (Objects.isNull(this.json)) return null;

        this.json = this.trimStringInNoQuatition(this.json);

        if (!this.json.startsWith("{") && !this.json.startsWith("[")) return new JsonSimple(json);
        if (this.json.startsWith("{") && this.json.endsWith("}")) {
            return this.handleObject(this.json);
        }
        if (this.json.startsWith("[") && this.json.endsWith("]")) {
            return this.handleArray(this.json);
        }
        return null;
    }

    private String trimStringInNoQuatition(String json) {
        StringBuilder newJson = new StringBuilder();
        for (int i = 0; i < json.split("").length; i++) {
            boolean opened = false;
            for (int j = 0; j < json.split("").length; j++) {
                String at = String.valueOf(json.charAt(j));
                if (at.equals("\"")) {
                    opened = !opened;
                }
                if (i == j) {
                    if (!String.valueOf(json.charAt(i)).equals(" ") || opened) {
                        newJson.append(at);
                    }
                    break;
                }
            }
        }
        return newJson.toString();
    }

    private @NotNull JsonElement handleObject(String json) {
        JsonObject object = new JsonObject();


        Set<Integer> list = new HashSet<>();

        json = this.replaceLast(json.replaceFirst("\\{", ""), "}", "");


        String tmp = json;

        while (tmp.contains(",")) {
            list.add(this.findFirst(",", tmp) + list.size());
            tmp = tmp.replaceFirst(",", "");
        }



        Set<Integer> filteredSet = this.filter(list, json);



        List<String> splits = new ArrayList<>();


        for (int i = 0; i < filteredSet.size(); i++) {
            int x = (int) filteredSet.toArray()[i];


            if (i == 0) {
                String substring = json.substring(0, x);
                splits.add(substring);
            }
            if (i == filteredSet.size() - 1) {
                String substring = json.substring(x + 1 );
                splits.add(substring);
            }

            if (i != 0) {
                String substring = json.substring((Integer) filteredSet.toArray()[i - 1] + 1, x + 1);
                splits.add(substring);
            }

        }

        if (filteredSet.size() == 0) splits.add(json);


        for (String split : splits) {
            String key;
            String value;

            Set<Integer> set = new HashSet<>();

            tmp = split;


            while (tmp.contains(":")) {
                set.add(this.findFirst(":", tmp));
                tmp = tmp.replaceFirst(":", "");
            }

            set = this.filter(set, split);
            int columIndex = (int) set.toArray()[0];


            key = this.replaceLast(split.substring(0, columIndex).replaceFirst("\"", ""), "\"", "");
            String substring = split.substring(columIndex + 1);
            value = substring.startsWith("{")  || substring.startsWith("[") ? substring : this.replaceLast(substring.replaceFirst("\"", ""), "\"", "");


            if (value.startsWith("{")) {
                object.add(key, this.handleObject(value.substring(0, substring.length() - 1)));
                continue;
            }

            if (value.startsWith("[")) {
                object.add(key, this.handleArray(value.substring(0, substring.length() - 1)));
                continue;
            }

            object.add(key, new JsonSimple(this.replaceLast(value.replaceFirst("\"", ""), "\"", "")));



        }



        return object;
    }

    private JsonElement handleArray(String json) {
        JsonArray array = new JsonArray();

        Set<Integer> list = new HashSet<>();

        json = this.replaceLast(json.replaceFirst("\\[", ""), "]", "");


        String tmp = json;


        while (tmp.contains(",")) {
            list.add(this.findFirst(",", tmp) + list.size());
            tmp = tmp.replaceFirst(",", "");
        }

        Set<Integer> filteredSet = this.filter(list, json);




        List<String> splits = new ArrayList<>();



        for (int i = 0; i < filteredSet.size(); i++) {
            int x = (int) filteredSet.toArray()[i];


            if (i == 0) {
                String substring = json.substring(0, x - 1);
                splits.add(substring);
            }
            if (i == filteredSet.size() - 1) {
                String substring = json.substring(x + 1 );
                splits.add(substring);
            }

            if (i != 0) {
                String substring = json.substring((Integer) filteredSet.toArray()[i - 1] + 1, x + 1);
                splits.add(substring);
            }

        }

        if (filteredSet.size() == 0) splits.add(json);

        for (String split : splits) {



            String substring = split.substring(1);

            String value = substring.startsWith("{")  || substring.startsWith("[") ? substring : this.replaceLast(substring.replaceFirst("\"", ""), "\"", "");


            if (value.startsWith("{")) {
                array.add(this.handleObject(value));
                continue;
            }

            if (value.startsWith("[")) {
                array.add(this.handleArray(value));
                continue;
            }



            array.add(new JsonSimple(this.replaceLast(value.replaceFirst("\"", ""), "\"", "")));

        }



        return array;
    }

    private int findFirst(String key, @NotNull String content) {
        return content.indexOf(key);
    }

    private @NotNull String replaceLast(@NotNull String input, String target, String replacement) {
        int lastIndex = input.lastIndexOf(target);

        if (lastIndex == -1) {
            return input;
        }

        String beforeLast = input.substring(0, lastIndex);
        String afterLast = input.substring(lastIndex + target.length());

        return beforeLast + replacement + afterLast;
    }

    private Set<Integer> filter(Set<Integer> list, String completeContent) {
        Set<Integer> finalList = new HashSet<>(list);
        for (Integer i : list) {
            boolean opened = false;
            for (int j = 0; j < completeContent.split("").length; j++) {
                String at = String.valueOf(completeContent.charAt(j));
                if (at.equals("\"") || at.equals("[") || at.equals("]") || at.equals("{") || at.equals("}")) {
                    opened = !opened;
                }
                if (i == j) {
                    if (opened) {
                        finalList.remove(i);
                    }
                    break;
                }
            }
        }


        return finalList;
    }


}
