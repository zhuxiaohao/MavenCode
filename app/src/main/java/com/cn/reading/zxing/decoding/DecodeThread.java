/*
 * Copyright (C) 2008 ZXing authors
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


final class DecodeThread extends Thread {

  public static final String BARCODE_BITMAP = "barcode_bitmap";
  private final com.cn.reading.zxing.activity.CaptureActivity activity;
  private final java.util.Hashtable<com.google.zxing.DecodeHintType, Object> hints;
  private android.os.Handler handler;
  private final java.util.concurrent.CountDownLatch handlerInitLatch;

  DecodeThread(com.cn.reading.zxing.activity.CaptureActivity activity,
               java.util.Vector<com.google.zxing.BarcodeFormat> decodeFormats,
               String characterSet,
               com.google.zxing.ResultPointCallback resultPointCallback) {

    this.activity = activity;
    handlerInitLatch = new java.util.concurrent.CountDownLatch(1);

    hints = new java.util.Hashtable<com.google.zxing.DecodeHintType, Object>(3);

    if (decodeFormats == null || decodeFormats.isEmpty()) {
    	 decodeFormats = new java.util.Vector<com.google.zxing.BarcodeFormat>();
    	 decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
    	 decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
    	 decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
    }
    
    hints.put(com.google.zxing.DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

    if (characterSet != null) {
      hints.put(com.google.zxing.DecodeHintType.CHARACTER_SET, characterSet);
    }

    hints.put(com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
  }

  android.os.Handler getHandler() {
    try {
      handlerInitLatch.await();
    } catch (InterruptedException ie) {
      // continue?
    }
    return handler;
  }

  @Override
  public void run() {
    android.os.Looper.prepare();
    handler = new DecodeHandler(activity, hints);
    handlerInitLatch.countDown();
    android.os.Looper.loop();
  }

}
