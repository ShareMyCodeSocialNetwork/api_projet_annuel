package com.esgi.api_project_annuel.application;

import com.esgi.api_project_annuel.kernel.Utils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilsService implements Utils {

    @Override
    public List<?> arrayIdToArrayEntity(List<Integer> id, JpaRepository jpaRepository) {
        List<Optional> entity = new ArrayList<>();
        if(id.isEmpty()){
            return entity;
        }else{
            for(int i = 0; i<id.size();i++){
                 entity.add(jpaRepository.findById(id.get(i)));
            }
        }
        return entity;
    }
}
