package com.neo.v1.cache;

import static com.neo.v1.enums.ProductCatalogueServiceKeyMapping.UNIT_INVALID;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import com.neo.core.exception.ServiceException;
import com.neo.v1.entity.UnitEntity;
import com.neo.v1.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    private List<UnitEntity> unitEntityList = new ArrayList<>();

    @PostConstruct
    @Transactional(readOnly = true)
    public void loadUnits() {
        this.unitEntityList = unitRepository.findAll();
    }

    public UnitEntity getUnitEntityByCode(String code) {
        return unitEntityList.stream().filter(unit -> code.equals(unit.getCode())).findFirst()
                .orElseThrow(()-> new ServiceException(UNIT_INVALID));
    }


}
