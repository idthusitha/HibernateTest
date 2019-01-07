package com.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.pojo.BasePojo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommonUtil {
	public static CommonUtil commonUtil = null;

	private CommonUtil() {
	}

	public static CommonUtil getInstance() {
		if (commonUtil == null) {
			commonUtil = new CommonUtil();
		}
		return commonUtil;
	}

	public JSONObject getJsonData(Object model, HttpServletResponse response) throws Exception {
		Class[] parameterTypes = {};
		Object[] parameterValues = {};
		Field[] fields = model.getClass().getDeclaredFields();

		// HashMap<String, Object> dataMap = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class type = field.getType();
			try {
				if (type == java.util.Date.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? (java.util.Date) method.invoke(model, parameterValues) : "");

				} else if (type == Integer.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					String newFieldName = fieldName.equals("id") ? getClassName(model.getClass()) + "_" + fieldName : fieldName;
					json.accumulate(newFieldName, model != null ? Integer.toString((Integer) method.invoke(model, parameterValues)) : "");

				} else if (type == String.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? method.invoke(model, parameterValues) != null ? method.invoke(model, parameterValues).toString() : "" : "");

				} else if (type == Number.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? method.invoke(model, parameterValues) != null ? method.invoke(model, parameterValues).toString() : "" : "");

				} else if (BasePojo.class == type.getSuperclass()) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					Class subClass = method.getReturnType();

					Object subModel = (Object) method.invoke(model, parameterValues);

					JSONObject subJson = setSubClassFildValues(subClass, subModel);
					JSONArray subArray = new JSONArray();
					subArray.add(subJson);
					// System.out.println("SUB==>"+getClassName(subModel.getClass()) + "==>" + subJson.toString());
					json.put(getClassName(subModel.getClass()), subArray.toString());

				} else if (Set.class == type) {
					// System.out.println("type==>" + type);
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					Class subClass = method.getReturnType();
					// System.out.println("subClass==>" + subClass+" method.getName()===>"+method.getName());

					Set subModel = new HashSet((Set) method.invoke(model, parameterValues));
					JSONArray subArray = new JSONArray();

					String className = "";
					for (Object object : subModel) {
						JSONObject subJson = setSubClassFildValues(object.getClass(), object);
						subArray.add(subJson);
						className = getClassName(object.getClass());
						// System.out.println("SUB==>"+getClassName(object.getClass()) + "==>" + subJson.toString());
					}
					json.put(className, subArray.toString());

				} else {
					// System.out.println("getJsonData type.getSuperclass()"+type.getSuperclass());

					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? method.invoke(model, parameterValues) != null ? method.invoke(model, parameterValues).toString() : "" : "");
				}
			} catch (Exception e) {
				System.out.println("ERROR==>" + e.getMessage());
				// e.printStackTrace();
			}
		}

		return json;
	}

	private JSONObject setSubClassFildValues(Class subClass, Object model) {
		Class[] parameterTypes = {};
		Object[] parameterValues = {};
		Field[] fields = subClass.getDeclaredFields();

		JSONObject json = new JSONObject();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class type = field.getType();

			try {
				if (type == java.util.Date.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? (java.util.Date) method.invoke(model, parameterValues) : "");

				} else if (type == Integer.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					String newFieldName = fieldName.equals("id") ? getClassName(model.getClass()) + "_" + fieldName : fieldName;
					json.accumulate(newFieldName, model != null ? Integer.toString((Integer) method.invoke(model, parameterValues)) : "");

				} else if (type == String.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? method.invoke(model, parameterValues) != null ? method.invoke(model, parameterValues).toString() : "" : "");

				} else if (type == Number.class) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? method.invoke(model, parameterValues) != null ? method.invoke(model, parameterValues).toString() : "" : "");

				} else if (BasePojo.class == type.getSuperclass()) {
					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					Class subClassTemp = method.getReturnType();

					Object subModel = (Object) method.invoke(model, parameterValues);

					JSONObject subJson = setSubClassFildValues(subClassTemp, subModel);
					JSONArray subArray = new JSONArray();
					subArray.add(subJson);
					// System.out.println("SUB==>"+getClassName(subModel.getClass()) + "==>" + subJson.toString());
					json.put(getClassName(subModel.getClass()), subArray.toString());

				} else {
					// System.out.println("setSubClassFildValues type.getSuperclass()"+type.getSuperclass());

					Method method = model.getClass().getDeclaredMethod("get" + StringUtils.capitalize(fieldName), parameterTypes);
					json.accumulate(fieldName, model != null ? method.invoke(model, parameterValues) != null ? method.invoke(model, parameterValues).toString() : "" : "");
				}
			} catch (Exception e) {
				System.out.println("ERROR==>" + e.getMessage());
				// e.printStackTrace();
			}
		}
		return json;
	}

	private String getClassName(Class classObject) {
		String className = classObject.getSimpleName();
		if (className.contains("_$")) {
			className = className.substring(0, className.indexOf("_$"));
		}
		return className;
	}
}
