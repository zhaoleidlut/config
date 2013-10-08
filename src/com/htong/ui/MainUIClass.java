package com.htong.ui;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.htong.domain.IndexNodeModel;
import com.htong.domain.WellModel;
import com.htong.init.TagDataBase;
import com.htong.persist.IndexNodeModelOperate;
import com.htong.persist.UpdateConfig;
import com.htong.persist.WellModelOperate;
import com.htong.ui.composite.OperatingComposite;
import com.htong.ui.tree.RootTreeModel;
import com.htong.ui.tree.TreeContentProvider;
import com.htong.ui.tree.TreeLabelProvider;
import com.htong.ui.util.LayoutUtil;
import com.htong.ui.util.PinyinComparator;
import com.swtdesigner.ResourceManager;

public class MainUIClass {
	
	private Image add_sub_icon = ResourceManager.getPluginImage("config",  "icons/add_sub.jpg");
	private Image add_well_icon = ResourceManager.getPluginImage("config", "icons/add_well.jpg");
	private Image del_well_icon = ResourceManager.getPluginImage("config", "icons/del_well.jpg");
	private Image del_label_icon = ResourceManager.getPluginImage("config", "icons/del_label.jpg");
	private Image edit_icon = ResourceManager.getPluginImage("config", "icons/edit.jpg");

	private final static Logger log = LoggerFactory
			.getLogger(MainUIClass.class);

	protected Shell shell;
	private MenuManager menuMng;
	public static TreeViewer treeViewer;
	private OperatingComposite operatingComposite; // 操作面板
	
	private class Sorter extends ViewerSorter {
		public int compare(Viewer viewer, Object e1, Object e2) {
			if(e1 instanceof WellModel && e2 instanceof WellModel) {
				WellModel w1 = (WellModel)e1;
				WellModel w2 = (WellModel)e2;
				return PinyinComparator.INSTANCE.compare(w1.getNum(), w2.getNum());
			}
			return 0;
		}
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TagDataBase.INSTANCE.init();

