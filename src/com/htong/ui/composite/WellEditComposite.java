package com.htong.ui.composite;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.htong.domain.WellModel;
import com.htong.persist.WellModelOperate;
/**
 * 标签索引编辑面板
 * @author 赵磊
 *
 */
public class WellEditComposite extends Composite {
	private Text text_name;
	private WellModel wellModel;
	private Text text_chouyoujixinghao;
	private Text text_bengjing;
	private Text text_bengshen;
	private Text text_hanshuiliang;
	private Text text_density;
	private Text text_dtuid;
	private Text text_port;
	private Text text_slaveid;
	private Text text_heartbeat;
	private Text text_chongcheng;
	private Text text_secret;
	private Text text_num;
	private Text text_loss;
	private Text text_mobile;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public WellEditComposite(final Composite parent, int style, final WellModel wellModel) {
		super(parent, style);
		this.wellModel = wellModel;
		GridLayout gridLayout = new GridLayout(2, false);
		setLayout(gridLayout);
		
		Group group_3 = new Group(this, SWT.NONE);
		group_3.setLayout(new GridLayout(2, false));
		GridData gd_group_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_group_3.widthHint = 483;
		group_3.setLayoutData(gd_group_3);
		group_3.setText("井参数");
		
		Label name = new Label(group_3, SWT.NONE);
		name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		name.setText("井名：");
		
		text_name = new Text(group_3, SWT.BORDER);
		GridData gd_text_name = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_name.widthHint = 200;
		text_name.setLayoutData(gd_text_name);
		text_name.setText(wellModel.getName() == null ? "" :wellModel.getName());
		
		Label label_3 = new Label(group_3, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("井号：");
		
		text_num = new Text(group_3, SWT.BORDER);
		GridData gd_text_num = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_num.widthHint = 200;
		text_num.setLayoutData(gd_text_num);
		
		Label label = new Label(group_3, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("抽油机型号：");
		
		text_chouyoujixinghao = new Text(group_3, SWT.BORDER);
		GridData gd_text_chouyoujixinghao = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_chouyoujixinghao.widthHint = 200;
		text_chouyoujixinghao.setLayoutData(gd_text_chouyoujixinghao);
		
		Label label_1 = new Label(group_3, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("泵径(毫米)：");
		
		text_bengjing = new Text(group_3, SWT.BORDER);
		GridData gd_text_bengjing = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_bengjing.widthHint = 200;
		text_bengjing.setLayoutData(gd_text_bengjing);
		
		Label lblNewLabel = new Label(group_3, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("泵深(米)：");
		
		text_bengshen = new Text(group_3, SWT.BORDER);
		GridData gd_text_bengshen = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_bengshen.widthHint = 200;
		text_bengshen.setLayoutData(gd_text_bengshen);
		
		Label label_2 = new Label(group_3, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("含水量(%)：");
		
		text_hanshuiliang = new Text(group_3, SWT.BORDER);
		GridData gd_text_hanshuiliang = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_hanshuiliang.widthHint = 200;
		text_hanshuiliang.setLayoutData(gd_text_hanshuiliang);
		
		Label lblgcm = new Label(group_3, SWT.NONE);
		lblgcm.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblgcm.setText("油密度(g/cm³)：");
		
		text_density = new Text(group_3, SWT.BORDER);
		GridData gd_text_density = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_density.widthHint = 200;
		text_density.setLayoutData(gd_text_density);
		
		Label lblm = new Label(group_3, SWT.NONE);
		lblm.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblm.setText("理论冲程(m)：");
		
		text_chongcheng = new Text(group_3, SWT.BORDER);
		GridData gd_text_chongcheng = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_chongcheng.widthHint = 200;
		text_chongcheng.setLayoutData(gd_text_chongcheng);
		
		Label label_5 = new Label(group_3, SWT.NONE);
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setText("补偿量：");
		
		text_loss = new Text(group_3, SWT.BORDER);
		GridData gd_text_loss = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_loss.widthHint = 200;
		text_loss.setLayoutData(gd_text_loss);
		
		Label label_6 = new Label(group_3, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_6.setText("负责人手机号：");
		
		text_mobile = new Text(group_3, SWT.BORDER);
		GridData gd_text_mobile = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_mobile.widthHint = 200;
		text_mobile.setLayoutData(gd_text_mobile);
		new Label(group_3, SWT.NONE);
		new Label(group_3, SWT.NONE);
		
		
		Group group_2 = new Group(this, SWT.NONE);
		group_2.setText("通讯参数");
		group_2.setLayout(new GridLayout(2, false));
		group_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		
		Label lblDtuId = new Label(group_2, SWT.NONE);
		lblDtuId.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDtuId.setText("DTU ID号：");
		
		text_dtuid = new Text(group_2, SWT.BORDER);
		GridData gd_text_dtuid = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_dtuid.widthHint = 80;
		text_dtuid.setLayoutData(gd_text_dtuid);
		
		Label label_9 = new Label(group_2, SWT.NONE);
		label_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_9.setText("通讯端口：");
		
		text_port = new Text(group_2, SWT.BORDER);
		GridData gd_text_port = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_port.widthHint = 80;
		text_port.setLayoutData(gd_text_port);
		
		Label label_4 = new Label(group_2, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setText("心跳信号：");
		
		text_heartbeat = new Text(group_2, SWT.BORDER);
		GridData gd_text_heartbeat = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_heartbeat.widthHint = 80;
		text_heartbeat.setLayoutData(gd_text_heartbeat);
		
		Label label_10 = new Label(group_2, SWT.NONE);
		label_10.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_10.setText("仪表地址：");
		
		text_slaveid = new Text(group_2, SWT.BORDER);
		GridData gd_text_slaveid = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_slaveid.widthHint = 80;
		text_slaveid.setLayoutData(gd_text_slaveid);



		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if("".equals(text_name.getText().trim())) {
					MessageDialog.openError(getShell(), "错误", "请输入名字！");
					return;
				} else if("".equals(text_dtuid.getText().trim())) {
					MessageDialog.openError(getShell(), "错误", "请输入DTU ID号！");
					return;
				} else if("".equals(text_port.getText().trim())) {
					MessageDialog.openError(getShell(), "错误", "请输入通讯端口！");
					return;
				} else if("".equals(text_slaveid.getText().trim())) {
					MessageDialog.openError(getShell(), "错误", "请输入仪表地址！");
					return;
				}
				
//				if(text_dtuid.getText().trim().length() != 3) {
//					MessageDialog.openError(getShell(), "错误", "请输入3位DTU ID号！");
//					return;
//				}

				try {
					int temp = Integer.valueOf(text_dtuid.getText().trim());
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
					MessageDialog.openError(getShell(), "错误", "请输入正确的DTU ID号！");
					return;
				}
				
				if(wellModel.getName() == null) {//新建
					String dtuid = text_dtuid.getText().trim();
					
					wellModel.setName("".equals(text_name.getText().trim())?null:text_name.getText().trim());
					wellModel.setNum("".equals(text_num.getText().trim())?null:text_num.getText().trim());
					wellModel.setOilDensity("".equals(text_density.getText().trim())?null:text_density.getText().trim());
					wellModel.setChouyoujiXinghao("".equals(text_chouyoujixinghao.getText().trim())?null:text_chouyoujixinghao.getText().trim());
					wellModel.setHanshui("".equals(text_hanshuiliang.getText().trim())?null:text_hanshuiliang.getText().trim());
					wellModel.setBengjing("".equals(text_bengjing.getText().trim())?null:text_bengjing.getText().trim());
					wellModel.setBengshen("".equals(text_bengshen.getText().trim())?null:text_bengshen.getText().trim());
					wellModel.setLilunchongcheng("".equals(text_chongcheng.getText().trim())?null:text_chongcheng.getText().trim());
					wellModel.setLoss("".equals(text_loss.getText().trim())?null:text_loss.getText().trim());
					
					wellModel.setDtuId("".equals(text_dtuid.getText().trim())?null:text_dtuid.getText().trim());
					wellModel.setPort("".equals(text_port.getText().trim())?null:text_port.getText().trim());
					wellModel.setSlaveId("".equals(text_slaveid.getText().trim())?null:text_slaveid.getText().trim());
					wellModel.setHeartBeat("".equals(text_heartbeat.getText().trim())?null:text_heartbeat.getText().trim());
					
					wellModel.setMobileNum("".equals(text_mobile.getText().trim())?null:text_mobile.getText().trim());
					
					
					WellModelOperate.addWell(wellModel, dtuid);
					
					
//					MainUIClass.treeViewer.add(wellModel.getParentObject(), wellModel);	//更新树
//					MainUIClass.treeViewer.setExpandedState(wellModel.getParentObject(), true);	//展开新节点
					
				} else {//编辑
					String oldNum = wellModel.getNum();
					String oldDtuId = wellModel.getDtuId();
					
					wellModel.setName("".equals(text_name.getText().trim())?null:text_name.getText().trim());
					wellModel.setNum("".equals(text_num.getText().trim())?null:text_num.getText().trim());
					wellModel.setOilDensity("".equals(text_density.getText().trim())?null:text_density.getText().trim());
					wellModel.setChouyoujiXinghao("".equals(text_chouyoujixinghao.getText().trim())?null:text_chouyoujixinghao.getText().trim());
					wellModel.setHanshui("".equals(text_hanshuiliang.getText().trim())?null:text_hanshuiliang.getText().trim());
					wellModel.setBengjing("".equals(text_bengjing.getText().trim())?null:text_bengjing.getText().trim());
					wellModel.setBengshen("".equals(text_bengshen.getText().trim())?null:text_bengshen.getText().trim());
					wellModel.setLilunchongcheng("".equals(text_chongcheng.getText().trim())?null:text_chongcheng.getText().trim());
					wellModel.setLoss("".equals(text_loss.getText().trim())?null:text_loss.getText().trim());
					
					wellModel.setDtuId("".equals(text_dtuid.getText().trim())?null:text_dtuid.getText().trim());
					wellModel.setPort("".equals(text_port.getText().trim())?null:text_port.getText().trim());
					wellModel.setSlaveId("".equals(text_slaveid.getText().trim())?null:text_slaveid.getText().trim());
					wellModel.setHeartBeat("".equals(text_heartbeat.getText().trim())?null:text_heartbeat.getText().trim());
					
					wellModel.setMobileNum("".equals(text_mobile.getText().trim())?null:text_mobile.getText().trim());
					
					WellModelOperate.updateWellByNum(oldNum, oldDtuId,wellModel);
					
//					//更新树
//					MainUIClass.treeViewer.update(wellModel, null);
				}
				
				((OperatingComposite)parent).setTop("井信息已保存！");
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
		
		initControlValues();

	}
	
	private void initControlValues() {
		if(this.wellModel.getName() != null) {
			this.text_name.setText(wellModel.getName()==null?"":wellModel.getName());
			this.text_num.setText(wellModel.getNum()==null?"":wellModel.getNum());
			this.text_density.setText(wellModel.getOilDensity()==null?"":wellModel.getOilDensity());
			this.text_chouyoujixinghao.setText(wellModel.getChouyoujiXinghao()==null?"":wellModel.getChouyoujiXinghao());
			this.text_hanshuiliang.setText(wellModel.getHanshui()==null?"":wellModel.getHanshui());
			this.text_bengjing.setText(wellModel.getBengjing()==null?"":wellModel.getBengjing());
			this.text_bengshen.setText(wellModel.getBengshen()==null?"":wellModel.getBengshen());
			this.text_chongcheng.setText(wellModel.getLilunchongcheng()==null?"":wellModel.getLilunchongcheng());
			this.text_loss.setText(wellModel.getLoss()==null?"":wellModel.getLoss());
			
			this.text_dtuid.setText(wellModel.getDtuId() == null ? "" :wellModel.getDtuId());
			this.text_port.setText(wellModel.getPort() == null ? "" :wellModel.getPort());
			this.text_slaveid.setText(wellModel.getSlaveId() == null ? "" : wellModel.getSlaveId());
			this.text_heartbeat.setText(wellModel.getHeartBeat() == null ? "" : wellModel.getHeartBeat());
			
			this.text_mobile.setText(wellModel.getMobileNum() == null?"":wellModel.getMobileNum());
//			
//			this.text_secret.setText(wellModel.getSecret() == null ? "" : wellModel.getSecret());

		}
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
