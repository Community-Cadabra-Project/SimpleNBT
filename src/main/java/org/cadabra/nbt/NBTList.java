package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import static org.cadabra.nbt.NBTFactory.*;
import static org.cadabra.nbt.TagType.*;

public class NBTList extends NBTObject<List<NBT>> implements Iterable<NBT> {

    private List<NBT> list = new ArrayList<>();
    private int subTagID = 0;

    private NBTList() {
        super(TAG_LIST.getID());
    }

    private NBTList(String name) {
        super(TAG_LIST.getID(), name);
    }

    static NBTList unnamed() {
        return new NBTList();
    }

    static NBTList named(String name) {
        return new NBTList(name);
    }

    public NBTList unnamed(int subTagID) throws NBTException {
        NBTList e = unnamed();
        e.setSubID(subTagID);
        return e;
    }

    public NBTList named(String name, int subTagID) throws NBTException {
        NBTList e = named(name);
        e.setSubID(subTagID);
        return e;
    }

    public void add(NBT e) throws NBTException {
        Objects.requireNonNull(e);
        checkNBT(e);
        list.add(e);
    }

    public NBT set(int index, NBT e) throws NBTException {
        Objects.requireNonNull(e);
        checkNBT(e);
        return list.set(index, e);
    }

    public NBT remove(int index) {
        return list.remove(index);
    }

    public int size() {
        return list.size();
    }

    private void checkNBT(NBT e) throws NBTException {
        if (e.isUnnamed())
            throw new NBTException(namedObject(e));
        if (e.tagID() != subTagID)
            throw new NBTException(differentTags(subTagID, e));
    }

    @Override
    List<NBT> getValue() {
        return list;
    }

    @Override
    List<NBT> setValue(List<NBT> e) {
        Objects.requireNonNull(e);
        List<NBT> oldList = list;
        list = e;
        return oldList;
    }

    private void setSubID(int tagID) throws NBTException {
        assert subTagID == 0;
        checkTagID(tagID);
        subTagID = tagID;
    }

    private static void checkTagID(int tagID) throws NBTException {
        checkRange(tagID);
        if (!containsTagID(tagID)) {
            throw new NBTException(String.format("TagID: %d not registered", tagID));
        }
    }

    private static String namedObject(NBT e) {
        return "Objects in List must haven't name: " + e;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeByte(subTagID);
        out.writeInt(list.size());
        for (NBT e :
                list) {
            if (e.isUnnamed())
                e.write(out);
            else
                throw new IOException(namedObject(e));
        }
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        int subID = in.read();
        int size = in.readInt();
        ArrayList<NBT> list = new ArrayList<>(size);
        Supplier<NBT> supp = NBTFactory.getSupp(subID);
        for (int i = 0; i < size; i++) {
            NBT e = supp.get();
            e.read(in);
            list.add(e);
        }
        this.list = list;
    }

    @Override
    public Iterator<NBT> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTList.class.getSimpleName() + "[", "]")
                .add("list=" + list)
                .add("subTagID=" + subTagID)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
