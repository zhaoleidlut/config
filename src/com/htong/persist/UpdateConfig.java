package com.htong.persist;

import com.htong.domain.IndexNodeModel;
import com.htong.init.TagDataBase;
import com.htong.ui.MainUIClass;
import com.htong.ui.tree.RootTreeModel;
import com.htong.util.CollectionConstants;

/**
 * 更新配置到数据库
 * @author 赵磊
 *
 */
public class UpdateConfig {
	public static void updateConfigToDatabase() {
		PersistManager.INSTANCE.getMongoTemplate().dropCollection(CollectionConstants.TAG_COLLECTION_NAME);
		for (IndexNodeModel indexNode : TagDataBase.INSTANCE
				.getIndexNodeList()) {
			setParentObjectNull(indexNode);
			PersistManager.INSTANCE.getMongoTemplate().insert(
					CollectionConstants.TAG_COLLECTION_NAME, indexNode);
		}



		TagDataBase.INSTANCE.init();
		MainUIClass.treeViewer.setInput(new RootTreeModel());
		MainUIClass.treeViewer.refresh();
	}
	
	/**
	 * 存储数据库时设置父级变量为空
	 * 
	 * @param indexNode
	 */
	private static void setParentObjectNull(IndexNodeModel indexNode) {
		indexNode.setParentObject(null);
		if (indexNode.getIndexNodes() != null
				&& !indexNode.getIndexNodes().isEmpty()) {
			for (IndexNodeModel in : indexNode.getIndexNodes()) {
				setParentObjectNull(in);
			}
		}
	}
}


