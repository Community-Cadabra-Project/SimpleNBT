package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public class NBTFloat extends NBTObject<Float> {

    private final static TagType TAG = TagType.TAG_FLOAT;

    private float val = 0;

    private NBTFloat(String name) {
        super(TAG.getID(), name);
    }

    private NBTFloat() {
        super(TAG.getID());
    }

    public static NBTFloat unnamed() {
        return new NBTFloat();
    }

    public static NBTFloat named(String name) {
        return new NBTFloat(name);
    }

    public float get() {
        return val;
    }

    public float set(float newVal) {
        float oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    public Float getValue() {
        return get();
    }

    @Override
    public Float setValue(Float e) {
        return set(e);
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        out.writeFloat(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readFloat();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTFloat.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
