package com.ble.ble.oad;

public enum OADType {
    cc2541_oad {
        public String toString() {
            return "CC2541 OAD";
        }
    },
    cc2541_large_img_oad {
        public String toString() {
            return "CC2541 Large Image OAD";
        }
    },
    cc2640_on_chip_oad {
        public String toString() {
            return "CC2640 On-Chip OAD";
        }
    },
    cc2640_off_chip_oad {
        public String toString() {
            return "CC2640 Off-Chip OAD";
        }
    },
    cc2640_r2_oad {
        public String toString() {
            return "CC2640 R2 OAD";
        }
    },
    cc2640_oad_2_0_0 {
        public String toString() {
            return "CC2640 OAD 2.0.0";
        }
    }
}
