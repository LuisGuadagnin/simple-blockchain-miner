package com.luisguadagnin.miner;

import com.luisguadagnin.blockchain.Block;
import com.luisguadagnin.blockchain.Blockchain;

public class BlockMiner implements Runnable {

    private final Blockchain blockchain;
    private final int minerId;

    private Block blockCandidate;

    public BlockMiner(Blockchain blockchain, int minerId) {
        this.blockchain = blockchain;
        this.minerId = minerId;
    }

    private void updateBlockCandidate() {
        String lastHash = blockchain.getLastHash();
        if (blockCandidate == null || !blockCandidate.getPreviousHash().equals(lastHash)) {
            blockCandidate = new Block("Block mined by Miner #" + minerId, lastHash, System.currentTimeMillis());
        }
        else {
            blockCandidate.refreshHash();
        }
    }

    @Override
    public void run() {
        while(true) {
            updateBlockCandidate();
            blockchain.addBlock(blockCandidate);
        }
    }
}
