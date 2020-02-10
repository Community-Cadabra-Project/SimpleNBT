package org.cadabra.nbt.io;

import org.cadabra.nbt.TagType;

import java.io.*;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class NBTInputStream extends DataInputStream {

    public NBTInputStream(InputStream in) {
        super(wrapIfCanGZIPIn(in));
    }

    public static InputStream wrapIfCanGZIPIn(InputStream in) {
        try {
            if (in instanceof GZIPInputStream)
                return in;
            return new GZIPInputStream(in);
        } catch (Exception e) {
            return in;
        }
    }

    public Map.Entry<Byte, String> readHeadTag() throws IOException {
        int tagID = read();
        String name = tagID == TagType.TAG_END.getID()
                ? ""
                : readUTF();
        return Map.entry((byte) tagID, name);
    }
}
