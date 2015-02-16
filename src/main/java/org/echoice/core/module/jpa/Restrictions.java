package org.echoice.core.module.jpa;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.echoice.core.module.jpa.Criterion.Operator;
import org.hibernate.criterion.MatchMode;

/**
 * ����������
 * ���ڴ����������ʽ
 * @Class Name Restrictions
 * @Author 
 */
public class Restrictions {

	/**
	 * ����
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
		return new SimpleExpression (fieldName, value, Operator.EQ);
	}
	
	/**
	 * ������
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
		return new SimpleExpression (fieldName, value, Operator.NE);
	}

	/**
	 * ģ��ƥ��
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
		if(StringUtils.isEmpty(value))return null;
		return new SimpleExpression (fieldName, value, Operator.LIKE);
	}

	/**
	 * 
	 * @param fieldName
	 * @param value
	 * @param matchMode
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression like(String fieldName, String value,
			MatchMode matchMode, boolean ignoreNull) {
		if(StringUtils.isEmpty(value))return null;
		return null;
	}

	/**
	 * ����
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
		return new SimpleExpression (fieldName, value, Operator.GT);
	}

	/**
	 * С��
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
		return new SimpleExpression (fieldName, value, Operator.LT);
	}

	/**
	 * ���ڵ���
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
		return new SimpleExpression (fieldName, value, Operator.GTE);
	}

	/**
	 * С�ڵ���
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
		return new SimpleExpression (fieldName, value, Operator.LTE);
	}

	/**
	 * ����
	 * @param criterions
	 * @return
	 */
	public static LogicalExpression and(Criterion... criterions){
		return new LogicalExpression(criterions, Operator.AND);
	}
	/**
	 * ����
	 * @param criterions
	 * @return
	 */
	public static LogicalExpression or(Criterion... criterions){
		return new LogicalExpression(criterions, Operator.OR);
	}
	/**
	 * ������
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
		if(ignoreNull&&(value==null||value.isEmpty())){
			return null;
		}
		SimpleExpression[] ses = new SimpleExpression[value.size()];
		int i=0;
		for(Object obj : value){
			ses[i]=new SimpleExpression(fieldName,obj,Operator.EQ);
			i++;
		}
		return new LogicalExpression(ses,Operator.OR);
	}
}

