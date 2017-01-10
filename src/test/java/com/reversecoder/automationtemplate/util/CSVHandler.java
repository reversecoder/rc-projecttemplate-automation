package com.reversecoder.automationtemplate.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.reversecoder.automationtemplate.dto.Dto;
import com.reversecoder.automationtemplate.dto.data.UserDto;

public class CSVHandler {
	public List<Dto> getTestData(String id, Dto testDataDto,
			ResourceType resource) {
		List<Dto> testData = new ArrayList<>();
		List<String[]> lines = readCSVFile(resource);
		if (lines.isEmpty())
			return testData;
		lines = filterTestcaseData(id, lines);
		for (String[] l : lines)
			testData.add(getTestDataDto(testDataDto, l));
		return testData;
	}

	private Dto getTestDataDto(Dto testDataDto, String[] line) {
		Field[] dtos = testDataDto.getClass().getDeclaredFields();
		List<JSONObject> dtoJson = new ArrayList<>();
		Collection<JSONObject> listDataJson = new ArrayList<JSONObject>();
		JSONObject fieldJson = new JSONObject();
		int columnCounter = 0;
		for (Field field : dtos) {
			Class<?> fieldType = field.getType();
			if (Dto.class.isAssignableFrom(fieldType)) {
				JSONObject userJson = new JSONObject();
				Field[] userFields = fieldType.getDeclaredFields();
				for (Field f : userFields) {
					userJson.put(f.getName(),
							line.length > columnCounter ? line[columnCounter]
									: "");
					columnCounter++;
				}
				dtoJson.add(userJson);
			} else if (List.class.isAssignableFrom(fieldType)) {
				ParameterizedType fieldGenericType = (ParameterizedType) field
						.getGenericType();
				Class<?> fieldTypeParameterType = (Class<?>) fieldGenericType
						.getActualTypeArguments()[0];
				Field[] dataFields = fieldTypeParameterType.getDeclaredFields();
				while (columnCounter < line.length) {
					JSONObject data = new JSONObject();
					for (Field f : dataFields) {
						data.put(
								f.getName(),
								line.length > columnCounter ? line[columnCounter]
										: "");
						columnCounter++;
					}
					listDataJson.add(data);
				}
			} else {
				fieldJson.put(field.getName(),
						line.length > columnCounter ? line[columnCounter] : "");
				columnCounter++;
			}
		}
		Class<?> fieldType = dtos[0].getType();
		JSONObject json = new JSONObject();
		if (Dto.class.isAssignableFrom(fieldType)) {
			for (Field field : dtos) {
				if (Dto.class.isAssignableFrom(field.getType())) {
					json.put(field.getName(), dtoJson.get(0));
					dtoJson.remove(0);
				} else if (List.class.isAssignableFrom(field.getType())) {
					json.put(field.getName(), listDataJson);
				}
			}
			testDataDto = new Gson().fromJson(json.toString(),
					testDataDto.getClass());
		} else {
			testDataDto = new Gson().fromJson(fieldJson.toString(),
					testDataDto.getClass());
		}
		return testDataDto;
	}

	private String getAbsoluteFileName(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	public static void main(String[] args) {
		CSVHandler csv = new CSVHandler();
		// List<Dto> dtos = csv.getTestData("21", new MyReadingsDto(),
		// ResourceType.TEST_DATA);
		// for (Dto dto : dtos) {
		// MyReadingsDto temp = (MyReadingsDto) dto;
		// System.out.println(temp.user.email);
		// System.out.println(temp.user.password);
		// System.out.println(temp.testData.size());
		// System.out.println(temp.testData.get(0).dateTime);
		// }
		List<Dto> dtos = csv.getTestData("tc10_4_7", new UserDto(),
				ResourceType.TEST_DATA);
		for (Dto dto : dtos) {
			System.out.println(dto);
		}
//		List<Dto> dtos = csv.getTestData("LI001", new LI001Dto(),
//				ResourceType.SCREEN_DATA);
//		for (Dto dto : dtos) {
//			System.out.println(dto);
//		}
	}

	private List<String[]> filterTestcaseData(String id, List<String[]> lines) {
		List<String[]> dataLine = new ArrayList<>();
		for (String[] l : lines)
			if (!isCommentLine(l) && isTestcaseLine(id, l))
				dataLine.add(l);
		return dataLine;
	}

	private boolean isTestcaseLine(String id, String[] line) {
		return !StringUtils.isBlank(line[0]) && (line[0].compareTo(id) == 0);
	}

	private boolean isCommentLine(String[] line) {
		return Pattern.matches("^[ \t]*#.*$", line[0])
				|| StringUtils.isBlank(line[0]);
	}

	private List<String[]> readCSVFile(ResourceType resource) {
		List<String[]> allRows = new ArrayList<>();
		CSVReader reader = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(
					getAbsoluteFileName(Constants.RESOURCE_BASE_DIR
							+ resource.getResource())), "UTF-8");
			reader = new CSVReader(inputStreamReader);
			allRows = reader.readAll();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
		return allRows;
	}

	public static enum ResourceType {
		TEST_DATA("TestData.csv"), SCREEN_DATA("ScreenData.csv"), PAGE_DATA("PageData.csv");

		public final String resource;

		ResourceType(String resource) {
			this.resource = resource;
		}

		String getResource() {
			return resource;
		}
	}
}
