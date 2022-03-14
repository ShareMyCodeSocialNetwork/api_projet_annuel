package com.esgi.api_project_annuel.kernel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Utils {
    List<?> arrayIdToArrayEntity(List<Integer> id, JpaRepository jpaRepository);
}
