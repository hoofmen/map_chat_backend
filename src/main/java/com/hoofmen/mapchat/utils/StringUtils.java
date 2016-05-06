package com.hoofmen.mapchat.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StringUtils {

	/**
	 * prints a class name and its fields=values
	 * @param obj the object you want to print e.g. StringUtils.toString(this,false);
	 * @param printSupperClass whether you want to print the superclass's content also
	 * @return
	 */
	public static String toString(Object obj, boolean printSupperClass) {
		
		if (obj == null)
			return "null";
		
		StringBuffer sb = new StringBuffer();
		
		Field[] fields;
		if (printSupperClass){
			sb.append(obj.getClass().getSuperclass().getSimpleName());
			sb.append(" ");
			fields = obj.getClass().getSuperclass().getDeclaredFields();
			sb.append(printFields(obj, fields));
		}
		sb.append(obj.getClass().getSimpleName());
		sb.append(" ");
		fields = obj.getClass().getDeclaredFields();
		sb.append(printFields(obj, fields));
		
		return sb.toString();
	}
	
	private static String printFields(Object obj, Field[] fields){
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				f.setAccessible(true);
				Object value;
				try {
					value = f.get(obj);
					buffer.append(f.getName());
					buffer.append("=");
					buffer.append("" + value);
					buffer.append(", ");
				} catch (IllegalAccessException ex) {
					System.out.println(ex.toString());
				}
			}
		}
		if (fields.length != 0)
			buffer.setLength(buffer.length()-2);
		buffer.append("}");
		return buffer.toString();
	}
}
