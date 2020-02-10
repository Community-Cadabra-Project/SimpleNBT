package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public class NBTDouble extends NBTObject<Double> {

    private final static TagType TAG = TagType.TAG_DOUBLE;

    private double val = 0;

    private NBTDouble(String name) {
        super(TAG.getID(), name);
    }

    private NBTDouble() {
        super(TAG.getID());
    }

    public static NBTDouble unnamed() {
        return new NBTDouble();
    }

    public static NBTDouble named(String name) {
        return new NBTDouble(name);
    }

    public double get() {
        return val;
    }

    public double set(double newVal) {
        double oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    public Double getValue() {
        return get();
    }

    @Override
    public Double setValue(Double e) {
        return set(e);
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        out.writeDouble(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readDouble();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTDouble.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
