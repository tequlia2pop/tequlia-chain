package com.gmail.tequlia2pop.chain;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

/**
 * 区块。
 * 
 * @author tequlia2pop
 */
public class Block {

	/** 当前区块的 hash 值，代表了数字签名 **/
	private String hash;

	/** 前一个区块的 hash 值 **/
	private final String previousHash;

	/** 数据，这里是一条简单的消息 **/
	private final String data;

	/** 时间戳 **/
	@JsonbDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
	private final LocalDateTime timeStamp;

	/** 用于工作量证明算法的计数器 **/
	private int nonce;

	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = LocalDateTime.now();

		this.hash = calculateHash(); // 确保在设置其他值后执行此操作
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public String getData() {
		return data;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	/**
	 * 基于区块内容计算新的 hash。
	 * 
	 * @return
	 */
	public String calculateHash() {
		String hash = StringUtil.sha256Hex(previousHash + timeStamp.toString() + Integer.toString(nonce) + data);
		return hash;
	}

	/**
	 * 挖矿：只有当为区块生成的 hash 值以指定数量的 0 开头，才能将其添加到区块链中。
	 * Q: 增加 nonce 值，直到达到 hash 目标为止。
	 * 
	 * 像 1 或 2 这样低难度的 difficulty 值，也许一台计算机就可以解决了。
	 * 我建议将 difficulty 的值设置为 4-6 来做测试。
	 * 现在莱特币挖矿的 difficulty 值约为 442,592。
	 * 
	 * @param difficulty 程序需要计算处理的 0 的数量
	 */
	public void mineBlock(int difficulty) {
		// 创建一个用 difficulty * "0" 组成的字符串。
		String target = StringUtil.getDificultyString(difficulty);
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

}
