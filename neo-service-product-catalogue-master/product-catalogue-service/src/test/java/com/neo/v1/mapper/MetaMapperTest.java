package com.neo.v1.mapper;

import com.neo.core.message.GenericMessageSource;
import com.neo.v1.product.catalogue.model.Meta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetaMapperTest {

    @InjectMocks
    private MetaMapper subject;

    @Mock
    private GenericMessageSource messageSource;

    @Test
    void map_withStringStringObject_returnMeta() {
        String responseCode = "";
        String messageKey = "";
        List<String> params = new ArrayList<>();
        String msg = "";

        Meta expected = Meta.builder().code(responseCode).message(msg).build();
        when(messageSource.getMessage(messageKey, params)).thenReturn(msg);

        Meta result = subject.map(responseCode, messageKey, params);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);

        verify(messageSource).getMessage(messageKey, params);
    }

}
