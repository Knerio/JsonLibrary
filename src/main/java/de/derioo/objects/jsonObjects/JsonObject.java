package de.derioo.objects.jsonObjects;

import java.util.*;
import java.util.stream.Collectors;

public class JsonObject extends JsonElement {

    private final Map<String, JsonElement> entries;


    public JsonObject() {
        this.entries = new HashMap<>();
    }


    public JsonElement get(String key) {
        if (!entries.containsKey(key)) {
            throw new IllegalStateException("'" + key + "' dont exists in " + this.toString());
        }

        return this.entries.get(key);
    }

    public void add(String key, JsonElement e) {
        this.entries.put(key, e);
    }

    public void addProperty(String key, String value) {
        this.entries.put(key, value == null ? null : new JsonSimple(value));
    }

    public void addProperty(String key, int value) {
        this.entries.put(key, new JsonSimple(value));
    }

    public void addProperty(String key, double value) {
        this.entries.put(key, new JsonSimple(value));
    }

    public void addProperty(String key, long value) {
        this.entries.put(key, new JsonSimple(value));
    }

    public void addProperty(String key, float value) {
        this.entries.put(key, new JsonSimple(value));
    }

    public void addProperty(String key, Object value) {
        this.entries.put(key, new JsonSimple(value));
    }


    @Override
    public Set<String> keySet() {
        Set<String> set = new HashSet<>();
        this.entries.forEach((s, element) -> {
            set.add(s);
        });
        return set;
    }

    @Override
    public String toString() {
        return "{" +
                String.join(",", this.getObjectsAsSet()) +
                "}";
    }

    private Set<String> getObjectsAsSet() {
        Set<String> set = new HashSet<>();
        for (String s : this.entries.keySet()) {
            String string = "\"" + s + "\":" + this.get(s).toString();
            set.add(string);
        }
        return set;
    }

    @Override
    public JsonElement copy() {
        JsonObject o = new JsonObject();

        for (String s : this.keySet()) {
            o.add(s, this.get(s));
        }

        return o;
    }

    @Override
    public boolean contains(String key) {
        return entries.containsKey(key);
    }
}
