package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import static org.cadabra.nbt.TagType.*;

public final class NBTList extends NBTObject implements Iterable<NBTObject> {

    private List<NBTObject> list = new ArrayList<>();
    private int subTag = 0;

    NBTList(String name) {
        super(TAG_LIST.getID(), name);
    }

    void setSubTag(int subTag) {
        this.subTag = subTag;
    }

    public void add(NBTObject e) throws NBTException {
        Objects.requireNonNull(e);
        checkFormat(e);
        list.add(e);
    }

    public NBTObject set(int index, NBTObject e) throws NBTException {
        Objects.requireNonNull(e);
        checkFormat(e);
        return list.set(index, e);
    }

    public NBTObject remove(int index) {
        return list.remove(index);
    }

    public int size() {
        return list.size();
    }

    private void checkFormat(NBTObject e) throws NBTException {
        if (e.isUnnamed())
            throw new NBTException(namedObject(e));
        if (e.getTag() != subTag)
            throw new NBTException(differentTags(subTag, e));
    }

    private static String namedObject(NBTObject e) {
        return "Objects in List must haven't name: " + e;
    }

    private static String differentTags(int tag, NBTObject e) {
        return e.getClass().getSimpleName() + " does not match tag " + tag;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeByte(subTag);
        out.writeInt(list.size());
        for (NBTObject e :
                list) {
            assert e.isUnnamed();
            e.write(out);
        }
    }

    @Override
    public void readBody(NBTInputStream in) throws IOException {
        int subID = in.read();
        int size = in.readInt();
        ArrayList<NBTObject> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            NBTObject e = NBTFactory.unnamed(subID);
            e.readBody(in);
            list.add(e);
        }
        this.list = list;
    }

    @Override
    public Iterator<NBTObject> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTList.class.getSimpleName() + "[", "]")
                .add("list=" + list)
                .add("subTagID=" + subTag)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
