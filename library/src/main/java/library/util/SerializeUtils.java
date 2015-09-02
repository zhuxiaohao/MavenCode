package library.util;

/**
 * ClassName: SerializeUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:03:04 <br/>
 * 序列化工具类，可用于序列化对象到文件或从文件反序列化对象，
 * 如： deserialization(String filePath) 从文件反序列化对象<br/>
 * serialization(String filePath, Object obj) 序列化对象到文件<br/>
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class SerializeUtils {

    private SerializeUtils() {
        throw new AssertionError();
    }

    /**
     * 反序列化的文件
     * 
     * @param filePath
     * @return
     * @throws RuntimeException
     *             if an error occurs
     */
    public static Object deserialization(String filePath) {
        java.io.ObjectInputStream in = null;
        try {
            in = new java.io.ObjectInputStream(new java.io.FileInputStream(filePath));
            Object o = in.readObject();
            in.close();
            return o;
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ClassNotFoundException occurred. ", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (java.io.IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * 序列化到文件
     * 
     * @param filePath
     * @param obj
     * @return
     * @throws RuntimeException
     *             if an error occurs
     */
    public static void serialization(String filePath, Object obj) {
        java.io.ObjectOutputStream out = null;
        try {
            out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(filePath));
            out.writeObject(obj);
            out.close();
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (java.io.IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }
}
