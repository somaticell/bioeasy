package com.baidu.location.a;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.baidu.location.f;

public class n implements SensorEventListener {
    private static n c;
    private float[] a;
    private SensorManager b;
    private float d;
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;

    private n() {
    }

    public static synchronized n a() {
        n nVar;
        synchronized (n.class) {
            if (c == null) {
                c = new n();
            }
            nVar = c;
        }
        return nVar;
    }

    public void a(boolean z) {
        this.e = z;
    }

    public synchronized void b() {
        Sensor defaultSensor;
        if (!this.g) {
            if (this.e) {
                if (this.b == null) {
                    this.b = (SensorManager) f.getServiceContext().getSystemService("sensor");
                }
                if (!(this.b == null || (defaultSensor = this.b.getDefaultSensor(11)) == null || !this.e)) {
                    this.b.registerListener(this, defaultSensor, 3);
                }
                this.g = true;
            }
        }
    }

    public void b(boolean z) {
        this.f = z;
    }

    public synchronized void c() {
        if (this.g) {
            if (this.b != null) {
                this.b.unregisterListener(this);
                this.b = null;
            }
            this.g = false;
        }
    }

    public boolean d() {
        return this.e;
    }

    public float e() {
        return this.d;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @SuppressLint({"NewApi"})
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case 11:
                this.a = (float[]) sensorEvent.values.clone();
                if (this.a != null) {
                    float[] fArr = new float[9];
                    try {
                        SensorManager.getRotationMatrixFromVector(fArr, this.a);
                        float[] fArr2 = new float[3];
                        SensorManager.getOrientation(fArr, fArr2);
                        this.d = (float) Math.toDegrees((double) fArr2[0]);
                        this.d = (float) Math.floor(this.d >= 0.0f ? (double) this.d : (double) (this.d + 360.0f));
                        return;
                    } catch (Exception e2) {
                        this.d = 0.0f;
                        return;
                    }
                } else {
                    return;
                }
            default:
                return;
        }
    }
}
