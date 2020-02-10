package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static org.cadabra.nbt.TagType.TAG_BYTE_ARRAY;

public class NBTLongArray extends NBTObject<List<Long>> {

    private List<Long> array = new ArrayList<>();

    private NBTLongArray() {
        super(TAG_BYTE_ARRAY.getID());
    }

    private NBTLongArray(String name) {
        super(TAG_BYTE_ARRAY.getID(), name);
    }

    public static NBTLongArray unnamed() {
        return new NBTLongArray();
    }

    public static NBTLongArray named(String name) {
        return new NBTLongArray(name);
    }

    @Override
    List<Long> getValue() {
        return array;
    }

    @Override
    List<Long> setValue(List<Long> e) {
        Objects.requireNonNull(e);
        List<Long> oldArr = array;
        array = e;
        return oldArr;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeInt(array.size());
        for (long val :
                array) {
            out.writeLong(val);
        }
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        int size = in.readInt();
        if (size == 0)
            return;
        ArrayList<Long> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            array.add(in.readLong());
        }
        this.array = array;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTLongArray.class.getSimpleName() + "[", "]")
                .add("array=" + array)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
