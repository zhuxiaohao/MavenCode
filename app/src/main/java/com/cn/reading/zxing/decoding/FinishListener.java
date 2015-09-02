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

/**
 * Simple listener used to exit the app in a few cases.
 *
 */
public final class FinishListener
    implements android.content.DialogInterface.OnClickListener, android.content.DialogInterface.OnCancelListener, Runnable {

  private final android.app.Activity activityToFinish;

  public FinishListener(android.app.Activity activityToFinish) {
    this.activityToFinish = activityToFinish;
  }

  public void onCancel(android.content.DialogInterface dialogInterface) {
    run();
  }

  public void onClick(android.content.DialogInterface dialogInterface, int i) {
    run();
  }

  public void run() {
    activityToFinish.finish();
  }

}
