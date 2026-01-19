package com.tinnova.veiculos.business.specification;

import com.tinnova.veiculos.business.entity.Veiculo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.*;

public class VeiculoSpecification {

    public static Specification<Veiculo> filtro(
            String marca,
            Integer ano,
            String cor,
            BigDecimal minPreco,
            BigDecimal maxPreco
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("ativo")));

            if (marca != null)
                predicates.add(cb.equal(root.get("marca"), marca));

            if (ano != null)
                predicates.add(cb.equal(root.get("ano"), ano));

            if (cor != null)
                predicates.add(cb.equal(root.get("cor"), cor));

            if (minPreco != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("precoDolar"), minPreco));

            if (maxPreco != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("precoDolar"), maxPreco));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

