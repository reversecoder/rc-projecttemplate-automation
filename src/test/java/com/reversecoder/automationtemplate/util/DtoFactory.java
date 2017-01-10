package com.reversecoder.automationtemplate.util;

import java.lang.reflect.Field;

import com.reversecoder.automationtemplate.dto.Dto;
import com.reversecoder.automationtemplate.util.CSVHandler.ResourceType;

public class DtoFactory {

	public static <T> void initScreenDto(T screen) {
		try {
			Field[] fields = screen.getClass().getDeclaredFields();
			for (Field field : fields) {
				Class<?> fieldType = field.getType();
				if (Dto.class.isAssignableFrom(fieldType)) {
					Class<?> c = Class.forName(field.getType()
							.getCanonicalName());
					Dto screenDto = new CSVHandler().getTestData(
							field.getName().toUpperCase(),
							(Dto) c.newInstance(), ResourceType.SCREEN_DATA)
							.get(0);
					field.set(screen, screenDto);
				}
			}
		} catch (Exception e) {
		}
	}
	
	public static <T> void initPageDto(T page) {
		try {
			Field[] fields = page.getClass().getDeclaredFields();
			for (Field field : fields) {
				Class<?> fieldType = field.getType();
				if (Dto.class.isAssignableFrom(fieldType)) {
					Class<?> c = Class.forName(field.getType()
							.getCanonicalName());
					Dto pageDto = new CSVHandler().getTestData(
							field.getName().toUpperCase(),
							(Dto) c.newInstance(), ResourceType.PAGE_DATA)
							.get(0);
					field.set(page, pageDto);
				}
			}
		} catch (Exception e) {
		}
	}
}
