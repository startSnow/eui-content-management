package com.leeco.eui.api.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class CommonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	 
	private String token = "5GGxXlgAhNvzn2PQWe";

	public String generateMD5Hash(String ts) throws UnsupportedEncodingException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Error while generating message digest",e);
			throw new UnsupportedEncodingException("Error while generating message digest");
		}
		LOGGER.debug("ts = " + ts);
		Long timestamp = Long.parseLong(ts.trim());
		byte[] thedigest = md.digest((token + timestamp).getBytes("UTF-8"));
		StringBuffer sb = new StringBuffer();
		for (byte b : thedigest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		LOGGER.debug(" Generated Sign " + sb.toString());
		return sb.toString();
	}
}
