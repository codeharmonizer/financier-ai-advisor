package com.neo.v1.cache;

import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.LANGUAGE_INVALID;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import com.neo.core.exception.ServiceException;
import com.neo.v1.entity.LanguageEntity;
import com.neo.v1.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    private List<LanguageEntity> languageEntityList = new ArrayList<>();

    @PostConstruct
    @Transactional(readOnly = true)
    public void loadLanguages() {
        this.languageEntityList = languageRepository.findAll();
    }

    public LanguageEntity getLanguageEntityByCode(String code) {
        return languageEntityList.stream().filter(lang -> code.equals(lang.getCode())).findFirst()
                .orElseThrow(() -> new ServiceException(LANGUAGE_INVALID));
    }
}
