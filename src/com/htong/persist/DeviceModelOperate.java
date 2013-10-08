package com.htong.persist;

import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.htong.domain.ChannelModel;
import com.htong.domain.DeviceModel;
import com.htong.util.CollectionConstants;

public class DeviceModelOperate {
	/**
	 * 按设备号删除设备
	 * @param oid
	 */
	public static void deleteDeviceByOid(String oid) {
		Query query = new Query(Criteria.where("oid").is(oid));
		PersistManager.INSTANCE.getMongoTemplate().remove(CollectionConstants.DEVICE_COLLECTION_NAME, query, DeviceModel.class);
		
		ChannelModelOperate.deleteChannelByDTUid(oid);
		
	}
	
	public static void addDevcie(DeviceModel deviceModel) {
		//先删除相同设备
		deleteDeviceByOid(deviceModel.getOid());
		
		PersistManager.INSTANCE.getMongoTemplate().insert(CollectionConstants.DEVICE_COLLECTION_NAME, deviceModel);
		
		//增加通道
		ChannelModel channelModel = new ChannelModel();
		channelModel.setDtuId(deviceModel.getOid());
		channelModel.setOid(deviceModel.getOid());
		channelModel.setInterval("100");
		channelModel.setLoopInterval("3550");
		channelModel.setOffline("5");
		channelModel.setProtocal("DL645_DTU");
		channelModel.setHeartBeat(deviceModel.getName());
		channelModel.setTcpPort(deviceModel.getChannelName().substring(deviceModel.getOid().length()));//除去前3位dtu号
		channelModel.setName(deviceModel.getChannelName());
		
		
		ChannelModelOperate.addChannelModel(channelModel);
		
	}

}
