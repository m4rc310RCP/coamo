package br.com.coamo.marcelo.atividade.services;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.coamo.marcelo.atividade.utis.CopyFieldsUtils;

public class MService {
	protected <T> void cloneObject(String sfield, String jpaMethod, JpaRepository<T, ?> repository, T value) {
		try {
			Class<? extends Object> type = value.getClass();
			Field field = type.getDeclaredField(sfield);
			field.setAccessible(true);

			Object obj = field.get(value);

			if (obj == null) {
				return;
			}

			Class<?> repositoryType = repository.getClass();

//			Method method = repositoryType.getDeclaredMethod(jpaMethod, obj.getClass());
			Method method = getMethod(repositoryType, jpaMethod);
			method.setAccessible(true);

			Optional<?> optional = (Optional<?>) method.invoke(repository, obj);
			if (optional.isPresent()) {
				CopyFieldsUtils.copyAtoB(optional.get(), value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Method getMethod(Class<?> type, String name) {
		List<Method> methods = new ArrayList<>();
		methods.addAll(Arrays.asList(type.getDeclaredMethods()));
		methods.addAll(Arrays.asList(type.getMethods()));
		for (Method method : methods) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new UnsupportedOperationException("Method not found");
	}

}
