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
            StringBuilder builder = new StringBuilder();
            builder.append("\"").append(s).append("\":");
            if (this.isJsonSimple()) {
                builder.append("\"");
                builder.append(this.get(s).toString());
                builder.append("\"");
            } else {
                builder.append(this.get(s).toString());
            }

            set.add(builder.toString());
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


}
