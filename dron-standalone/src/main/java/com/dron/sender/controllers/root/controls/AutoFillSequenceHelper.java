package com.dron.sender.controllers.root.controls;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.dron.sender.controllers.base.controls.AutoFillTextBoxBase;

public class AutoFillSequenceHelper {

	public static final List<String> getFiles(String path) {
		return getFiles(new File(path));
	}

	public static final List<String> getFiles(File file) {
		if (file.isFile()) {
			file = new File(StringUtils.substringBeforeLast(file.getPath(),
					File.separator));
		}

		return Arrays
				.stream(file.list())
				.filter(m -> StringUtils.endsWith(m,
						AutoFillTextBoxBase.FILE_END_JSON)).sorted()
				.collect(Collectors.toList());
	}
}
