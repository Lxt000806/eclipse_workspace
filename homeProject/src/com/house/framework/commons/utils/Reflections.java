package com.house.framework.commons.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.sun.jmx.snmp.Timestamp;

/**
 * 
 * @ClassName: Reflections 
 * @Description: TODO( 反射工具类.
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.) 
 *
 */
public class Reflections {

	private static Logger logger = LoggerFactory.getLogger(Reflections.class);

	static {
		DateConverter dc = new DateConverter(null);
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value) {
		invokeSetterMethod(target, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			if(logger.isDebugEnabled()){
				logger.error("设置对象属性值异常" + e.getMessage() + ", [ReflectionUtils.setFieldValue]", e);
			}
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {// NOSONAR
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强行设置Field可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射, 获得定义Class时声明的父类的泛型参数的类型.
	 * 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection 来源集合.
	 * @param propertyName 要提取的属性名.
	 * @param separator 分隔符.
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串
	 * @param toType 转换目标类型
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
	
	
	/**
	 * 通过反射将对象内所有字符串属性值 进行trim() 
	 * @param obj
	 * @return
	 */
	public static Object trim(Object obj){
		return trim(obj, null);
	}
	
	/**
	 * 通过反射将对象内所有字符串属性值 进行trim() 
	 * 暂时无法提供父对象属性的 trim(); 原因是未找到 子对象获取父对象
	 * @param obj
	 * @param escapeList 不需要trim()的属性列表
	 * @return
	 */
	public static Object trim(Object obj, List<String> escapeList){
		if(obj == null)
			return null;
		try {
			//for(Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass(),obj = obj.super){
			Field[] fields =  obj.getClass().getFields();
			if(fields != null && fields.length > 0){
				for(Field field : fields){
					if(field.getType().toString().equals("class java.lang.String")){
						Object val = FieldUtils.readField(field, obj, true);
						if(val != null){
							if(escapeList != null && escapeList.indexOf(field.getName()) != -1)
								continue;
							FieldUtils.writeField(field, obj, val.toString().trim(), true);
						}
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	/**
	 * 实例化类
	 * @param className
	 * @param args
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object newInstance(String className, Object[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class newoneClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = newoneClass.getConstructor(argsClass);
		return cons.newInstance(args);
	} 
	
	/**
	 * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个list分别存obj1,obj2此属性名的值
	 * @param obj1 进行属性比较的对象1
	 * @param obj2 进行属性比较的对象2
	 * @param ignoreArr 选择忽略比较的属性数组
	 * @return 属性差异比较结果map
	 */
	public static Map<String, List<Object>> compareFields(Object obj1, Object obj2, String[] ignoreArr) {
		try{
			Map<String, List<Object>> map = new HashMap<String, List<Object>>();
			List<String> ignoreList = null;
			if(ignoreArr != null && ignoreArr.length > 0){
				// array转化为list
				ignoreList = Arrays.asList(ignoreArr);
			}
			if (obj1.getClass() == obj2.getClass()) {// 只有两个对象都是同一类型的才有可比性
				Class clazz = obj1.getClass();
				// 获取object的属性描述
				PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz,
						Object.class).getPropertyDescriptors();
				for (PropertyDescriptor pd : pds) {// 这里就是所有的属性了
					String name = pd.getName();// 属性名
					if(ignoreList != null && ignoreList.contains(name)){// 如果当前属性选择忽略比较，跳到下一次循环
						continue;
					}
					Method readMethod = pd.getReadMethod();// get方法
					// 在obj1上调用get方法等同于获得obj1的属性值
					Object o1 = readMethod.invoke(obj1)==null?"":readMethod.invoke(obj1);
					// 在obj2上调用get方法等同于获得obj2的属性值
					Object o2 = readMethod.invoke(obj2)==null?"":readMethod.invoke(obj2);
					if(o1 instanceof Timestamp){
						o1 = new Date(((Timestamp) o1).getDateTime());
					}
					if(o2 instanceof Timestamp){
						o2 = new Date(((Timestamp) o2).getDateTime());
					}
					if(o1 == null && o2 == null){
						continue;
					}
					if (!o1.toString().trim().equals(o2.toString().trim())) {// 比较这两个值是否相等,不等就可以放入map了
						List<Object> list = new ArrayList<Object>();
						list.add(o1);
						list.add(o2);
						map.put(name, list);
					}
				}
			}
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
