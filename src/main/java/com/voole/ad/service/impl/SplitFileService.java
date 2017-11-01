package com.voole.ad.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;

/**
 * @author Administrator 和脚本version7一起使用,只处理.f(脚本处理最终文件)结尾的文件
 *         20150717121314.txt.f 每个方法处理不同type目录下的文件
 */
@Component("splitFileService")
public class SplitFileService {

	private Logger log = Logger.getLogger(SplitFileService.class);

	private void split(String bigtype) {
		try {
			String[] path = { GlobalProperties.getProperties("splitFileSh"), bigtype, GlobalProperties.getProperties("backupOSplitFolder") };
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("splitFile error:" + e.getMessage());
		}
	}

	public void splitFileSh1() {
		split("1");
	}

	public void splitFileSh2() {
		split("2");
	}

	public void splitFileSh3() {
		split("3");
	}

	public void splitFileSh4() {
		split("4");
	}

	public void splitFileSh5() {
		split("5");
	}

	public void splitFileSh6() {
		split("6");
	}

	public void splitFileSh7() {
		split("7");
	}

	public void splitFileSh8() {
		split("8");
	}
}
