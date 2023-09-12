package de.derioo.objects.jsonObjects;


import java.util.Set;

public class JsonSimple extends JsonElement {


    private final Object value;

    public JsonSimple(Object value) {
        this.value = value;
    }


    @Override
    public String getAsString() {
        return this.toString();
    }

    @Override
    public String toString() {
        if (!this.isDouble() && !this.isFloat() && !this.isInt() && !this.isLong())
            return "\"" + this.value.toString() + "\"";
        return this.value.toString();
    }

    @Override
    public boolean getAsBoolean() {
        return Boolean.parseBoolean(this.toString());
    }

    @Override
    public Number getAsNumber() {
        return new Number() {
            @Override
            public int intValue() {
                return getAsInt();
            }

            @Override
            public long longValue() {
                return getAsLong();
            }

            @Override
            public float floatValue() {
                return getAsFloat();
            }

            @Override
            public double doubleValue() {
                return getAsDouble();
            }
        };
    }

    @Override
    public int getAsInt() {
        if (!this.isInt()) throw new IllegalStateException("Not a Integer:" + this);
        try {
            return Integer.parseInt(this.value.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public float getAsFloat() {
        if (!this.isFloat()) throw new IllegalStateException("Not a Json Float:" + this);
        try {
            return Float.parseFloat(this.value.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getAsDouble() {
        if (!this.isDouble()) throw new IllegalStateException("Not a Json Double:" + this);
        try {
            return Double.parseDouble(this.value.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getAsLong() {
        if (!this.isLong()) throw new IllegalStateException("Not a Json Long:" + this);
        try {
            return Long.parseLong(this.value.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInt() {
        try {
            this.getAsInt();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFloat() {
        try {
            this.getAsFloat();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDouble() {
        try {
            this.getAsDouble();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLong() {
        try {
            this.getAsLong();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JsonElement copy() {
        return null;
    }

    @Override
    public Set<String> keySet() {
        return Set.of();
    }
}
