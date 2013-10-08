package com.htong.ui.tree;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.htong.domain.IndexNodeModel;
import com.htong.domain.WellModel;
import com.swtdesigner.ResourceManager;

public class TreeLabelProvider implements ILabelProvider {
	
	private Image well_icon = ResourceManager.getPluginImage("config", "icons/well.png");
	
	private Image label_icon = ResourceManager.getPluginImage("config", "icons/label.gif");
	
	private Image query_icon = ResourceManager.getPluginImage("config", "icons/config.gif");

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object object) {
		if (object instanceof String) {
			return query_icon;
		} else if (object instanceof IndexNodeModel) {
			return label_icon;
		} else if (object instanceof WellModel) {
			return well_icon;
		} else 
		
		return null;
	}

	@Override
	public String getText(Object object) {
		if (object instanceof String) {
			return (String) object;
		} else if (object instanceof IndexNodeModel) {
			IndexNodeModel inm = (IndexNodeModel) object;
			return inm.getName();
		} else if (object instanceof WellModel) {
			WellModel well = (WellModel) object;
			return well.getName();
		}

		return null;
	}

}
