package com.feyfey.method;

import java.io.File;

public class StringTest {

	public static void main(String[] args) {
		String path = "F://test2/css/Œ¢–≈Õº∆¨_20180104170221.jpg";
		String a = path.substring(0, path.lastIndexOf("/"));
		System.out.println(a);
	}

}