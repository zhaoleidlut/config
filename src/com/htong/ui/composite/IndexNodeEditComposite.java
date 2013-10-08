package com.htong.ui.composite;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.htong.domain.IndexNodeModel;
import com.htong.persist.IndexNodeModelOperate;
/**
 * 标签索引编辑面板
 * @author 赵磊
 *
 */
public class IndexNodeEditComposite extends Composite {
	private Text text_name;
	private IndexNodeModel indexNodeModel;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public IndexNodeEditComposite(final Composite parent, int style, final IndexNodeModel indexNodeModel) {
		super(parent, style);
		this.indexNodeModel = indexNodeModel;
		GridLayout gridLayout = new GridLayout(2, false);
		setLayout(gridLayout);
		
		Label name = new Label(this, SWT.NONE);
		name.setText("标签名字：");
		
		text_name = new Text(this, SWT.BORDER);
		GridData gd_text_name = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_name.widthHint = 200;
		text_name.setLayoutData(gd_text_name);
		text_name.setText(indexNodeModel.getName() == null ? "" :indexNodeModel.getName());

		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if("".equals(text_name.getText().trim())) {
					MessageDialog.openError(getShell(), "错误", "名字不能为空！");
					return;
				} 
				if(indexNodeModel.getName() == null) {//新建
					indexNodeModel.setName(text_name.getText().trim());
					
					IndexNodeModelOperate.addNewIndexNode(indexNodeModel);
					
					
//					MainUIClass.treeViewer.add(indexNodeModel.getParentObject(), indexNodeModel);	//更新树
//					MainUIClass.treeViewer.setExpandedState(indexNodeModel.getParentObject(), true);	//展开新节点
					
				} else {//编辑
					String oldPath = IndexNodeModelOperate.getPath(indexNodeModel);
					indexNodeModel.setName(text_name.getText());
					
					IndexNodeModelOperate.updateIndexNodeModel(oldPath,IndexNodeModelOperate.getPath(indexNodeModel));
					
					//更新树
//					MainUIClass.treeViewer.update(indexNodeModel, null);
				}
				
				((OperatingComposite)parent).setTop("标签已保存！");
			}
		});
		btnNewButton.setBounds(45, 39, 72, 22);
		btnNewButton.setText("保  存");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				((OperatingComposite)parent).setTop("操作已取消！");
			}
		});
		btnNewButton_1.setBounds(173, 39, 72, 22);
		btnNewButton_1.setText("取  消");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
