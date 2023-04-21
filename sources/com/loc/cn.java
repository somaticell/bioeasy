package com.loc;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

final class cn extends PhoneStateListener {
    private /* synthetic */ cl a;

    private cn(cl clVar) {
        this.a = clVar;
    }

    /* synthetic */ cn(cl clVar, byte b) {
        this(clVar);
    }

    public final void onCellLocationChanged(CellLocation cellLocation) {
        try {
            long unused = this.a.w = System.currentTimeMillis();
            CellLocation unused2 = this.a.A = cellLocation;
            super.onCellLocationChanged(cellLocation);
        } catch (Exception e) {
        }
    }

    public final void onServiceStateChanged(ServiceState serviceState) {
        try {
            if (serviceState.getState() == 0) {
                boolean unused = this.a.n = true;
                String[] a2 = cl.b(this.a.e);
                int unused2 = this.a.r = Integer.parseInt(a2[0]);
                int unused3 = this.a.s = Integer.parseInt(a2[1]);
            } else {
                boolean unused4 = this.a.n = false;
            }
            super.onServiceStateChanged(serviceState);
        } catch (Exception e) {
        }
    }

    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        try {
            if (this.a.l) {
                int unused = this.a.m = signalStrength.getCdmaDbm();
            } else {
                int unused2 = this.a.m = signalStrength.getGsmSignalStrength();
                if (this.a.m == 99) {
                    int unused3 = this.a.m = -1;
                } else {
                    int unused4 = this.a.m = (this.a.m * 2) - 113;
                }
            }
            super.onSignalStrengthsChanged(signalStrength);
        } catch (Exception e) {
        }
    }
}
