package com.htong.persist;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.data.document.mongodb.query.Update;

import com.htong.domain.DeviceModel;
import com.htong.domain.WellModel;
import com.htong.util.CollectionConstants;

public class WellModelOperate {
	private static final Logger log = LoggerFactory.getLogger(WellModelOperate.class);
	
	/**
	 * 通过路径获得井
	 * @param path
	 * @return
	 */
	public static List<WellModel> getWellByPath(String path) {
		log.debug(path);
		Query query = new Query(Criteria.where("path").is(path));
		List<WellModel> wellList = PersistManager.INSTANCE.getMongoTemplate().find(CollectionConstants.WELL_COLLECTION_NAME, query, WellModel.class);
		return wellList;
	}
	
	/**
	 * 通过起始路径删除井
	 * @param startwithPath
	 */
	public static void deleteWellByStartwithPath(String startwithPath) {
		Query query = new Query(Criteria.where("path").regex("^"+startwithPath));
		PersistManager.INSTANCE.getMongoTemplate().remove(CollectionConstants.WELL_COLLECTION_NAME, query, WellModel.class);
	}
	
	/**
	 * 通过井号删除井
	 * @param num
	 */
	public static void deleteWellByNum(String num, String dtuid, WellModel wellModel) {
		Query query = new Query(Criteria.where("num").is(num));
		PersistManager.INSTANCE.getMongoTemplate().remove(CollectionConstants.WELL_COLLECTION_NAME, query, WellModel.class);
		
		//删除设备
		DeviceModelOperate.deleteDeviceByOid(dtuid);

	}
	/**
	 * 更新井的路径
	 * @param oldStartWithPath
	 * @param newStartWithPath
	 */
	public static void updateWellPath(String oldStartWithPath, String newStartWithPath) {
		Query query = new Query(Criteria.where("path").regex("^"+oldStartWithPath));
		List<WellModel> wellList = PersistManager.INSTANCE.getMongoTemplate().find(CollectionConstants.WELL_COLLECTION_NAME, query, WellModel.class);
		
		for(WellModel well : wellList) {
			String newPath = well.getPath().replace(oldStartWithPath, newStartWithPath);
			Query query1 = new Query(Criteria.where("path").is(well.getPath()));
			Update update = new Update();
			update.set("path", newPath);
			PersistManager.INSTANCE.getMongoTemplate().updateMulti(CollectionConstants.WELL_COLLECTION_NAME, query1, update);
		}
	}
	
	/**
	 * 通过井号更新井
	 * @param oldNum
	 * @param wellModel
	 */
	public static void updateWellByNum(String oldNum, String dtuid, WellModel wellModel) {
		//先删除存在的
		deleteWellByNum(oldNum, dtuid,wellModel);
		
		PersistManager.INSTANCE.getMongoTemplate().insert(CollectionConstants.WELL_COLLECTION_NAME, wellModel);
		
		
		//添加设备
		DeviceModel device = new DeviceModel();
		device.setOid(wellModel.getDtuId());//设备oid与dtuId相同
		device.setName(wellModel.getHeartBeat());//设备名与心跳信号相同
		device.setSlaveId(wellModel.getSlaveId());
		device.setChannelName(wellModel.getDtuId()+wellModel.getPort());//dtuID + port
		device.setRetry("3");
		device.setTimeout("5000");
		device.setUsed("true");
		
		DeviceModelOperate.addDevcie(device);
		
		UpdateConfig.updateConfigToDatabase();
		
	}
	
	/**
	 * 添加井
	 * @param wellModel
	 */
	public static void addWell(WellModel wellModel, String dtuid) {
		//先删除存在的
		deleteWellByNum(wellModel.getNum(),dtuid,wellModel);
		
		wellModel.setDeviceOid(wellModel.getDtuId());
		
		PersistManager.INSTANCE.getMongoTemplate().insert(CollectionConstants.WELL_COLLECTION_NAME, wellModel);
		
		
		//添加设备
		DeviceModel device = new DeviceModel();
		device.setOid(wellModel.getDtuId());//设备oid与dtuId相同
		device.setName(wellModel.getHeartBeat());//设备名与心跳信号相同
		device.setSlaveId(wellModel.getSlaveId());
		device.setChannelName(wellModel.getDtuId()+wellModel.getPort());//dtuID + port
		device.setRetry("3");
		device.setTimeout("5000");
		device.setUsed("true");
		
		DeviceModelOperate.addDevcie(device);
		
		UpdateConfig.updateConfigToDatabase();
		
	}
}
