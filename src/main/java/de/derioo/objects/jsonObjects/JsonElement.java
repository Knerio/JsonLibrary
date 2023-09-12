package de.derioo.objects.jsonObjects;


import java.util.Set;

public abstract class JsonElement {



    public boolean getAsBoolean() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public int getAsInt() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public double getAsDouble() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public float getAsFloat() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public long getAsLong() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public Number getAsNumber() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public String getAsString() {
        throw new IllegalStateException("Not a supported Operation of: " + this.getClass().getSimpleName());
    }

    public abstract JsonElement copy();

    public abstract Set<String> keySet();

    public String toString() {
        if (this.isJsonSimple()) return this.getAsJsonSimple().toString();
        if (this.isJsonArray()) return this.getAsJsonArray().toString();
        if (this.isJsonObject()) return this.getAsJsonObject().toString();
        throw new IllegalStateException("Element isn't a JsonSimple, JsonArray or JsonObject");
    }

    public boolean isJsonObject() {
        return this instanceof JsonObject;
    }

    public JsonObject getAsJsonObject() {
        if (!this.isJsonObject()) {
            throw new IllegalStateException("Not a Json Object:" + this.getClass().getSimpleName());
        }

        return (JsonObject) this;
    }

    public boolean isJsonSimple() {
        return this instanceof JsonSimple;
    }

    public JsonSimple getAsJsonSimple() {
        if (!this.isJsonSimple()) {
            throw new IllegalStateException("Not a Json JsonSimple:" + this.getClass().getSimpleName());
        }

        return (JsonSimple) this;
    }


    public boolean isJsonArray() {
        return this instanceof JsonArray;
    }

    public JsonArray getAsJsonArray() {
        if (!this.isJsonArray()) {
            throw new IllegalStateException("Not a Json Array:" + this);
        }

        return (JsonArray) this;
    }

}
