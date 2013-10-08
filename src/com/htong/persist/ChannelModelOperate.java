package com.htong.persist;

import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.htong.domain.ChannelModel;
import com.htong.util.CollectionConstants;

/**
 * 
 * @author 赵磊
 *
 */
public class ChannelModelOperate {
	/**
	 * 按通道名删除通道
	 * @param name
	 */
	public static void deleteChannelByDTUid(String dtuId) {
		Query query = new Query(Criteria.where("dtuId").is(dtuId));
		PersistManager.INSTANCE.getMongoTemplate().remove(CollectionConstants.CHANNEL_COLLECTION_NAME, query, ChannelModel.class);
	}
	
	/**
	 * 增加通道
	 * @param channel
	 */
	public static void addChannelModel(ChannelModel channel) {
		//先删除相同的
		deleteChannelByDTUid(channel.getDtuId());
		
		PersistManager.INSTANCE.getMongoTemplate().insert(CollectionConstants.CHANNEL_COLLECTION_NAME, channel);
		
		
	}

}
