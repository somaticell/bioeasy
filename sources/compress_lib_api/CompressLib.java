package compress_lib_api;

public class CompressLib {
    public static native byte[] RasterImageToCompressCmd(int i, int i2, byte[] bArr);

    static {
        System.loadLibrary("compress-lib");
    }
}
