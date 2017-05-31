/*
 * Copyright (C) 2017 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.opendatakit.logging.WebLogger;

import java.io.*;

/**
 * Isolate bitmap resizing to its own utility class.
 *
 * @author mitchellsundt@gmail.com
 */
public class BitmapUtils {
  private final static String t = "BitmapUtils";

  public static Bitmap getBitmapScaledToDisplay(String appName, File f, int screenHeight, int screenWidth) {
    // Determine image size of f
    BitmapFactory.Options o = new BitmapFactory.Options();
    o.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(f.getAbsolutePath(), o);

    int heightScale = o.outHeight / screenHeight;
    int widthScale = o.outWidth / screenWidth;

    // Powers of 2 work faster, sometimes, according to the doc.
    // We're just doing closest size that still fills the screen.
    int scale = Math.max(widthScale, heightScale);

    // get bitmap with scale ( < 1 is the same as 1)
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inSampleSize = scale;
    Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
    if (b != null) {
      WebLogger.getLogger(appName).i(t,
          "Screen is " + screenHeight + "x" + screenWidth + ".  Image has been scaled down by "
              + scale + " to " + b.getHeight() + "x" + b.getWidth());
    }
    return b;
  }
}