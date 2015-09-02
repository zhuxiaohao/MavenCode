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

/**
 * This class is used to activate the weak light on some camera phones (not flash)
 * in order to illuminate surfaces for scanning. There is no official way to do this,
 * but, classes which allow access to this function still exist on some devices.
 * This therefore proceeds through a great deal of reflection.
 *
 * See <a href="http://almondmendoza.com/2009/01/05/changing-the-screen-brightness-programatically/">
 * http://almondmendoza.com/2009/01/05/changing-the-screen-brightness-programatically/</a> and
 * <a href="http://code.google.com/p/droidled/source/browse/trunk/src/com/droidled/demo/DroidLED.java">
 * http://code.google.com/p/droidled/source/browse/trunk/src/com/droidled/demo/DroidLED.java</a>.
 * Thanks to Ryan Alford for pointing out the availability of this class.
 */
final class FlashlightManager {

  private static final String TAG = com.cn.reading.zxing.camera.FlashlightManager.class.getSimpleName();

  private static final Object iHardwareService;
  private static final java.lang.reflect.Method setFlashEnabledMethod;
  static {
    iHardwareService = getHardwareService();
    setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);
    if (iHardwareService == null) {
      android.util.Log.v(TAG, "This device does supports control of a flashlight");
    } else {
      android.util.Log.v(TAG, "This device does not support control of a flashlight");
    }
  }

  private FlashlightManager() {
  }

  /**
   * �����������ƿ���
   */
  //FIXME
  static void enableFlashlight() {
    setFlashlight(false);
  }

  static void disableFlashlight() {
    setFlashlight(false);
  }

  private static Object getHardwareService() {
    Class<?> serviceManagerClass = maybeForName("android.os.ServiceManager");
    if (serviceManagerClass == null) {
      return null;
    }

    java.lang.reflect.Method getServiceMethod = maybeGetMethod(serviceManagerClass, "getService", String.class);
    if (getServiceMethod == null) {
      return null;
    }

    Object hardwareService = invoke(getServiceMethod, null, "hardware");
    if (hardwareService == null) {
      return null;
    }

    Class<?> iHardwareServiceStubClass = maybeForName("android.os.IHardwareService$Stub");
    if (iHardwareServiceStubClass == null) {
      return null;
    }

    java.lang.reflect.Method asInterfaceMethod = maybeGetMethod(iHardwareServiceStubClass, "asInterface", android.os.IBinder.class);
    if (asInterfaceMethod == null) {
      return null;
    }

    return invoke(asInterfaceMethod, null, hardwareService);
  }

  private static java.lang.reflect.Method getSetFlashEnabledMethod(Object iHardwareService) {
    if (iHardwareService == null) {
      return null;
    }
    Class<?> proxyClass = iHardwareService.getClass();
    return maybeGetMethod(proxyClass, "setFlashlightEnabled", boolean.class);
  }

  private static Class<?> maybeForName(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException cnfe) {
      // OK
      return null;
    } catch (RuntimeException re) {
      android.util.Log.w(TAG, "Unexpected error while finding class " + name, re);
      return null;
    }
  }

  private static java.lang.reflect.Method maybeGetMethod(Class<?> clazz, String name, Class<?>... argClasses) {
    try {
      return clazz.getMethod(name, argClasses);
    } catch (NoSuchMethodException nsme) {
      // OK
      return null;
    } catch (RuntimeException re) {
      android.util.Log.w(TAG, "Unexpected error while finding method " + name, re);
      return null;
    }
  }

  private static Object invoke(java.lang.reflect.Method method, Object instance, Object... args) {
    try {
      return method.invoke(instance, args);
    } catch (IllegalAccessException e) {
      android.util.Log.w(TAG, "Unexpected error while invoking " + method, e);
      return null;
    } catch (java.lang.reflect.InvocationTargetException e) {
      android.util.Log.w(TAG, "Unexpected error while invoking " + method, e.getCause());
      return null;
    } catch (RuntimeException re) {
      android.util.Log.w(TAG, "Unexpected error while invoking " + method, re);
      return null;
    }
  }

  private static void setFlashlight(boolean active) {
    if (iHardwareService != null) {
      invoke(setFlashEnabledMethod, iHardwareService, active);
    }
  }

}
