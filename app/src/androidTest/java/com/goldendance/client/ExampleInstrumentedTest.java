package com.goldendance.client;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.goldendance.client.http.GDImageLoader;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.goldendance.client", appContext.getPackageName());
    }

    @Test
    public void testImageLoader() {
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        GDImageLoader.display(appContext, "http://t10.baidu.com/it/u=845607762,4018225966&fm=76");
//        System.out.print("testImageloader");
    }
}
