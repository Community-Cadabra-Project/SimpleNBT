package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.cadabra.nbt.TagType.*;

public class NBTFactory {

    private static Map<Integer, Function<String, NBTObject>> factory;

    static {
        factory = new HashMap<>();
        register(TAG_END, null);
        register(TAG_BYTE, NBTByte::new);
        register(TAG_SHORT, NBTShort::new);
        register(TAG_INT, NBTInteger::new);
        register(TAG_LONG, NBTLong::new);
        register(TAG_FLOAT, NBTFloat::new);
        register(TAG_DOUBLE, NBTDouble::new);
        register(TAG_BYTE_ARRAY, NBTByteArray::new);
        register(TAG_STRING, NBTString::new);
        register(TAG_LIST, NBTList::new);
        register(TAG_COMPOUND, NBTCompound::new);
        register(TAG_INT_ARRAY, NBTIntArray::new);
        register(TAG_LONG_ARRAY, NBTLongArray::new);
    }

    private static void register(TagType tag, Function<String, NBTObject> func) {
        factory.put(tag.getID(), func);
    }

    public static void register(int tagID, Function<String, NBTObject> func) {
        checkTagID(tagID);
        factory.put(tagID, func);
    }

    public static NBTByte createByte(String s) {
        return (NBTByte) factory.get(TAG_BYTE.getID()).apply(s);
    }

    public static NBTShort createShort(String s) {
        return (NBTShort) factory.get(TAG_FLOAT.getID()).apply(s);
    }

    public static NBTInteger createInt(String s) {
        return (NBTInteger) factory.get(TAG_INT.getID()).apply(s);
    }

    public static NBTLong createLong(String s) {
        return (NBTLong) factory.get(TAG_LONG.getID()).apply(s);
    }

    public static NBTFloat createFloat(String s) {
        return (NBTFloat) factory.get(TAG_FLOAT.getID()).apply(s);
    }

    public static NBTDouble createDouble(String s) {
        return (NBTDouble) factory.get(TAG_DOUBLE.getID()).apply(s);
    }

    public static NBTByteArray createByteArray(String s) {
        return (NBTByteArray) factory.get(TAG_BYTE_ARRAY.getID()).apply(s);
    }

    public static NBTIntArray createIntArray(String s) {
        return (NBTIntArray) factory.get(TAG_INT_ARRAY.getID()).apply(s);
    }

    public static NBTLongArray createLongArray(String s) {
        return (NBTLongArray) factory.get(TAG_LONG_ARRAY.getID()).apply(s);
    }

    public static NBTList createList(String s, int subTag) {
        NBTList list = (NBTList) factory.get(TAG_LIST.getID()).apply(s);
        list.setSubTag(subTag);
        return list;
    }

    public static NBTCompound createCompound(String s) {
        return (NBTCompound) factory.get(TAG_COMPOUND.getID()).apply(s);
    }

    static void checkRange(int tagID) {
        if (tagID != (tagID & 0xFF))
            throw new IllegalArgumentException("TagID must be in range [0, 255] - " + tagID);
    }

    static boolean containsTagID(int tagID) {
        return factory.containsKey(tagID);
    }

    private static void checkTagID(int tagID) {
        checkRange(tagID);
        if (containsTagID(tagID))
            throw new IllegalArgumentException("TagID: %d already registered");
    }

    public static NBTObject unnamed(int tagID) {
        return factory.get(tagID).apply(null);
    }

    public static NBTObject named(int tagID, String name) {
        Objects.requireNonNull(name);
        return factory.get(tagID).apply(name);
    }

    public static NBTObject read(NBTInputStream in) throws IOException {
        Map.Entry<Byte, String> e = in.readHeadTag();
        NBTObject o = named(e.getKey(), e.getValue());
        o.readBody(in);
        in.close();
        return o;
    }

    public static NBTObject read(InputStream in) throws IOException {
        return read(new NBTInputStream(in));
    }

    public static NBTObject read(File file) throws IOException {
        return read(new NBTInputStream(new FileInputStream(file)));
    }

    public static NBTObject read(String name) throws IOException {
        File file = new File(name);
        return read(file);
    }
}
