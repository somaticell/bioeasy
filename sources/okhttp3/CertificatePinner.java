package okhttp3;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    /* access modifiers changed from: private */
    public final List<Pin> pins;
    /* access modifiers changed from: private */
    public final TrustRootIndex trustRootIndex;

    private CertificatePinner(Builder builder) {
        this.pins = Util.immutableList(builder.pins);
        this.trustRootIndex = builder.trustRootIndex;
    }

    public void check(String hostname, List<Certificate> peerCertificates) throws SSLPeerUnverifiedException {
        List<Pin> pins2 = findMatchingPins(hostname);
        if (!pins2.isEmpty()) {
            if (this.trustRootIndex != null) {
                peerCertificates = new CertificateChainCleaner(this.trustRootIndex).clean(peerCertificates);
            }
            int certsSize = peerCertificates.size();
            for (int c = 0; c < certsSize; c++) {
                X509Certificate x509Certificate = (X509Certificate) peerCertificates.get(c);
                ByteString sha1 = null;
                ByteString sha256 = null;
                int pinsSize = pins2.size();
                for (int p = 0; p < pinsSize; p++) {
                    Pin pin = pins2.get(p);
                    if (pin.hashAlgorithm.equals("sha256/")) {
                        if (sha256 == null) {
                            sha256 = sha256(x509Certificate);
                        }
                        if (pin.hash.equals(sha256)) {
                            return;
                        }
                    } else if (pin.hashAlgorithm.equals("sha1/")) {
                        if (sha1 == null) {
                            sha1 = sha1(x509Certificate);
                        }
                        if (pin.hash.equals(sha1)) {
                            return;
                        }
                    } else {
                        throw new AssertionError();
                    }
                }
            }
            StringBuilder message = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
            int certsSize2 = peerCertificates.size();
            for (int c2 = 0; c2 < certsSize2; c2++) {
                X509Certificate x509Certificate2 = (X509Certificate) peerCertificates.get(c2);
                message.append("\n    ").append(pin(x509Certificate2)).append(": ").append(x509Certificate2.getSubjectDN().getName());
            }
            message.append("\n  Pinned certificates for ").append(hostname).append(":");
            int pinsSize2 = pins2.size();
            for (int p2 = 0; p2 < pinsSize2; p2++) {
                message.append("\n    ").append(pins2.get(p2));
            }
            throw new SSLPeerUnverifiedException(message.toString());
        }
    }

    public void check(String hostname, Certificate... peerCertificates) throws SSLPeerUnverifiedException {
        check(hostname, (List<Certificate>) Arrays.asList(peerCertificates));
    }

    /* access modifiers changed from: package-private */
    public List<Pin> findMatchingPins(String hostname) {
        List<Pin> result = Collections.emptyList();
        for (Pin pin : this.pins) {
            if (pin.matches(hostname)) {
                if (result.isEmpty()) {
                    result = new ArrayList<>();
                }
                result.add(pin);
            }
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public Builder newBuilder() {
        return new Builder(this);
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha256/" + sha256((X509Certificate) certificate).base64();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    static ByteString sha1(X509Certificate x509Certificate) {
        return Util.sha1(ByteString.of(x509Certificate.getPublicKey().getEncoded()));
    }

    static ByteString sha256(X509Certificate x509Certificate) {
        return Util.sha256(ByteString.of(x509Certificate.getPublicKey().getEncoded()));
    }

    static final class Pin {
        final ByteString hash;
        final String hashAlgorithm;
        final String pattern;

        Pin(String pattern2, String pin) {
            this.pattern = pattern2;
            if (pin.startsWith("sha1/")) {
                this.hashAlgorithm = "sha1/";
                this.hash = ByteString.decodeBase64(pin.substring("sha1/".length()));
            } else if (pin.startsWith("sha256/")) {
                this.hashAlgorithm = "sha256/";
                this.hash = ByteString.decodeBase64(pin.substring("sha256/".length()));
            } else {
                throw new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + pin);
            }
            if (this.hash == null) {
                throw new IllegalArgumentException("pins must be base64: " + pin);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean matches(String hostname) {
            boolean z = false;
            if (this.pattern.equals(hostname)) {
                return true;
            }
            int firstDot = hostname.indexOf(46);
            if (this.pattern.startsWith("*.")) {
                if (hostname.regionMatches(false, firstDot + 1, this.pattern, 2, this.pattern.length() - 2)) {
                    z = true;
                }
            }
            return z;
        }

        public boolean equals(Object other) {
            return (other instanceof Pin) && this.pattern.equals(((Pin) other).pattern) && this.hashAlgorithm.equals(((Pin) other).hashAlgorithm) && this.hash.equals(((Pin) other).hash);
        }

        public int hashCode() {
            return ((((this.pattern.hashCode() + 527) * 31) + this.hashAlgorithm.hashCode()) * 31) + this.hash.hashCode();
        }

        public String toString() {
            return this.hashAlgorithm + this.hash.base64();
        }
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public final List<Pin> pins = new ArrayList();
        /* access modifiers changed from: private */
        public TrustRootIndex trustRootIndex;

        public Builder() {
        }

        Builder(CertificatePinner certificatePinner) {
            this.pins.addAll(certificatePinner.pins);
            this.trustRootIndex = certificatePinner.trustRootIndex;
        }

        public Builder trustRootIndex(TrustRootIndex trustRootIndex2) {
            this.trustRootIndex = trustRootIndex2;
            return this;
        }

        public Builder add(String pattern, String... pins2) {
            if (pattern == null) {
                throw new IllegalArgumentException("pattern == null");
            }
            for (String pin : pins2) {
                this.pins.add(new Pin(pattern, pin));
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(this);
        }
    }
}
