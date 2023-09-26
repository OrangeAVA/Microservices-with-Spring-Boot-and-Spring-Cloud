package com.example.demo.endpoint;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "system-endpoint")
public class MyCustomEndpoint {

	@Autowired
	SystemInfo systemInfo;

	@ReadOperation
	public SystemInfo system() {
		return systemInfo;
	}

	@ReadOperation
	public String readSystemInfoByName(@Selector String name) {
		String info = systemInfo.getsystemDetails().get(name);
		return info;
	}

	
	@WriteOperation
	public SystemInfo updateSystemInfo_statusOK(String name) {
		// perform write operation
		System.out.println(name);
		if (name.equals("processors")) {
			System.out.println("adding extra info");
			Map<String, String> details = systemInfo.getsystemDetails();
			details.put(name, Runtime.getRuntime().availableProcessors() + "");
			systemInfo.setSystemDetails(details);
		}
		return systemInfo;
	}
	
//	@WriteOperation
//	public SystemInfo updateSystemInfo_statusNoContent(String name) {
//		// perform write operation
//		System.out.println(name);
//		if (name.equals("processors")) {
//			System.out.println("adding extra info");
//			Map<String, String> details = systemInfo.getsystemDetails();
//			details.put(name, Runtime.getRuntime().availableProcessors() + "");
//			systemInfo.setSystemDetails(details);
//		}
//		return systemInfo;
//
//	}

//	@DeleteOperation
//	public void modifySystemInfo__pathVariable(@Selector String name) {
//		// delete operation
//		systemInfo.getsystemDetails().remove(name);
//	}

	@DeleteOperation
	public void modifySystemInfo_queryParam(String name) {
		// delete operation
		systemInfo.getsystemDetails().remove(name);
	}

}
