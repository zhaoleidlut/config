package com.htong.ui.login;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.springframework.data.document.mongodb.query.Criteria;
import org.springframework.data.document.mongodb.query.Query;

import com.htong.domain.User;
import com.htong.persist.PersistManager;
import com.htong.util.CollectionConstants;
import org.eclipse.wb.swt.ResourceManager;

public class LoginDialog extends Dialog {
	private Text text;
	private Text text_1;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public LoginDialog(Shell parentShell) {
		
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 3;
		new Label(container, SWT.NONE);
		
		Label label_1 = new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("     ");
		
		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		label.setText("用户名：");
		
		text = new Text(container, SWT.BORDER);

		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 100;
		text.setLayoutData(gd_text);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("密码：");
		
		text_1 = new Text(container, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_1.widthHint = 100;
		text_1.setLayoutData(gd_text_1);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.NO_ID, IDialogConstants.OK_LABEL,
				true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if("".equals(text.getText().trim()) || "".equals(text_1.getText().trim())) {
					MessageDialog.openWarning(getShell(), "不能为空", "用户名或密码不能为空！");
					return;
				}
				
				String username = text.getText().trim();
				String password = text_1.getText().trim();
				
				Query query = new Query(Criteria.where("username").is(username));
				User user = PersistManager.INSTANCE.getMongoTemplate().findOne(CollectionConstants.USER, query, User.class);

				if(user == null || !user.getPassword().equals(password)) {
					MessageDialog.openWarning(getShell(), "错误", "用户名或密码错误！");
					return;
				}
				close();
			}
		});
		button.setText("登录");
		Button button_1 = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		button_1.setText("取消");
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(258, 172);
	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setImage(ResourceManager.getPluginImage("config", "icons/huatong.ico"));
		newShell.setText("用户登录");

		super.configureShell(newShell);
	}

	
	


}
