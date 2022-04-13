package com.esgi.api_project_annuel.Infrastructure.logger;

import com.esgi.api_project_annuel.kernel.logger.Logger;
import com.esgi.api_project_annuel.kernel.logger.LoggerFactory;


import java.util.Objects;

public class DefaultLoggerFactory implements LoggerFactory {

    @Override
    public Logger getLogger(Object object) {
        return new DefaultLogger(Objects.requireNonNull(object).getClass().getName());
    }
}
