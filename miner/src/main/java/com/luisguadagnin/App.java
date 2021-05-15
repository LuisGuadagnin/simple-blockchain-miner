package com.luisguadagnin;

import com.luisguadagnin.blockchain.Blockchain;
import com.luisguadagnin.miner.BlockMiner;

import java.util.List;
import java.util.concurrent.Executors;

public class App
{
    public static void main( String[] args )
    {
        var blockchain = new Blockchain(5);

        var miners = List.of(
                new BlockMiner(blockchain, 1),
                new BlockMiner(blockchain, 2),
                new BlockMiner(blockchain, 3),
                new BlockMiner(blockchain, 4)
        );

        var executorService = Executors.newFixedThreadPool(miners.size());
        miners.forEach(executorService::execute);
        executorService.shutdown();
    }
}
