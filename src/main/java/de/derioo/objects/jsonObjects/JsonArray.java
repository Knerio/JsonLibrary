package de.derioo.objects.jsonObjects;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JsonArray extends JsonElement implements Iterable<JsonElement>{

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
                this.asSet().stream().map(JsonElement::toString).collect(Collectors.joining(",")) +
                "]";
    }

    public Set<JsonElement> asSet() {
        return new HashSet<>(this.elements);
    }

    @Override
    public JsonElement copy() {
        JsonArray clone = new JsonArray();

        for (JsonElement jsonElement : this.asSet()) {
            clone.add(jsonElement);
        }

        return clone;
    }



    @NotNull
    @Override
    public Iterator<JsonElement> iterator() {
        JsonElement[] set = this.asSet().toArray(new JsonElement[]{});
        AtomicInteger currentIndex = new AtomicInteger(0);
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return currentIndex.get() < set.length;
            }

            @Override
            public JsonElement next() {
                JsonElement element = set[currentIndex.get()];
                currentIndex.set(currentIndex.get() + 1);
                return element;
            }




        };
    }
}
