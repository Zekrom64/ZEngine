package com.zekrom_64.ze.base;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.backend.render.ZERenderContext;
import com.zekrom_64.ze.base.err.ZEngineInternalException;

/** An application class provides a foundation for programs to use the engine. It
 * is not required to access the other classes but removes the need for some
 * boiler plate code.
 * 
 * @author Zekrom_64
 *
 */
public abstract class ZEApplication {
	
	private static ZEApplication mainApplication = null;
	private static List<WeakReference<ZEApplication>> runningApplications = new ArrayList<>();
	
	public static ZEApplication getMainApplication() {
		return mainApplication;
	}
	
	public static List<WeakReference<ZEApplication>> getRunningApplications() {
		return Collections.unmodifiableList(runningApplications);
	}
	
	public static class ZEApplicationInfo {
		public final String applicationName;
		public final String engineName;
		public final ZERenderBackend<?> renderBackend;
		public final ZERenderContext<?> renderOutput;
		
		public ZEApplicationInfo(
				String appName,
				String engName,
				ZERenderBackend<?> renderBack,
				ZERenderContext<?> renderOut
		) {
			applicationName = appName;
			engineName = engName;
			renderBackend = renderBack;
			renderOutput = renderOut;
		}
	}
	
	public final ZEApplicationInfo appinfo;
	
	public ZEApplication(ZEApplicationInfo appinfo) {
		this.appinfo = appinfo;
		if (mainApplication==null) mainApplication = this;
		runningApplications.add(new WeakReference<ZEApplication>(this));
	}
	
	public void onInternalException(ZEngineInternalException exception) { }
	
}
