package com.chanven.lib.cptr.indicator;

public class PtrTensionIndicator extends PtrIndicator {
    private float DRAG_RATE = 0.5f;
    private float mCurrentDragPercent;
    private float mDownPos;
    private float mDownY;
    private float mOneHeight = 0.0f;
    private float mReleasePercent = -1.0f;
    private int mReleasePos;

    public void onPressDown(float x, float y) {
        super.onPressDown(x, y);
        this.mDownY = y;
        this.mDownPos = (float) getCurrentPosY();
    }

    public void onRelease() {
        super.onRelease();
        this.mReleasePos = getCurrentPosY();
        this.mReleasePercent = this.mCurrentDragPercent;
    }

    public void onUIRefreshComplete() {
        this.mReleasePos = getCurrentPosY();
        this.mReleasePercent = getOverDragPercent();
    }

    public void setHeaderHeight(int height) {
        super.setHeaderHeight(height);
        this.mOneHeight = (((float) height) * 4.0f) / 5.0f;
    }

    /* access modifiers changed from: protected */
    public void processOnMove(float currentX, float currentY, float offsetX, float offsetY) {
        if (currentY < this.mDownY) {
            super.processOnMove(currentX, currentY, offsetX, offsetY);
            return;
        }
        float scrollTop = ((currentY - this.mDownY) * this.DRAG_RATE) + this.mDownPos;
        float currentDragPercent = scrollTop / this.mOneHeight;
        if (currentDragPercent < 0.0f) {
            setOffset(offsetX, 0.0f);
            return;
        }
        this.mCurrentDragPercent = currentDragPercent;
        float boundedDragPercent = Math.min(1.0f, Math.abs(currentDragPercent));
        float tensionSlingshotPercent = Math.max(0.0f, Math.min(scrollTop - this.mOneHeight, this.mOneHeight * 2.0f) / this.mOneHeight);
        float f = currentX;
        setOffset(f, (float) (((int) ((this.mOneHeight * boundedDragPercent) + ((this.mOneHeight * (((float) (((double) (tensionSlingshotPercent / 4.0f)) - Math.pow((double) (tensionSlingshotPercent / 4.0f), 2.0d))) * 2.0f)) / 2.0f))) - getCurrentPosY()));
    }

    private float offsetToTarget(float scrollTop) {
        float currentDragPercent = scrollTop / this.mOneHeight;
        this.mCurrentDragPercent = currentDragPercent;
        float boundedDragPercent = Math.min(1.0f, Math.abs(currentDragPercent));
        float tensionSlingshotPercent = Math.max(0.0f, Math.min(scrollTop - this.mOneHeight, this.mOneHeight * 2.0f) / this.mOneHeight);
        int pow = (int) ((this.mOneHeight * boundedDragPercent) + ((this.mOneHeight * (((float) (((double) (tensionSlingshotPercent / 4.0f)) - Math.pow((double) (tensionSlingshotPercent / 4.0f), 2.0d))) * 2.0f)) / 2.0f));
        return 0.0f;
    }

    public int getOffsetToKeepHeaderWhileLoading() {
        return getOffsetToRefresh();
    }

    public int getOffsetToRefresh() {
        return (int) this.mOneHeight;
    }

    public float getOverDragPercent() {
        if (isUnderTouch()) {
            return this.mCurrentDragPercent;
        }
        if (this.mReleasePercent <= 0.0f) {
            return (1.0f * ((float) getCurrentPosY())) / ((float) getOffsetToKeepHeaderWhileLoading());
        }
        return (this.mReleasePercent * ((float) getCurrentPosY())) / ((float) this.mReleasePos);
    }
}
