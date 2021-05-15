package com.luisguadagnin;

import com.luisguadagnin.blockchain.Blockchain;
import com.luisguadagnin.miner.BlockMiner;

import java.util.List;
import java.util.concurrent.Executors;

public class App
{
    public static void main( String[] args )
    {
        Blockchain b = new Blockchain(5);

        var miners = List.of(
                new BlockMiner(b, 1),
                new BlockMiner(b, 2),
                new BlockMiner(b, 3),
                new BlockMiner(b, 4)
        );

        var executorService = Executors.newFixedThreadPool(miners.size());
        miners.forEach(executorService::execute);
        executorService.shutdown();
    }
}
