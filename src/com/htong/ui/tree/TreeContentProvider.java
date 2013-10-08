package com.htong.ui.tree;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htong.domain.IndexNodeModel;
import com.htong.domain.WellModel;
import com.htong.init.TagDataBase;
import com.htong.persist.IndexNodeModelOperate;
import com.htong.persist.WellModelOperate;

public class TreeContentProvider implements ITreeContentProvider {
	private final Logger log = LoggerFactory.getLogger(TreeContentProvider.class);

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof String) {
			String label = (String) parentElement;
			if (label.equals("标签配置")) {
				List<IndexNodeModel> indexNodeList = TagDataBase.INSTANCE.getIndexNodeList();
				if(indexNodeList == null || indexNodeList.isEmpty()) {
					return null;
				} else {
					for(IndexNodeModel in : indexNodeList) {
						in.setParentObject("标签配置");
					}
					return indexNodeList.toArray();
				}

			} else if (label.equals("其他配置")) {

			} 
		} else if (parentElement instanceof IndexNodeModel) {
			List<IndexNodeModel> indexNodeList = ((IndexNodeModel)parentElement).getIndexNodes();
			if(indexNodeList != null && !indexNodeList.isEmpty()) {
				for(IndexNodeModel in : indexNodeList) {
					in.setParentObject(parentElement);
				}
				return indexNodeList.toArray();
			} else {
				List<WellModel> wellList = WellModelOperate.getWellByPath(IndexNodeModelOperate.getPath((IndexNodeModel)parentElement));
				
				return  wellList.toArray();
			}

		} 
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof RootTreeModel) {
			return ((RootTreeModel) inputElement).getRoottree();
		}
		return null;
	}

	@Override
	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		Object[] children = this.getChildren(arg0);
		if (children == null) {
			return false;
		} else if (children.length > 0) {
			return true;
		} else {
			return false;
		}
	}

}
