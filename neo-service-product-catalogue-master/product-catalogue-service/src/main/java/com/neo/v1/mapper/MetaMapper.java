package com.neo.v1.mapper;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.product.catalogue.model.Meta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MetaMapper {

    private final GenericMessageSource messageSource;

    public Meta map(String responseCode, String messageKey, Object... params) {
        return Meta.builder()
                .code(responseCode)
                .message(messageSource.getMessage(messageKey, params))
                .build();
    }
}
