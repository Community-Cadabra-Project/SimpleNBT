package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.cadabra.nbt.TagType.*;

public class NBTFactory {

    private static Map<Integer, Function<String, NBT>> factoryNBT;
    private static Map<Integer, Supplier<NBT>> factoryEmpty;

    static {
        factoryEmpty = new HashMap<>();
        factoryNBT = new HashMap<>();
        register(TAG_END, null, null);
        register(TAG_BYTE, NBTByte::named, NBTByte::unnamed);
        register(TAG_SHORT, NBTShort::named, NBTShort::unnamed);
        register(TAG_INT, NBTInteger::named, NBTInteger::unnamed);
        register(TAG_LONG, NBTLong::named, NBTLong::unnamed);
        register(TAG_FLOAT, NBTFloat::named, NBTFloat::unnamed);
        register(TAG_DOUBLE, NBTDouble::named, NBTDouble::unnamed);
        register(TAG_BYTE_ARRAY, NBTByteArray::named, NBTByteArray::unnamed);
        register(TAG_STRING, NBTString::named, NBTString::unnamed);
        register(TAG_LIST, NBTList::named, NBTList::unnamed);
        register(TAG_COMPOUND, NBTCompound::named, NBTCompound::unnamed);
        register(TAG_INT_ARRAY, NBTIntArray::named, NBTIntArray::unnamed);
        register(TAG_LONG_ARRAY, NBTLongArray::named, NBTLongArray::unnamed);
    }

    private static void register(TagType tag, Function<String, NBT> func, Supplier<NBT> supp) {
        factoryNBT.put(tag.getID(), func);
        factoryEmpty.put(tag.getID(), supp);
    }

    public static void register(int tagID, Function<String, NBT> func, Supplier<NBT> supp) {
        checkTagID(tagID);
        factoryNBT.put(tagID, func);
        factoryEmpty.put(tagID, supp);
    }

    static void checkRange(int tagID) {
        if (tagID != (tagID & 0xFF))
            throw new IllegalArgumentException("TagID must be in range [0, 255] - " + tagID);
    }

    static boolean containsTagID(int tagID) {
        return factoryNBT.containsKey(tagID) || factoryEmpty.containsKey(tagID);
    }

    private static void checkTagID(int tagID) {
        checkRange(tagID);
        if (containsTagID(tagID))
            throw new IllegalArgumentException("TagID: %d already registered");
    }

    public static Supplier<NBT> getSupp(int tagID) {
        return factoryEmpty.get(tagID);
    }

    public static Function<String, NBT> getFunc(int tagID) {
        return factoryNBT.get(tagID);
    }

    public static NBT unnamed(int tagID) {
        return getSupp(tagID).get();
    }

    public static NBT named(int tagID, String name) {
        return getFunc(tagID).apply(name);
    }

    public static NBT read(NBTInputStream in) throws IOException {
        Map.Entry<Byte, String> e = in.readHeadTag();
        NBT o = named(e.getKey(), e.getValue());
        o.read(in);
        in.close();
        return o;
    }

    public static NBT read(InputStream in) throws IOException {
        return read(new NBTInputStream(in));
    }

    public static NBT read(File file) throws IOException {
        return read(new NBTInputStream(new FileInputStream(file)));
    }

    public static NBT read(String name) throws IOException {
        File file = new File(name);
        return read(file);
    }
}
