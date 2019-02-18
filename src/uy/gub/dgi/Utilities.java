package uy.gub.dgi;

import org.apache.commons.io.FilenameUtils;

import com.genexus.ModelContext;
import com.genexus.internet.HttpContext;
import com.genexus.webpanels.HttpContextWeb;

public class Utilities {

	public static String getRealPath(String filename) {
		String file = filename;
		if (ModelContext.getModelContext() != null) {
			HttpContext webContext = ModelContext.getModelContext()
					.getHttpContext();
			if ((webContext != null) && (webContext instanceof HttpContextWeb)) {
				file = ((HttpContextWeb) webContext).getRealPath(filename);
			}
		}
		return FilenameUtils.separatorsToSystem(file);
	}

	public static String getContextPath() {
		String contextPath = "";
		if (ModelContext.getModelContext() != null) {
			HttpContext webContext = ModelContext.getModelContext()
					.getHttpContext();
			if ((webContext != null) && (webContext instanceof HttpContextWeb)) {
				contextPath = ((HttpContextWeb) webContext).getDefaultPath()
						+ "\\";
			}
		}
		return FilenameUtils.separatorsToSystem(contextPath);
	}
}
