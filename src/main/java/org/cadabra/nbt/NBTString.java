package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.Objects;
import java.util.StringJoiner;

public class NBTString extends NBTObject<String> {

    private final static TagType TAG = TagType.TAG_STRING;

    private String val = "";

    private NBTString(String name) {
        super(TAG.getID(), name);
    }

    private NBTString() {
        super(TAG.getID());
    }

    public static NBTString unnamed() {
        return new NBTString();
    }

    public static NBTString named(String name) {
        return new NBTString(name);
    }

    @Override
    public String getValue() {
        return val;
    }

    @Override
    public String setValue(String newVal) {
        Objects.requireNonNull(newVal);
        String oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {

        out.writeUTF(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readUTF();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTString.class.getSimpleName() + "[", "]")
                .add("val='" + val + "'")
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
