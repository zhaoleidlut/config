package com.htong.util;

import com.htong.persist.PersistManager;
import com.mongodb.BasicDBObject;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PersistManager.INSTANCE.getMongoTemplate().insert("channel", new BasicDBObject("df","df"));

	}

}