			MainUIClass window = new MainUIClass();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MainUIClass() {
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(692, 524);
		shell.setText("胜利油田油井监测管理系统配置软件3.2 2012-9-21");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setImage(ResourceManager.getPluginImage("config", "icons/huatong.ico"));

		// 窗口居中
		LayoutUtil.centerShell(Display.getCurrent(), shell);

		final Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		menuItem.setText("帮助");

		SashForm sashForm = new SashForm(shell, SWT.BORDER | SWT.SMOOTH);
		sashForm.setSashWidth(1);

		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		treeViewer = new TreeViewer(composite, SWT.BORDER);
		treeViewer.setAutoExpandLevel(TreeViewer.ALL_LEVELS);
		treeViewer.setSorter(new Sorter());
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setInput(new RootTreeModel());

		Tree tree = treeViewer.getTree();

		menuMng = new MenuManager();
		menuMng.setRemoveAllWhenShown(true);
		menuMng.addMenuListener(new MenuListener(treeViewer));

		tree.setMenu(menuMng.createContextMenu(tree)); // 添加菜单

		// 点击打开编辑页面
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 1) {
					IStructuredSelection sel = ((IStructuredSelection) treeViewer
							.getSelection());
					if (!sel.isEmpty()) {
						final Object obj = ((IStructuredSelection) treeViewer
								.getSelection()).getFirstElement();
						Display.getDefault().timerExec(
								Display.getDefault().getDoubleClickTime(),
								new Runnable() {
									public void run() {
										edit(obj);
									}
								});
					}

				}
			}

		});

		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		GridLayout gl_composite_1 = new GridLayout(1, false);
		gl_composite_1.marginTop = 10;
		gl_composite_1.marginLeft = 10;
		composite_1.setLayout(gl_composite_1);

		operatingComposite = new OperatingComposite(composite_1, SWT.NONE);
		operatingComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		sashForm.setWeights(new int[] { 192, 325 });

	}

	private class MenuListener implements IMenuListener {
		private TreeViewer treeViewer;

		public MenuListener(TreeViewer treeViewer) {
			this.treeViewer = treeViewer;
		}

		@Override
		public void menuAboutToShow(IMenuManager manager) {
			IStructuredSelection selection = (IStructuredSelection) treeViewer
					.getSelection();
			if (!selection.isEmpty()) {
				createContextMenu(selection.getFirstElement());
			}
		}

		/**
		 * 右键菜单内容
		 * 
		 * @param
		 */
		private void createContextMenu(final Object selectedObject) {
			if (selectedObject instanceof String) {
				final String str = (String) selectedObject;

				if (str.equals(RootTreeModel.getRoottree()[0])) {// 标签索引配置
					// 新建标签索引
					Action add = new Action() {
						public void run() {
							IndexNodeModel inm = new IndexNodeModel();
							inm.setParentObject(selectedObject);
							operatingComposite.setTop(inm);
						}
					};
					add.setText("新建标签(&A)");
					add.setImageDescriptor(ImageDescriptor.createFromImage(add_sub_icon));
					menuMng.add(add);
				}

			}
			if (selectedObject instanceof IndexNodeModel) { // 标签索引节点
				final IndexNodeModel indexNodeModel = (IndexNodeModel) selectedObject;
				
				List<WellModel> wellList = WellModelOperate.getWellByPath(IndexNodeModelOperate.getPath(indexNodeModel));
				if(wellList == null || wellList.isEmpty()) {
					// 新建标签索引
					Action add = new Action() {
						public void run() {
							IndexNodeModel inm = new IndexNodeModel();
							inm.setParentObject(selectedObject);
							operatingComposite.setTop(inm);
						}
					};
					add.setText("新建下级标签(&A)");
					add.setImageDescriptor(ImageDescriptor.createFromImage(add_sub_icon));
					menuMng.add(add);
				}
				

				if (indexNodeModel.getIndexNodes() == null
						|| indexNodeModel.getIndexNodes().isEmpty()) {
					// 新建井
					Action addWell = new Action() {
						public void run() {
							WellModel wellModel = new WellModel();
							wellModel.setPath(IndexNodeModelOperate
									.getPath(indexNodeModel));
							operatingComposite.setTop(wellModel);
						}
					};
					addWell.setText("新建井(&A)");
					addWell.setImageDescriptor(ImageDescriptor.createFromImage(add_well_icon));
					menuMng.add(addWell);

				}

				// 修改标签
				Action edit = new Action() {
					public void run() {
						// 修改标签
						operatingComposite
								.setTop((IndexNodeModel) selectedObject);
					}
				};
				edit.setImageDescriptor(ImageDescriptor.createFromImage(edit_icon));
				edit.setText("修改标签(&E)");
				menuMng.add(edit);

				// 删除标签
				Action del = new Action() {
					public void run() {
						// 删除标签
						if (MessageDialog.openConfirm(treeViewer.getTree()
								.getShell(), "删除", "井信息也将删除，确认要删除吗？")) {

							IndexNodeModelOperate
									.removeIndexNode(indexNodeModel);

//							treeViewer.remove(indexNodeModel);

						}

					}
				};
				del.setText("删除标签(&D)");
				del.setImageDescriptor(ImageDescriptor.createFromImage(del_label_icon));
				menuMng.add(del);

			} else if(selectedObject instanceof WellModel) {
				// 修改标签
				Action edit = new Action() {
					public void run() {
						// 修改标签
						operatingComposite
								.setTop((WellModel) selectedObject);
					}
				};
				edit.setText("修改井(&E)");
				edit.setImageDescriptor(ImageDescriptor.createFromImage(edit_icon));
				menuMng.add(edit);

				// 删除标签
				Action delp = new Action() {
					public void run() {
						// 删除标签
						if (MessageDialog.openConfirm(treeViewer.getTree()
								.getShell(), "删除", "确认要删除该井吗？")) {

							WellModelOperate.deleteWellByNum(((WellModel) selectedObject).getNum(),((WellModel) selectedObject).getDtuId(), (WellModel) selectedObject);
							
							UpdateConfig.updateConfigToDatabase();

						}

					}
				};
				delp.setText("删除井(&D)");
				delp.setImageDescriptor(ImageDescriptor.createFromImage(del_well_icon));
				menuMng.add(delp);
			}
		}
	}

	/**
	 * 编辑
	 * 
	 * @param object
	 */
	private void edit(Object object) {
		 if(object instanceof WellModel) {
		 operatingComposite.dealWithWell((WellModel)object);
		 } else if(object instanceof IndexNodeModel) {
		 operatingComposite.setTop((IndexNodeModel)object);
		 }

	}


}
