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

final class DecodeFormatManager {

  private static final java.util.regex.Pattern COMMA_PATTERN = java.util.regex.Pattern.compile(",");

  static final java.util.Vector<com.google.zxing.BarcodeFormat> PRODUCT_FORMATS;
  static final java.util.Vector<com.google.zxing.BarcodeFormat> ONE_D_FORMATS;
  static final java.util.Vector<com.google.zxing.BarcodeFormat> QR_CODE_FORMATS;
  static final java.util.Vector<com.google.zxing.BarcodeFormat> DATA_MATRIX_FORMATS;
  static {
    PRODUCT_FORMATS = new java.util.Vector<com.google.zxing.BarcodeFormat>(5);
    PRODUCT_FORMATS.add(com.google.zxing.BarcodeFormat.UPC_A);
    PRODUCT_FORMATS.add(com.google.zxing.BarcodeFormat.UPC_E);
    PRODUCT_FORMATS.add(com.google.zxing.BarcodeFormat.EAN_13);
    PRODUCT_FORMATS.add(com.google.zxing.BarcodeFormat.EAN_8);
    PRODUCT_FORMATS.add(com.google.zxing.BarcodeFormat.RSS14);
    ONE_D_FORMATS = new java.util.Vector<com.google.zxing.BarcodeFormat>(PRODUCT_FORMATS.size() + 4);
    ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
    ONE_D_FORMATS.add(com.google.zxing.BarcodeFormat.CODE_39);
    ONE_D_FORMATS.add(com.google.zxing.BarcodeFormat.CODE_93);
    ONE_D_FORMATS.add(com.google.zxing.BarcodeFormat.CODE_128);
    ONE_D_FORMATS.add(com.google.zxing.BarcodeFormat.ITF);
    QR_CODE_FORMATS = new java.util.Vector<com.google.zxing.BarcodeFormat>(1);
    QR_CODE_FORMATS.add(com.google.zxing.BarcodeFormat.QR_CODE);
    DATA_MATRIX_FORMATS = new java.util.Vector<com.google.zxing.BarcodeFormat>(1);
    DATA_MATRIX_FORMATS.add(com.google.zxing.BarcodeFormat.DATA_MATRIX);
  }

  private DecodeFormatManager() {}

  static java.util.Vector<com.google.zxing.BarcodeFormat> parseDecodeFormats(android.content.Intent intent) {
    java.util.List<String> scanFormats = null;
    String scanFormatsString = intent.getStringExtra(Intents.Scan.SCAN_FORMATS);
    if (scanFormatsString != null) {
      scanFormats = java.util.Arrays.asList(COMMA_PATTERN.split(scanFormatsString));
    }
    return parseDecodeFormats(scanFormats, intent.getStringExtra(Intents.Scan.MODE));
  }

  static java.util.Vector<com.google.zxing.BarcodeFormat> parseDecodeFormats(android.net.Uri inputUri) {
    java.util.List<String> formats = inputUri.getQueryParameters(Intents.Scan.SCAN_FORMATS);
    if (formats != null && formats.size() == 1 && formats.get(0) != null){
      formats = java.util.Arrays.asList(COMMA_PATTERN.split(formats.get(0)));
    }
    return parseDecodeFormats(formats, inputUri.getQueryParameter(Intents.Scan.MODE));
  }

  private static java.util.Vector<com.google.zxing.BarcodeFormat> parseDecodeFormats(Iterable<String> scanFormats,
                                                          String decodeMode) {
    if (scanFormats != null) {
      java.util.Vector<com.google.zxing.BarcodeFormat> formats = new java.util.Vector<com.google.zxing.BarcodeFormat>();
      try {
        for (String format : scanFormats) {
          formats.add(com.google.zxing.BarcodeFormat.valueOf(format));
        }
        return formats;
      } catch (IllegalArgumentException iae) {
        // ignore it then
      }
    }
    if (decodeMode != null) {
      if (Intents.Scan.PRODUCT_MODE.equals(decodeMode)) {
        return PRODUCT_FORMATS;
      }
      if (Intents.Scan.QR_CODE_MODE.equals(decodeMode)) {
        return QR_CODE_FORMATS;
      }
      if (Intents.Scan.DATA_MATRIX_MODE.equals(decodeMode)) {
        return DATA_MATRIX_FORMATS;
      }
      if (Intents.Scan.ONE_D_MODE.equals(decodeMode)) {
        return ONE_D_FORMATS;
      }
    }
    return null;
  }

}
