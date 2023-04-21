package com.lvrenyang.io.base;

import android.util.Log;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class UDPIO extends IO {
    private static final String TAG = "UDPIO";
    private String IPAddress;
    private int PortNumber;
    private AtomicBoolean isOpened = new AtomicBoolean(false);
    private DatagramSocket mmClientSocket = null;
    private Vector<Byte> rxBuffer = new Vector<>();

    public boolean Open(String IPAddress2, int PortNumber2) {
        this.mMainLocker.lock();
        try {
            this.mmClientSocket = new DatagramSocket();
            this.IPAddress = IPAddress2;
            this.PortNumber = PortNumber2;
            this.isOpened.set(true);
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mMainLocker.unlock();
        }
        return this.isOpened.get();
    }

    public boolean Open(String local_address, int local_port, String dest_address, int dest_port) {
        this.mMainLocker.lock();
        try {
            this.mmClientSocket = new DatagramSocket(dest_port, InetAddress.getByName(local_address));
            this.IPAddress = dest_address;
            this.PortNumber = dest_port;
            this.isOpened.set(true);
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.mMainLocker.unlock();
        }
        return this.isOpened.get();
    }

    public boolean SetReuseAddress(boolean reuse) {
        try {
            this.mmClientSocket.setReuseAddress(reuse);
            return true;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        }
    }

    public boolean SetBroadcast(boolean broadcast) {
        try {
            this.mmClientSocket.setBroadcast(broadcast);
            return true;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        }
    }

    public void Close() {
        try {
            this.mmClientSocket.close();
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        }
        this.isOpened.set(false);
        return;
    }

    public int Write(byte[] buffer, int offset, int count) {
        if (!this.isOpened.get()) {
            return -1;
        }
        this.mMainLocker.lock();
        try {
            byte[] cmd = new byte[count];
            System.arraycopy(buffer, offset, cmd, 0, count);
            this.mmClientSocket.send(new DatagramPacket(cmd, cmd.length, InetAddress.getByName(this.IPAddress), this.PortNumber));
            return count;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return -1;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public int Read(byte[] buffer, int offset, int count, int timeout) {
        if (!this.isOpened.get()) {
            return -1;
        }
        this.mMainLocker.lock();
        int nBytesReaded = 0;
        try {
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < ((long) timeout) && nBytesReaded != count) {
                if (this.rxBuffer.size() > 0) {
                    buffer[offset + nBytesReaded] = this.rxBuffer.get(0).byteValue();
                    this.rxBuffer.remove(0);
                    nBytesReaded++;
                } else {
                    byte[] data = new byte[1024];
                    DatagramPacket recvPacket = new DatagramPacket(data, data.length);
                    this.mmClientSocket.setSoTimeout(timeout);
                    this.mmClientSocket.receive(recvPacket);
                    int nReceived = recvPacket.getLength();
                    if (nReceived > 0) {
                        String str = "Recv: ";
                        for (int i = 0; i < nReceived; i++) {
                            this.rxBuffer.add(Byte.valueOf(data[i]));
                            str = str + String.format(Locale.CHINA, "%02X ", new Object[]{Long.valueOf(((long) data[i]) & 255)});
                        }
                        Log.i(TAG, str);
                    }
                }
            }
            return nBytesReaded;
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            return -1;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public int RecvDirect(byte[] buffer, int offset, int count, int timeout) {
        if (!this.isOpened.get()) {
            return -1;
        }
        this.mMainLocker.lock();
        try {
            byte[] data = new byte[count];
            DatagramPacket recvPacket = new DatagramPacket(data, data.length);
            this.mmClientSocket.setSoTimeout(timeout);
            this.mmClientSocket.receive(recvPacket);
            int nBytesReaded = recvPacket.getLength();
            if (nBytesReaded > 0) {
                System.arraycopy(data, 0, buffer, offset, nBytesReaded);
            }
            return nBytesReaded;
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            return 0;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public DatagramPacket RecvPacketDirect(int max_count, int timeout) {
        if (!this.isOpened.get()) {
            return null;
        }
        this.mMainLocker.lock();
        byte[] data = new byte[max_count];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            this.mmClientSocket.setSoTimeout(timeout);
            this.mmClientSocket.receive(packet);
            return packet;
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
            return packet;
        } finally {
            this.mMainLocker.unlock();
        }
    }

    public void SkipAvailable() {
        this.mMainLocker.lock();
        this.rxBuffer.clear();
        this.mMainLocker.unlock();
    }

    public boolean IsOpened() {
        return this.isOpened.get();
    }
}
