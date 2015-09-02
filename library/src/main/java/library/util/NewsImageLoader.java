package library.util;

/**
 * 
 * ClassName: NewsImageLoader <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午6:42:50 <br/>
 * 图片异步加载工具
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
@android.annotation.SuppressLint("NewApi") @android.annotation.TargetApi(android.os.Build.VERSION_CODES.FROYO)
public class NewsImageLoader {
	private android.content.Context context;
	private boolean isLoop = true;
	private String tag="";
	//图片软引用
	private java.util.HashMap<String, java.lang.ref.SoftReference<android.graphics.Bitmap>> caches;
	
	private java.util.ArrayList<library.util.NewsImageLoader.ImageLoadTask> taskQueue;

	private Thread thread = new Thread(){
		@android.annotation.SuppressLint("NewApi") public void run() {
			while(isLoop){
				while(taskQueue.size()>0){
					try {
						library.util.NewsImageLoader.ImageLoadTask task = taskQueue.remove(0);
						byte[] bytes = HttpUtils.getBytes(task.path, null, HttpUtils.METHOD_GET);
						if ("Header".equals(tag)||"ImagePager".equals(tag)) {
							task.bitmap = BitmapTools.getBitmap(bytes, 217, 163);
						}else{
							task.bitmap = BitmapTools.getBitmap(bytes, 64, 48);
						}
						if(task.bitmap!=null){
							caches.put(task.path, new java.lang.ref.SoftReference<android.graphics.Bitmap>(task.bitmap));
							java.io.File dir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
							if(dir!=null && !dir.exists()){
								dir.mkdirs();
							}
							java.io.File file = new java.io.File(dir,task.name);
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
	private android.os.Handler handler = new android.os.Handler(){
    	public void handleMessage(android.os.Message msg) {
    		library.util.NewsImageLoader.ImageLoadTask task = (library.util.NewsImageLoader.ImageLoadTask)msg.obj;
			task.callback.imageLoaded(task.path,task.name, task.bitmap);
    		
    	};
    };
	
	private class ImageLoadTask{
		String path;
		String name;
		android.graphics.Bitmap bitmap;
		library.util.NewsImageLoader.Callback callback;
	}
	
	public interface Callback{
		void imageLoaded(String path, String names, android.graphics.Bitmap bitmap);
	}
	
	public void quit(){
		isLoop = false;
	}

	public NewsImageLoader(android.content.Context context){
		this.context = context;
		caches = new java.util.HashMap<String, java.lang.ref.SoftReference<android.graphics.Bitmap>>();
		taskQueue = new java.util.ArrayList<library.util.NewsImageLoader.ImageLoadTask>();
		thread.start();
	}

	@android.annotation.SuppressLint("NewApi") public android.graphics.Bitmap loadImage(String tag, String path, String names, library.util.NewsImageLoader.Callback callback){
		android.graphics.Bitmap bitmap = null;
		this.tag=tag;
		if(caches.containsKey(path)){
			bitmap = caches.get(path).get();

			if(bitmap==null)
				caches.remove(path);
			else
				return bitmap;
		}
		java.io.File dir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
//		File file = new File(dir,path.substring(path.lastIndexOf("/")+1));
		java.io.File file = new java.io.File(dir,names);
		bitmap = BitmapTools.getBitmap(tag,file.getAbsolutePath());
		if(bitmap!=null)
			return bitmap;
		library.util.NewsImageLoader.ImageLoadTask task = new library.util.NewsImageLoader.ImageLoadTask();
		task.path = path;
		task.name=names;
		task.callback = callback;
		taskQueue.add(task);
		synchronized (thread) {
			thread.notify();
		}
		return null;
	}
}
