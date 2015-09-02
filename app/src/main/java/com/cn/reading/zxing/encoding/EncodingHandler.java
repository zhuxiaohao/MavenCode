package com.cn.reading.zxing.encoding;

/**
 * @author Ryan Tang
 *
 */
public final class EncodingHandler {
	private static final int BLACK = 0xff000000;
	
	public static android.graphics.Bitmap createQRCode(String str,int widthAndHeight) throws com.google.zxing.WriterException {
		java.util.Hashtable<com.google.zxing.EncodeHintType, String> hints = new java.util.Hashtable<com.google.zxing.EncodeHintType, String>();
        hints.put(com.google.zxing.EncodeHintType.CHARACTER_SET, "utf-8");
		com.google.zxing.common.BitMatrix matrix = new com.google.zxing.MultiFormatWriter().encode(str,
				com.google.zxing.BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int[] pixels = new int[width * height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = BLACK;
				}
			}
		}
		android.graphics.Bitmap bitmap = android.graphics.Bitmap.createBitmap(width, height,
				android.graphics.Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
}
