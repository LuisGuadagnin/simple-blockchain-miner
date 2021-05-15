package com.luisguadagnin.blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {

    public static final String FIRST_HASH = "0";

    private final List<Block> blockList;
    private String lastHash;
    private final int difficulty;

    public Blockchain(int difficulty) {
        this.blockList = new ArrayList<>();
        this.lastHash = FIRST_HASH;
        this.difficulty = difficulty;
    }

    public String getLastHash() {
        return lastHash;
    }

    public void addBlock(Block blockCandidate) {
        boolean isHashValid = blockCandidate.getHash().equals(blockCandidate.calculateBlockHash());
        boolean isHashWithDifficulty = blockCandidate.getHash().startsWith(new String(new char[difficulty]).replace('\0', '0'));

        if(isHashValid && isHashWithDifficulty) addBlockSynchronized(blockCandidate);
    }

    private synchronized void addBlockSynchronized(Block blockCandidate) {
        boolean isPreviousHashValid = blockCandidate.getPreviousHash().equals(this.getLastHash());
        if (isPreviousHashValid) {
            blockList.add(blockCandidate);
            lastHash = blockCandidate.getHash();

            System.out.println(blockCandidate);
        }
    }

    @Override
    public String toString() {
        return blockList.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}
