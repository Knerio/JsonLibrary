package de.derioo.objects.jsonObjects;

import java.util.*;

public class JsonArray extends JsonElement {

    private int size;
    private final List<JsonElement> elements;

    public JsonArray(List<JsonElement> elements) {
        this.size = elements.size();
        this.elements = elements;
    }

    public JsonArray() {
        this.size = 0;
        this.elements = new ArrayList<>();
    }

    public int size() {
        return this.size;
    }

    public void add(JsonElement e) {
        this.elements.add(e);
    }

    public void add(Object object) {
        this.elements.add(new JsonSimple(object));
    }

    public void remove(int index) {
        this.elements.remove(index);
    }

    public void remove(JsonElement element) {
        this.elements.remove(element);
    }

    public JsonElement get(int index) {
        return this.elements.get(index);
    }

    @Override
    public String toString() {
        return "[" +
                String.join(",", this.getArrayAsSet()) +
                "]";
    }

    private Set<String> getArrayAsSet() {
        Set<String> set = new HashSet<>();
        for (JsonElement element : this.elements) {
            set.add(element.toString());
        }
        return set;
    }

    @Override
    public JsonElement copy() {
        return null;
    }

    @Override
    public Set<String> keySet() {
        return null;
    }
}
