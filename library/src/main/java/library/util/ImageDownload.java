package library.util;

/**
 * ClassName: ImageLoader <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月20日 下午5:57:31 <br/>
 * 图片异步加载工具
 * @author chenhao
 * @version 
 * @since JDK 1.6
 */
public class ImageDownload {
	private android.content.Context context;
	private boolean isLoop = true;
	// 图片软引用
	private java.util.HashMap<String, java.lang.ref.SoftReference<android.graphics.Bitmap>> caches;
	private java.util.ArrayList<library.util.ImageDownload.ImageLoadTask> taskQueue;

	private Thread thread = new Thread() {
		public void run() {
			while (isLoop) {
				while (taskQueue.size() > 0) {
					try {
						library.util.ImageDownload.ImageLoadTask task = taskQueue.remove(0);
						byte[] bytes = HttpUtils.getBytes(task.path, null,HttpUtils.METHOD_GET);
						task.bitmap = BitmapTools.getBitmap(bytes, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
						if (task.bitmap != null) {
							caches.put(task.path, new java.lang.ref.SoftReference<android.graphics.Bitmap>(
									task.bitmap));
							// File dir =
							// context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
							java.io.File dir = new java.io.File(task.folder);
							if (dir != null && !dir.exists()) {
								dir.mkdirs();
							}
							java.io.File file = new java.io.File(dir, task.name);
							BitmapTools.saveBitmap(file.getAbsolutePath(),
									task.bitmap);
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
			library.util.ImageDownload.ImageLoadTask task = (library.util.ImageDownload.ImageLoadTask) msg.obj;
			task.callback.imageLoaded(task.path, task.name, task.bitmap);

		};
	};

	private class ImageLoadTask {
		String path;
		String folder;
		String name;
		android.graphics.Bitmap bitmap;
		library.util.ImageDownload.Callback callback;
	}

	public interface Callback {
		void imageLoaded(String path, String names, android.graphics.Bitmap bitmap);
	}

	public void quit() {
		isLoop = false;
	}

	public void resume() {
		isLoop = true;
	}

	public ImageDownload(android.content.Context context) {
		this.context = context;
		caches = new java.util.HashMap<String, java.lang.ref.SoftReference<android.graphics.Bitmap>>();
		taskQueue = new java.util.ArrayList<library.util.ImageDownload.ImageLoadTask>();
		thread.start();
	}

	public android.graphics.Bitmap loadImage(String tag, String path, String folder,
			String names, library.util.ImageDownload.Callback callback) {
		android.graphics.Bitmap bitmap = null;

		if (caches.containsKey(path)) {
			bitmap = caches.get(path).get();

			if (bitmap == null)
				caches.remove(path);
			else
				return bitmap;
		}
		// File dir =
		// context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		// File file = new File(folder,path.substring(path.lastIndexOf("/")+1));
		java.io.File dir = new java.io.File(folder);
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
		java.io.File file = new java.io.File(folder, names);
		bitmap = BitmapTools.getBitmap(tag, file.getAbsolutePath());
		if (bitmap != null)
			return bitmap;
		library.util.ImageDownload.ImageLoadTask task = new library.util.ImageDownload.ImageLoadTask();
		task.path = path;
		task.name = names;
		task.folder = folder;
		task.callback = callback;
		taskQueue.add(task);
		synchronized (thread) {
			thread.notify();
		}
		return null;
	}
}
