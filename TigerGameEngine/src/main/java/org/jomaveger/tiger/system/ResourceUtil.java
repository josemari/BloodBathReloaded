package org.jomaveger.tiger.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.jomaveger.tiger.util.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResourceUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ResourceUtil.class);
	
	private ResourceUtil() {		
	}
	
	public static String loadResource(String fileName) {
		String result;
		try (InputStream in = Class.forName(ResourceUtil.class.getName()).getResourceAsStream(fileName);
				Scanner scanner = new Scanner(in, "UTF-8")) {
			result = scanner.useDelimiter("\\A").next();
		} catch (ClassNotFoundException e) {
			LOG.error(ExceptionUtils.getExpandedMessage(e));
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOG.error(ExceptionUtils.getExpandedMessage(e));
			throw new RuntimeException(e);
		}
		return result;
	}

}
