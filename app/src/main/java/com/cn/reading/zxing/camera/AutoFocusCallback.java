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

package com.cn.reading.zxing.camera;

final class AutoFocusCallback implements android.hardware.Camera.AutoFocusCallback {

  private static final String TAG = com.cn.reading.zxing.camera.AutoFocusCallback.class.getSimpleName();

  private static final long AUTOFOCUS_INTERVAL_MS = 1500L;

  private android.os.Handler autoFocusHandler;
  private int autoFocusMessage;

  void setHandler(android.os.Handler autoFocusHandler, int autoFocusMessage) {
    this.autoFocusHandler = autoFocusHandler;
    this.autoFocusMessage = autoFocusMessage;
  }

  public void onAutoFocus(boolean success, android.hardware.Camera camera) {
    if (autoFocusHandler != null) {
      android.os.Message message = autoFocusHandler.obtainMessage(autoFocusMessage, success);
      autoFocusHandler.sendMessageDelayed(message, AUTOFOCUS_INTERVAL_MS);
      autoFocusHandler = null;
    } else {
      android.util.Log.d(TAG, "Got auto-focus callback, but no handler for it");
    }
  }

}
