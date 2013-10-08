package com.htong.persist;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htong.domain.IndexNodeModel;
import com.htong.init.TagDataBase;

public class IndexNodeModelOperate {
	private static final Logger log = LoggerFactory.getLogger(IndexNodeModelOperate.class);
	/**
	 * 获得路径
	 * @return
	 */
	public static String getPath(IndexNodeModel indexNodeModel) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(indexNodeModel.getName());
		
		while(indexNodeModel.getParentObject() instanceof IndexNodeModel) {
			IndexNodeModel parent = (IndexNodeModel)indexNodeModel.getParentObject();
			strBuilder.insert(0, parent.getName() + "/");
			
			indexNodeModel = (IndexNodeModel)indexNodeModel.getParentObject();
			
		}
		log.debug(strBuilder.toString());
		return strBuilder.toString();
	}
	
	/**
	 * 增加新节点
	 * @param indexNodeModel
	 */
	public static void addNewIndexNode(IndexNodeModel indexNodeModel) {
		if(indexNodeModel.getParentObject() instanceof String) {
			TagDataBase.INSTANCE.getIndexNodeMap().put(IndexNodeModelOperate.getPath(indexNodeModel), indexNodeModel);
			TagDataBase.INSTANCE.getIndexNodeList().add(indexNodeModel);
		} else {
			IndexNodeModel parentIndexNode = (IndexNodeModel)indexNodeModel.getParentObject();
			if(parentIndexNode.getIndexNodes() == null) {
				List<IndexNodeModel> iList = new ArrayList<IndexNodeModel>();
				iList.add(indexNodeModel);
				parentIndexNode.setIndexNodes(iList);
			} else {
				parentIndexNode.getIndexNodes().add(indexNodeModel);
			}
			TagDataBase.INSTANCE.getIndexNodeMap().put(IndexNodeModelOperate.getPath(indexNodeModel), indexNodeModel);
		}
		
		UpdateConfig.updateConfigToDatabase();
	}
	
	/**
	 * 删除节点
	 * @param indexNodeModel
	 */
	public static void removeIndexNode(IndexNodeModel indexNodeModel) {
		if(indexNodeModel.getParentObject() instanceof String) {
			TagDataBase.INSTANCE.getIndexNodeMap().remove(indexNodeModel.getName());
			TagDataBase.INSTANCE.getIndexNodeList().remove(indexNodeModel);
		} else {
			IndexNodeModel parentIndexNode = (IndexNodeModel)indexNodeModel.getParentObject();
			List<IndexNodeModel> indexNodeList = parentIndexNode.getIndexNodes();
			indexNodeList.remove(indexNodeModel);
			TagDataBase.INSTANCE.getIndexNodeMap().remove(getPath(indexNodeModel));
		}
		//删除井
		WellModelOperate.deleteWellByStartwithPath(getPath(indexNodeModel));
		
		UpdateConfig.updateConfigToDatabase();
		
	}
	/**
	 * 更新节点
	 * @param path
	 * @param indexNodeMOdel
	 */
	public static void updateIndexNodeModel(String oldStartwithPath , String newStartwithPath) {
		WellModelOperate.updateWellPath(oldStartwithPath, newStartwithPath);
		
		UpdateConfig.updateConfigToDatabase();
	}

}
