package com.luisguadagnin.blockchain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest
{

    @Test
    public void hashShouldBeTheSameIfBlockHasNotChanged()
    {
        Block block = new Block("Data", "0", System.currentTimeMillis());

        String blockHash = block.getHash();
        String calculatedHash = block.calculateBlockHash();

        assertEquals(blockHash, calculatedHash);
    }

    @Test
    public void refreshHashShouldUpdateNonceAndHash()
    {
        Block block = new Block("Data", "0", System.currentTimeMillis());

        String blockHash = block.getHash();
        int blockNonce = block.getNonce();
        String blockData = block.getData();
        String blockPreviousHash = block.getPreviousHash();
        long blockTimestamp = block.getTimeStamp();

        block.refreshHash();

        String refreshedBlockHash = block.getHash();
        int refreshedBlockNonce = block.getNonce();
        String refreshedBlockData = block.getData();
        String refreshedBlockPreviousHash = block.getPreviousHash();
        long refreshedBlockTimestamp = block.getTimeStamp();

        assertAll(
                () -> assertNotEquals(blockHash, refreshedBlockHash),
                () -> assertNotEquals(blockNonce, refreshedBlockNonce),
                () -> assertEquals(blockData, refreshedBlockData),
                () -> assertEquals(blockPreviousHash, refreshedBlockPreviousHash),
                () -> assertEquals(blockTimestamp, refreshedBlockTimestamp)
        );
    }
}
