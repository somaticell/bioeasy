package com.ble.ble.oad;

public enum State {
    idle {
        public String toString() {
            return "idle";
        }
    },
    waitingImgInfo {
        public String toString() {
            return "waitingImgInfo";
        }
    },
    prepared {
        public String toString() {
            return "prepared";
        }
    },
    programming {
        public String toString() {
            return "programming";
        }
    },
    interrupted {
        public String toString() {
            return "interrupted";
        }
    },
    finished {
        public String toString() {
            return "finished";
        }
    }
}
