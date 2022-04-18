package cn.myst.web.esjob.service;

import cn.myst.web.esjob.annotation.JobTrace;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

	@JobTrace
	public void tester(String name) {
		System.err.println("name: " + name);
	}
}
