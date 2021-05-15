package com.luisguadagnin.blockchain;

import lombok.Getter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Getter
public class Block {

    private final String previousHash;
    private final String data;
    private final long timeStamp;

    private String hash;
    private int nonce;

    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.nonce = 0;
        this.hash = this.calculateBlockHash();
    }

    private void updateNonce() {
        this.nonce ++;
    }

    public String calculateBlockHash() {
        String dataToHash = previousHash
                + timeStamp
                + nonce
                + data;
        MessageDigest digest;
        byte[] bytes;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Unable to generate hash", ex);
        }
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public void refreshHash() {
        this.updateNonce();
        this.hash = this.calculateBlockHash();
    }

    @Override
    public String toString() {
        return "Block{" +
                "data='" + data + '\'' +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
