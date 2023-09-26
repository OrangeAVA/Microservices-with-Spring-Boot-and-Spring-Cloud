package com.example.demo.endpoint;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

@Component
public class SystemInfo {

	private Map<String, String> systemDetails;
	public SystemInfo() {
		// TODO Auto-generated constructor stub
		systemDetails=new LinkedHashMap<>();
		// Operating system name
		String osName = System.getProperty("os.name");
		// Operating system version
		String osVersion = System.getProperty("os.version");
		// Operating system architecture
		String osArchitecture = System.getProperty("os.arch");

		systemDetails.put("os.name", osName);
		systemDetails.put("os.version", osVersion);
		systemDetails.put("os.arch", osArchitecture);
	}

	@JsonAnyGetter
	public Map<String, String> getsystemDetails() {
		return this.systemDetails;
	}

	public void setSystemDetails(Map<String, String> systemDetails) {
		this.systemDetails = systemDetails;
		
	}

}
