package library.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * ClassName: ParcelUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:00:41 <br/>
 * Android Parcel工具类，
 * 可用于从parcel读取或写入特殊类型数据，如：
 * readBoolean(Parcel in) 从pacel中读取boolean类型数据 
 * readHashMap(Parcel in, ClassLoader loader) 从pacel中读取map类型数据 
 * writeBoolean(boolean b, Parcel out) 向parcel中写入boolean类型数据
 * writeHashMap(Map<K, V> map, Parcel out, int flags) 向parcel中写入map类型数据
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class ParcelUtils {

    private ParcelUtils() {
        throw new AssertionError();
    }

    /**
     * read boolean
     * 
     * @param in
     * @return
     */
    public static boolean readBoolean(android.os.Parcel in) {
        return in.readInt() == 1;
    }

    /**
     * write boolean
     * 
     * @param b
     * @param out
     */
    public static void writeBoolean(boolean b, android.os.Parcel out) {
        out.writeInt(b ? 1 : 0);
    }

    /**
     * Read a HashMap from a Parcel, class of key and value are both String
     * 
     * @param in
     * @return
     */
    public static java.util.Map<String, String> readHashMapStringAndString(android.os.Parcel in) {
        if (in == null) {
            return null;
        }

        int size = in.readInt();
        if (size == -1) {
            return null;
        }

        java.util.Map<String, String> map = new java.util.HashMap<String, String>();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            map.put(key, in.readString());
        }
        return map;
    }

    /**
     * Write a HashMap to a Parcel, class of key and value are both String
     * 
     * @param map
     * @param out
     * @param flags
     */
    public static void writeHashMapStringAndString(java.util.Map<String, String> map, android.os.Parcel out, int flags) {
        if (map != null) {
            out.writeInt(map.size());
            for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
                out.writeString(entry.getKey());
                out.writeString(entry.getValue());
            }
        } else {
            out.writeInt(-1);
        }
    }

    /**
     * Read a HashMap from a Parcel, class of key is String, class of Value can
     * parcelable
     * 
     * @param <V>
     * @param in
     * @param loader
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <V extends android.os.Parcelable> java.util.Map<String, V> readHashMapStringKey(android.os.Parcel in, ClassLoader loader) {
        if (in == null) {
            return null;
        }

        int size = in.readInt();
        if (size == -1) {
            return null;
        }

        java.util.Map<String, V> map = new java.util.HashMap<String, V>();
        for (int i = 0; i < size; i++) {
            String key = in.readString();
            map.put(key, (V) in.readParcelable(loader));
        }
        return map;
    }

    /**
     * Write a HashMap to a Parcel, class of key is String, class of Value can
     * parcelable
     * 
     * @param map
     * @param out
     * @param flags
     */
    public static <V extends android.os.Parcelable> void writeHashMapStringKey(java.util.Map<String, V> map, android.os.Parcel out, int flags) {
        if (map != null) {
            out.writeInt(map.size());

            for (java.util.Map.Entry<String, V> entry : map.entrySet()) {
                out.writeString(entry.getKey());
                out.writeParcelable(entry.getValue(), flags);
            }
        } else {
            out.writeInt(-1);
        }
    }

    /**
     * Read a HashMap from a Parcel, class of key and value can parcelable both
     * 
     * @param <V>
     * @param in
     * @param loader
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K extends android.os.Parcelable, V extends android.os.Parcelable> java.util.Map<K, V> readHashMap(android.os.Parcel in, ClassLoader loader) {
        if (in == null) {
            return null;
        }

        int size = in.readInt();
        if (size == -1) {
            return null;
        }

        java.util.Map<K, V> map = new java.util.HashMap<K, V>();
        for (int i = 0; i < size; i++) {
            map.put((K) in.readParcelable(loader), (V) in.readParcelable(loader));
        }
        return map;
    }

    /**
     * Write a HashMap to a Parcel, class of key and value can parcelable both
     * 
     * @param map
     * @param out
     * @param flags
     */
    public static <K extends android.os.Parcelable, V extends android.os.Parcelable> void writeHashMap(java.util.Map<K, V> map, android.os.Parcel out, int flags) {
        if (map != null) {
            out.writeInt(map.size());

            for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
                out.writeParcelable(entry.getKey(), flags);
                out.writeParcelable(entry.getValue(), flags);
            }
        } else {
            out.writeInt(-1);
        }
    }
}
