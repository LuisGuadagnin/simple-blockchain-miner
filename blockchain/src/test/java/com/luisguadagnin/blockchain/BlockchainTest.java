package com.luisguadagnin.blockchain;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BlockchainTest {

    @Test
    public void shouldAddValidBlock() throws NoSuchFieldException, IllegalAccessException {
        Blockchain blockchain = new Blockchain(5);
        Block block = buildBlock("Block mined by Miner #3", "0", 1621046930364L, 63796, "00000516db616ef4abf69ddc3af4389072af7210748c03df4aac20ab6f62d810");

        blockchain.addBlock(block);

        assertEquals(block.getHash(), blockchain.getLastHash());
    }

    @Test
    public void shouldNotAddBlockWithHashWrongDifficulty() throws NoSuchFieldException, IllegalAccessException {
        Blockchain blockchain = new Blockchain(5);
        Block block = buildBlock("Block mined by Miner #3", "0", 1621048952737L, 11123, "0000884d63495a5a5b8579ce4e67c836ea16bca5a535b1cfee3b0c1eb8fb7e29");

        blockchain.addBlock(block);

        assertNotEquals(block.getHash(), blockchain.getLastHash());
    }

    @Test
    public void shouldNotAddBlockWithInvalidHash() throws NoSuchFieldException, IllegalAccessException {
        Blockchain blockchain = new Blockchain(5);
        Block block = buildBlock("Block mined by Miner #3", "0", 1621046930364L, 63796, "00000516db616ef4abf69ddc3af4389072af7210748c03df4aac20ab6f62d811");

        blockchain.addBlock(block);

        assertNotEquals(block.getHash(), blockchain.getLastHash());
    }

    private Block buildBlock(String data, String previousHash, long timestamp, int nonce, String hash) {
        Block block = new Block(data, previousHash, timestamp);
        setNonce(block, nonce);
        setHash(block, hash);
        return block;
    }

    private void setNonce(Block block, int nonce) {
        try {
            Field nonceField = block.getClass().getDeclaredField("nonce");
            nonceField.setAccessible(true);
            nonceField.setInt(block, nonce);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            fail("Could not set 'nonce' field through reflections");
        }
    }

    private void setHash(Block block, String hash) {
        try {
            Field nonceField = block.getClass().getDeclaredField("hash");
            nonceField.setAccessible(true);
            nonceField.set(block, hash);
        } catch(IllegalAccessException | NoSuchFieldException e) {
            fail("Could not set 'hash' field through reflections");
        }
    }

}
