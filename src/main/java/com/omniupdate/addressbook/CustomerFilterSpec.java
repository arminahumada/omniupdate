package com.omniupdate.addressbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Case-insensitive Specification for fields on {@link Contact}
 *
 * Uses like expression on a given fieldName with sql like '{searchTerm}%'
 *
 */
@AllArgsConstructor
@Builder
@Getter
public class CustomerFilterSpec implements Specification<Contact> {

    private final String fieldName;
    private final String searchTerm;

    @Override
    public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)), searchTerm.toLowerCase() + "%");
    }
}
