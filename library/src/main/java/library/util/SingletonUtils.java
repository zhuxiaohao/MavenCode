package library.util;

/**
 * 
 * ClassName: SingletonUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:47:18 <br/>
 * 个人单身助手类的初始化
 * @author chenhao
 * @version @param <T>
 * @since JDK 1.6
 */
public abstract class SingletonUtils<T> {

    private T instance;

    protected abstract T newInstance();

    public final T getInstance() {
        if (instance == null) {
            synchronized (library.util.SingletonUtils.class) {
                if (instance == null) {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
