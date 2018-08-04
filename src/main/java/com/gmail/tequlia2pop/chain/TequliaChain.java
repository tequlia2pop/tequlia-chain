package com.gmail.tequlia2pop.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * 区块链应用入口。
 * 
 * @author tequlia2pop
 */
public class TequliaChain {

	public static List<Block> blockchain = new ArrayList<>();

	public static int difficulty = 4;

	public static void main(String[] args) {
		System.out.println("Trying to Mine block 1... ");
		addBlock(new Block("Hi im the first block", "0"));
	
		System.out.println("Trying to Mine block 2... ");
		addBlock(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).getHash()));
	
		System.out.println("Trying to Mine block 3... ");
		addBlock(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).getHash()));
	
		System.out.println("\nBlockchain is Valid: " + isChainValid());
	
		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}

	/**
	 * 将区块添加到 blockchain 列表中。
	 * 
	 * @param newBlock
	 */
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

	/**
	 * 检查区块链是否合法，它会遍历链中所有的区块并比较其 hash 值。
	 * 
	 * 这个方法需要能够检查当前区块的 hash 值和计算出来的 hash 值是否相等，
	 * 以及前一个区块的 hash 值是否等于当前区块存储的 previousHash 值。
	 * 
	 * @return
	 */
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		// 遍历区块链并检查 hash 值。
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// 比较当前区块存储的 hash 值和计算出来的 hash 值。
			if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// 比较前一个区块存储的 hash 值和当前区块存储的 previousHash 值。
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// 检查区块是否已被开采。
			if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
}
