package com.esgi.api_project_annuel.application.query;

import com.esgi.api_project_annuel.Domain.repository.LikeRepository;
import com.esgi.api_project_annuel.Domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeQuery {
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

}
