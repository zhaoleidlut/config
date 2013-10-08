package config;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.htong.init.TagDataBase;
import com.htong.ui.MainUIClass;
import com.htong.ui.login.LoginDialog;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
//		Display display = PlatformUI.createDisplay();
//		try {
//			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
//			if (returnCode == PlatformUI.RETURN_RESTART)
//				return IApplication.EXIT_RESTART;
//			else
//				return IApplication.EXIT_OK;
//		} finally {
//			display.dispose();
//		}
		
		LoginDialog login = new LoginDialog(null);
		if(login.open() == Window.OK) {
			TagDataBase.INSTANCE.init();
			MainUIClass m = new MainUIClass();
			m.open();
			
			this.stop();
		}
		
		return IApplication.EXIT_OK;
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
