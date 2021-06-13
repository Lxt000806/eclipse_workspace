package com.house.framework.commons.conf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DefaultPropertiesPersister;
import com.house.framework.commons.utils.DesUtils;

public class DbPropertiesPersist extends DefaultPropertiesPersister {
	
	private String key = "jdbc.connection.password";

	public void load(Properties props, InputStream is) throws IOException {

		Properties properties = new Properties();
		properties.load(is);
		String pwd = (String) properties.get(key);
		if (StringUtils.isNotBlank(pwd)) {
			properties.setProperty(key, DesUtils.decode(pwd));
		}
		OutputStream outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			properties.store(outputStream, "");
			is = outStream2InputStream(outputStream);
			super.load(props, is);
		} catch (IOException e) {
			throw e;
		} finally {
			outputStream.close();
		}
	}

	private InputStream outStream2InputStream(OutputStream out) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(
				bos.toByteArray());
		return swapStream;
	}
}
