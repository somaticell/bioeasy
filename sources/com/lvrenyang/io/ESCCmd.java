package com.lvrenyang.io;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;

class ESCCmd {
    public byte[] CR = {13};
    public byte[] DLE_DC4_n_m_t = {16, 20, 1, 0, 1};
    public byte[] ESC_3_n = {27, 51, 0};
    public byte[] ESC_9_n = {27, 57, 1};
    public byte[] ESC_ALT = {27, 64};
    public byte[] ESC_CAN = {24};
    public byte[] ESC_FF = {27, 12};
    public byte[] ESC_L = {27, 76};
    public byte[] ESC_M_n = {27, 77, 0};
    public byte[] ESC_R_n = {27, 82, 0};
    public byte[] ESC_S = {27, 83};
    public byte[] ESC_SP_n = {27, 32, 0};
    public byte[] ESC_T_n = {27, 84, 0};
    public byte[] ESC_V_n = {27, 86, 0};
    public byte[] ESC_W_xL_xH_yL_yH_dxL_dxH_dyL_dyH = {27, 87, 0, 0, 0, 0, 72, 2, -80, 4};
    public byte[] ESC_a_n = {27, 97, 0};
    public byte[] ESC_dollors_nL_nH = {27, 36, 0, 0};
    public byte[] ESC_lbracket_n = {27, 123, 0};
    public byte[] ESC_line_n = {27, 45, 0};
    public byte[] ESC_t_n = {27, 116, 0};
    public byte[] FF = {12};
    public byte[] FS_AND = {28, BLEServiceApi.POWER_STATUS_CMD};
    public byte[] FS_line_n = {28, 45, 0};
    public byte[] FS_p_n_m = {28, 112, 1, 0};
    public byte[] GS_B_n = {29, 66, 0};
    public byte[] GS_E_n = {27, 69, 0};
    public byte[] GS_H_n = {29, 72, 0};
    public byte[] GS_P_x_y = {29, 80, 0, 0};
    public byte[] GS_V_m = {29, 86, 0};
    public byte[] GS_V_m_n = {29, 86, 66, 0};
    public byte[] GS_W_nL_nH = {29, 87, 118, 2};
    public byte[] GS_backslash_m = {29, 47, 0};
    public byte[] GS_backslash_nL_nH = {29, 92, 0, 0};
    public byte[] GS_dollors_nL_nH = {29, 36, 0, 0};
    public byte[] GS_exclamationmark_n = {29, BLEServiceApi.LED_CMD, 0};
    public byte[] GS_f_n = {29, 102, 0};
    public byte[] GS_h_n = {29, 104, -94};
    public byte[] GS_k_m_n_ = {29, 107, 65, 12};
    public byte[] GS_k_m_v_r_nL_nH = {29, 107, 97, 0, 2, 0, 0};
    public byte[] GS_leftbracket_k_pL_pH_cn_67_n = {29, 40, 107, 3, 0, 49, 67, 3};
    public byte[] GS_leftbracket_k_pL_pH_cn_69_n = {29, 40, 107, 3, 0, 49, 69, 48};
    public byte[] GS_leftbracket_k_pL_pH_cn_80_m__d1dk = {29, 40, 107, 3, 0, 49, 80, 48};
    public byte[] GS_leftbracket_k_pL_pH_cn_fn_m = {29, 40, 107, 3, 0, 49, 81, 48};
    public byte[] GS_w_n = {29, 119, 3};
    public byte[] LF = {10};

    ESCCmd() {
    }
}
