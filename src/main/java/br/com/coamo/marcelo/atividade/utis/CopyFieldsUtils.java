package br.com.coamo.marcelo.atividade.utis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CopyFieldsUtils {

	public static void copyAtoB(Object a, Object b) {

		if (a == null || b == null) {
			return;
		}

		Collection<Field> fieldsA = getAllFields(a.getClass());
		Collection<Field> fieldsB = getAllFields(b.getClass());

		fieldsA.forEach((fieldA) -> {
			fieldsB.stream().filter((fieldB) -> (fieldA.getName().equals(fieldB.getName())))
					.forEachOrdered((fieldB) -> {
						try {
							fieldA.setAccessible(true);
							fieldB.setAccessible(true);

							Object objA = fieldA.get(a);
							Object objB = fieldB.get(b);

							if (objB == null) {
								fieldB.set(b, objA);
							}

//	                    if (fieldB.isAnnotationPresent(Ac.class)) {
//	                        Ac ac = fieldB.getDeclaredAnnotation(Ac.class);
//	                        container.getAcBuilder(ac.ref()).updateComponent(objA);
//	                    }
						} catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
							e.printStackTrace(System.err);
						}
					});
		});
	}

	private static Collection<Field> getAllFields(Class<?> type) {

		Collection<Field> ret = new ArrayList<>();
		while (type != Object.class) {
			ret.addAll(Arrays.asList(type.getDeclaredFields()));
			type = type.getSuperclass();
		}
		return ret;
	}

}
