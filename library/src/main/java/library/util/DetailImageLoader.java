package library.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;

/**
 * ClassName: DetailImageLoader <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:01:26 <br/>
 * 图片异步加载工具
 * @author chenhao
 * @version
 * @since JDK 1.6
 */
public class DetailImageLoader {
    private android.content.Context context;
    private boolean isLoop = true;
    // 图片软引用
    // private HashMap<String, SoftReference<Bitmap>> caches;

    private java.util.ArrayList<library.util.DetailImageLoader.ImageLoadTask> taskQueue;

    private Thread thread = new Thread() {
        public void run() {
            while (isLoop) {
                while (taskQueue.size() > 0) {
                    try {
                        library.util.DetailImageLoader.ImageLoadTask task = taskQueue.remove(0);
                        byte[] bytes = HttpUtils.getBytes(task.src, null, HttpUtils.METHOD_GET);
                        task.bitmap = BitmapTools.getBitmap(bytes, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                        if (task.bitmap != null) {
                            // caches.put(task.src, new
                            // SoftReference<Bitmap>(task.bitmap));
                            java.io.File dir = new java.io.File(task.folder);
                            if (dir != null && !dir.exists()) {
                                dir.mkdirs();
                            }
                            java.io.File file = new java.io.File(task.path);
                            BitmapTools.saveBitmap(file.getAbsolutePath(), task.bitmap);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.obj = task;
                            handler.sendMessage(msg);
                        }
                    } catch (org.apache.http.client.ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
    };
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            library.util.DetailImageLoader.ImageLoadTask task = (library.util.DetailImageLoader.ImageLoadTask) msg.obj;
            task.callback.imageLoaded(task.id, task.path, true);

        };
    };

    private class ImageLoadTask {
        String id;
        String src;
        String folder;
        String path;
        android.graphics.Bitmap bitmap;
        library.util.DetailImageLoader.Callback callback;
    }

    public interface Callback {
        void imageLoaded(String src, String path, boolean flag);
    }

    public void quit() {
        isLoop = false;
    }

    public DetailImageLoader(android.content.Context context) {
        this.context = context;
        // caches = new HashMap<String, SoftReference<Bitmap>>();
        taskQueue = new java.util.ArrayList<library.util.DetailImageLoader.ImageLoadTask>();
    }

    /**
     * <pre>
     * 开启线程
     * </pre>
     * 
     * threa.start()
     */
    public void start() {
        if (thread != null) {
            thread.start();
        }
    }

    /**
     * <pre>
     * 加载图片
     * </pre>
     * 
     * @param tag
     *            标签，任意字符串
     * @param src
     *            网络地址
     * @param folder
     *            本地目录
     * @param path
     *            文件据对路径
     * @param callback
     *            回调
     * @return
     */
    public boolean loadImage(String tag, String id, String src, String folder, String path, library.util.DetailImageLoader.Callback callback) {
        // Bitmap bitmap = null;
        //
        // if(caches.containsKey(src)){
        // bitmap = caches.get(src).get();
        //
        // if(bitmap==null)
        // caches.remove(src);
        // else
        // return true;
        // }
        // File dir = new File(folder);
        // if(dir!=null && !dir.exists()){
        // dir.mkdirs();
        // }
        java.io.File file = new java.io.File(path);
        // bitmap = BitmapTools.getBitmap(tag, file.getAbsolutePath());
        // if(bitmap!=null)
        if (null != file && file.exists())
            return true;
        library.util.DetailImageLoader.ImageLoadTask task = new library.util.DetailImageLoader.ImageLoadTask();
        task.id = id;
        task.src = src;
        task.path = path;
        task.folder = folder;
        task.callback = callback;
        taskQueue.add(task);
        synchronized (thread) {
            thread.notify();
        }
        return false;
    }
}
