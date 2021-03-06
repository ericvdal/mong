package ebook.persistance.mongo.order;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.mongodb.DBObject;

public class SortingMongoEventListener extends AbstractMongoEventListener<Object> {
	@Override
	  public void onAfterConvert(DBObject dbo, final Object source) {
	      ReflectionUtils.doWithFields(source.getClass(), new SortingFieldCallback(source, SortPhase.AFTER_CONVERT));
	  }

	  @Override
	  public void onBeforeConvert(Object source) {
	      ReflectionUtils.doWithFields(source.getClass(), new SortingFieldCallback(source, SortPhase.BEFORE_CONVERT));
	  }

	  /**
	   * Performs sorting with field if:
	   * - field is an instance of list
	   * - is annotated with OrderBy annotation
	   *
	   * OrderBy annotation is set to run in same phase as SortingFieldCallback
	   */
	   private static class SortingFieldCallback implements ReflectionUtils.FieldCallback {
	       private Object source;
	       private SortPhase phase;

	       private SortingFieldCallback(Object source, SortPhase phase) {
	           this.source = source;
	           this.phase = phase;
	       }

	       public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
	           if (field.isAnnotationPresent(OrderBy.class)) {
	               OrderBy orderBy = field.getAnnotation(OrderBy.class);

	               if (Arrays.asList(orderBy.phase()).contains(phase)) {
	                   ReflectionUtils.makeAccessible(field);
	                   Object fieldValue = field.get(source);

	                   sort(fieldValue, orderBy);
	               }
	           }
	       }

	       private void sort(Object fieldValue, OrderBy orderBy) {
	           if (ClassUtils.isAssignable(List.class, fieldValue.getClass())) {
	               final List list = (List) fieldValue;

	               if (orderBy.order() == Sort.Direction.ASC) {
	                   Collections.sort(list, new BeanComparator(orderBy.value()));
	               } else {
	                   Collections.sort(list, new BeanComparator(orderBy.value(), Collections.reverseOrder()));
	               }
	           }

	       }
	   }	  
	  
}
