package org.echoice.core.module.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * ����һ����ѯ��������
 * @author lee
 *
 * @param <T>
 */
public class Criteria<T> implements Specification<T>{
	private List<Criterion> criterions = new ArrayList<Criterion>();

	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		if (!criterions.isEmpty()) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for(Criterion c : criterions){
				predicates.add(c.toPredicate(root, query,builder));
			}
			// ������������ and ��������
			if (predicates.size() > 0) {
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}
		return builder.conjunction();
	}
	/**
	 * ���Ӽ��������ʽ
	 * @Methods Name add
	 * @Create
	 * @param expression0 void
	 */
	public void add(Criterion criterion){
		if(criterion!=null){
			criterions.add(criterion);
		}
	}
}
