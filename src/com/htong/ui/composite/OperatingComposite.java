package com.htong.ui.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

import com.htong.domain.IndexNodeModel;
import com.htong.domain.WellModel;

/**
 * 操作面板
 * 
 * @author 赵磊
 * 
 */
public class OperatingComposite extends Composite {
	private Composite composite;
	private TextMessageComposite textMessageComposite; // 信息提示面板
	private IndexNodeEditComposite tagsIndexNodeEditComposite; // 标签索引节点编辑面板
	private WellEditComposite wellEditComposite;	//井编辑面板


	private StackLayout stackLayout = new StackLayout();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public OperatingComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		composite = new Composite(this, SWT.NONE);

		setLayout(stackLayout);

	}

	@Override
	protected void checkSubclass() {
	}

	public void setTop(Object o) {
		if (o instanceof IndexNodeModel) {
			tagsIndexNodeEditComposite = new IndexNodeEditComposite(this,
					SWT.NONE, (IndexNodeModel) o);
			stackLayout.topControl = tagsIndexNodeEditComposite;
			this.layout();
		} else if (o instanceof String) {
			textMessageComposite = new TextMessageComposite(this, SWT.NONE,
					(String) o);
			stackLayout.topControl = textMessageComposite;
			this.layout();
		} else if(o instanceof WellModel) {
			wellEditComposite = new WellEditComposite(this, SWT.NONE, (WellModel)o);
			stackLayout.topControl = wellEditComposite;
			this.layout();
		} else {
			stackLayout.topControl = composite;
			this.layout();
		}

	} 
	public void dealWithWell(WellModel wellModel) {
		wellEditComposite = new WellEditComposite(this, SWT.NONE, wellModel);
		stackLayout.topControl = wellEditComposite;
		this.layout();
	}

}
