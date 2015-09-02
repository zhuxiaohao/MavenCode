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
 * Finishes an activity after a period of inactivity.
 */
public final class InactivityTimer {

  private static final int INACTIVITY_DELAY_SECONDS = 5 * 60;

  private final java.util.concurrent.ScheduledExecutorService inactivityTimer =
      java.util.concurrent.Executors.newSingleThreadScheduledExecutor(new com.cn.reading.zxing.decoding.InactivityTimer.DaemonThreadFactory());
  private final android.app.Activity activity;
  private java.util.concurrent.ScheduledFuture<?> inactivityFuture = null;

  public InactivityTimer(android.app.Activity activity) {
    this.activity = activity;
    onActivity();
  }

  public void onActivity() {
    cancel();
    inactivityFuture = inactivityTimer.schedule(new FinishListener(activity),
                                                INACTIVITY_DELAY_SECONDS,
                                                java.util.concurrent.TimeUnit.SECONDS);
  }

  private void cancel() {
    if (inactivityFuture != null) {
      inactivityFuture.cancel(true);
      inactivityFuture = null;
    }
  }

  public void shutdown() {
    cancel();
    inactivityTimer.shutdown();
  }

  private static final class DaemonThreadFactory implements java.util.concurrent.ThreadFactory {
    public Thread newThread(Runnable runnable) {
      Thread thread = new Thread(runnable);
      thread.setDaemon(true);
      return thread;
    }
  }

}
