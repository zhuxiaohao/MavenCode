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



/**
 * This class handles all the messaging which comprises the state machine for capture.
 */
public final class CaptureActivityHandler extends android.os.Handler {

    private static final String TAG = com.cn.reading.zxing.decoding.CaptureActivityHandler.class.getSimpleName();

    private final com.cn.reading.zxing.activity.CaptureActivity activity;
    private final DecodeThread decodeThread;
    private com.cn.reading.zxing.decoding.CaptureActivityHandler.State state;

    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureActivityHandler(com.cn.reading.zxing.activity.CaptureActivity activity, java.util.Vector<com.google.zxing.BarcodeFormat> decodeFormats,
                                  String characterSet) {
        this.activity = activity;
        decodeThread = new DecodeThread(activity, decodeFormats, characterSet,
                new com.cn.reading.zxing.view.ViewfinderResultPointCallback(activity.getViewfinderView()));
        decodeThread.start();
        state = com.cn.reading.zxing.decoding.CaptureActivityHandler.State.SUCCESS;
        // Start ourselves capturing previews and decoding.
        com.cn.reading.zxing.camera.CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(android.os.Message message) {
        switch (message.what) {
            case com.cn.reading.R.id.auto_focus:
                //Log.d(TAG, "Got auto-focus message");
                // When one auto focus pass finishes, start another. This is the closest thing to
                // continuous AF. It does seem to hunt a bit, but I'm not sure what else to do.
                if (state == com.cn.reading.zxing.decoding.CaptureActivityHandler.State.PREVIEW) {
                    com.cn.reading.zxing.camera.CameraManager.get().requestAutoFocus(this, com.cn.reading.R.id.auto_focus);
                }
                break;
            case com.cn.reading.R.id.restart_preview:
                android.util.Log.d(TAG, "Got restart preview message");
                restartPreviewAndDecode();
                break;
            case com.cn.reading.R.id.decode_succeeded:
                android.util.Log.d(TAG, "Got decode succeeded message");
                state = com.cn.reading.zxing.decoding.CaptureActivityHandler.State.SUCCESS;
                android.os.Bundle bundle = message.getData();

                /***********************************************************************/
                android.graphics.Bitmap barcode = bundle == null ? null :
                        (android.graphics.Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);//���ñ����߳�

                activity.handleDecode((com.google.zxing.Result) message.obj, barcode);//���ؽ��
                /***********************************************************************/
                break;
            case com.cn.reading.R.id.decode_failed:
                // We're decoding as fast as possible, so when one decode fails, start another.
                state = com.cn.reading.zxing.decoding.CaptureActivityHandler.State.PREVIEW;
                com.cn.reading.zxing.camera.CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), com.cn.reading.R.id.decode);
                break;
            case com.cn.reading.R.id.return_scan_result:
                android.util.Log.d(TAG, "Got return scan result message");
                activity.setResult(android.app.Activity.RESULT_OK, (android.content.Intent) message.obj);
                activity.finish();
                break;
            case com.cn.reading.R.id.launch_product_query:
                android.util.Log.d(TAG, "Got product query message");
                String url = (String) message.obj;
                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url));
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                activity.startActivity(intent);
                break;
        }
    }

    public void quitSynchronously() {
        state = com.cn.reading.zxing.decoding.CaptureActivityHandler.State.DONE;
        com.cn.reading.zxing.camera.CameraManager.get().stopPreview();
        android.os.Message quit = android.os.Message.obtain(decodeThread.getHandler(), com.cn.reading.R.id.quit);
        quit.sendToTarget();
        try {
            decodeThread.join();
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        removeMessages(com.cn.reading.R.id.decode_succeeded);
        removeMessages(com.cn.reading.R.id.decode_failed);
    }

    private void restartPreviewAndDecode() {
        if (state == com.cn.reading.zxing.decoding.CaptureActivityHandler.State.SUCCESS) {
            state = com.cn.reading.zxing.decoding.CaptureActivityHandler.State.PREVIEW;
            com.cn.reading.zxing.camera.CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), com.cn.reading.R.id.decode);
            com.cn.reading.zxing.camera.CameraManager.get().requestAutoFocus(this, com.cn.reading.R.id.auto_focus);
            activity.drawViewfinder();
        }
    }

}
