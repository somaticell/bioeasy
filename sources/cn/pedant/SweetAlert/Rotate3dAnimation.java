package cn.pedant.SweetAlert;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {
    public static final int ROLL_BY_X = 0;
    public static final int ROLL_BY_Y = 1;
    public static final int ROLL_BY_Z = 2;
    private Camera mCamera;
    private float mFromDegrees;
    private float mPivotX;
    private int mPivotXType = 0;
    private float mPivotXValue = 0.0f;
    private float mPivotY;
    private int mPivotYType = 0;
    private float mPivotYValue = 0.0f;
    private int mRollType;
    private float mToDegrees;

    protected static class Description {
        public int type;
        public float value;

        protected Description() {
        }
    }

    /* access modifiers changed from: package-private */
    public Description parseValue(TypedValue value) {
        int i = 1;
        Description d = new Description();
        if (value == null) {
            d.type = 0;
            d.value = 0.0f;
        } else {
            if (value.type == 6) {
                if ((value.data & 15) == 1) {
                    i = 2;
                }
                d.type = i;
                d.value = TypedValue.complexToFloat(value.data);
            } else if (value.type == 4) {
                d.type = 0;
                d.value = value.getFloat();
            } else if (value.type >= 16 && value.type <= 31) {
                d.type = 0;
                d.value = (float) value.data;
            }
            return d;
        }
        d.type = 0;
        d.value = 0.0f;
        return d;
    }

    public Rotate3dAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Rotate3dAnimation);
        this.mFromDegrees = a.getFloat(R.styleable.Rotate3dAnimation_fromDeg, 0.0f);
        this.mToDegrees = a.getFloat(R.styleable.Rotate3dAnimation_toDeg, 0.0f);
        this.mRollType = a.getInt(R.styleable.Rotate3dAnimation_rollType, 0);
        Description d = parseValue(a.peekValue(R.styleable.Rotate3dAnimation_pivotX));
        this.mPivotXType = d.type;
        this.mPivotXValue = d.value;
        Description d2 = parseValue(a.peekValue(R.styleable.Rotate3dAnimation_pivotY));
        this.mPivotYType = d2.type;
        this.mPivotYValue = d2.value;
        a.recycle();
        initializePivotPoint();
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees) {
        this.mRollType = rollType;
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;
        this.mPivotX = 0.0f;
        this.mPivotY = 0.0f;
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees, float pivotX, float pivotY) {
        this.mRollType = rollType;
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = pivotX;
        this.mPivotYValue = pivotY;
        initializePivotPoint();
    }

    public Rotate3dAnimation(int rollType, float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        this.mRollType = rollType;
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;
        this.mPivotXValue = pivotXValue;
        this.mPivotXType = pivotXType;
        this.mPivotYValue = pivotYValue;
        this.mPivotYType = pivotYType;
        initializePivotPoint();
    }

    private void initializePivotPoint() {
        if (this.mPivotXType == 0) {
            this.mPivotX = this.mPivotXValue;
        }
        if (this.mPivotYType == 0) {
            this.mPivotY = this.mPivotYValue;
        }
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.mCamera = new Camera();
        this.mPivotX = resolveSize(this.mPivotXType, this.mPivotXValue, width, parentWidth);
        this.mPivotY = resolveSize(this.mPivotYType, this.mPivotYValue, height, parentHeight);
    }

    /* access modifiers changed from: protected */
    public void applyTransformation(float interpolatedTime, Transformation t) {
        float fromDegrees = this.mFromDegrees;
        float degrees = fromDegrees + ((this.mToDegrees - fromDegrees) * interpolatedTime);
        Matrix matrix = t.getMatrix();
        this.mCamera.save();
        switch (this.mRollType) {
            case 0:
                this.mCamera.rotateX(degrees);
                break;
            case 1:
                this.mCamera.rotateY(degrees);
                break;
            case 2:
                this.mCamera.rotateZ(degrees);
                break;
        }
        this.mCamera.getMatrix(matrix);
        this.mCamera.restore();
        matrix.preTranslate(-this.mPivotX, -this.mPivotY);
        matrix.postTranslate(this.mPivotX, this.mPivotY);
    }
}
