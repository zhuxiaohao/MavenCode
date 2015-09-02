/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cn.reading.zxing.decoding;


final class DecodeHandler extends android.os.Handler {

  private static final String TAG = com.cn.reading.zxing.decoding.DecodeHandler.class.getSimpleName();

  private final com.cn.reading.zxing.activity.CaptureActivity activity;
  private final com.google.zxing.MultiFormatReader multiFormatReader;

  DecodeHandler(com.cn.reading.zxing.activity.CaptureActivity activity, java.util.Hashtable<com.google.zxing.DecodeHintType, Object> hints) {
    multiFormatReader = new com.google.zxing.MultiFormatReader();
    multiFormatReader.setHints(hints);
    this.activity = activity;
  }

  @Override
  public void handleMessage(android.os.Message message) {
    switch (message.what) {
      case com.cn.reading.R.id.decode:
        //Log.d(TAG, "Got decode message");
        decode((byte[]) message.obj, message.arg1, message.arg2);
        break;
      case com.cn.reading.R.id.quit:
        android.os.Looper.myLooper().quit();
        break;
    }
  }

  /**
   * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
   * reuse the same reader objects from one decode to the next.
   *
   * @param data   The YUV preview frame.
   * @param width  The width of the preview frame.
   * @param height The height of the preview frame.
   */
  private void decode(byte[] data, int width, int height) {
    long start = System.currentTimeMillis();
    com.google.zxing.Result rawResult = null;
    
    //modify here
    byte[] rotatedData = new byte[data.length];
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++)
            rotatedData[x * height + height - y - 1] = data[x + y * width];
    }
    int tmp = width; // Here we are swapping, that's the difference to #11
    width = height;
    height = tmp;
    
    com.cn.reading.zxing.camera.PlanarYUVLuminanceSource source = com.cn.reading.zxing.camera.CameraManager.get().buildLuminanceSource(rotatedData, width, height);
    com.google.zxing.BinaryBitmap bitmap = new com.google.zxing.BinaryBitmap(new com.google.zxing.common.HybridBinarizer(source));
    try {
      rawResult = multiFormatReader.decodeWithState(bitmap);
    } catch (com.google.zxing.ReaderException re) {
      // continue
    } finally {
      multiFormatReader.reset();
    }

    if (rawResult != null) {
      long end = System.currentTimeMillis();
      android.util.Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
      android.os.Message message = android.os.Message.obtain(activity.getHandler(), com.cn.reading.R.id.decode_succeeded, rawResult);
      android.os.Bundle bundle = new android.os.Bundle();
      bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
      message.setData(bundle);
      //Log.d(TAG, "Sending decode succeeded message...");
      message.sendToTarget();
    } else {
      android.os.Message message = android.os.Message.obtain(activity.getHandler(), com.cn.reading.R.id.decode_failed);
      message.sendToTarget();
    }
  }

}
