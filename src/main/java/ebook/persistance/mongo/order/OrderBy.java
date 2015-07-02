package ebook.persistance.mongo.order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.domain.Sort;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface OrderBy {

	 /**
	  * Field name
	  */
	  String value();
	  Sort.Direction order() default Sort.Direction.ASC;
	  SortPhase[] phase() default SortPhase.AFTER_CONVERT;
	
}
