package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;

public class UploadDataCallback implements Serializable {
    private String message;
    private int result = 0;
    private SampleData sampleData;
    private int sampleId;
    private int uploadId;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public int getSampleId() {
        return this.sampleId;
    }

    public void setSampleId(int sampleId2) {
        this.sampleId = sampleId2;
    }

    public int getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(int uploadId2) {
        this.uploadId = uploadId2;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result2) {
        this.result = result2;
    }

    public SampleData getSampleData() {
        return this.sampleData;
    }

    public void setSampleData(SampleData sampleData2) {
        this.sampleData = sampleData2;
    }
}
