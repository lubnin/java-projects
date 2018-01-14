package ru.rti.holidays.service;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.rti.holidays.entity.SystemParameter;
import ru.rti.holidays.repository.SystemParameterRepository;

import java.util.List;

@Service
@UIScope
@SpringComponent
@SuppressWarnings("unused")
public class SystemParameterServiceImpl implements SystemParameterService {
    private static final Logger log = LoggerFactory.getLogger(SystemParameterServiceImpl.class);

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Override
    public SystemParameter saveSystemParameter(SystemParameter systemParameter) {
        return systemParameterRepository.save(systemParameter);
    }

    @Override
    public SystemParameter getSystemParameterByCaption(String caption) {
        return systemParameterRepository.findByCaption(caption);
    }

    @Override
    public SystemParameter getSystemParameterByInnerName(String innerName) {
        return systemParameterRepository.findByInnerName(innerName);
    }

    @Override
    public List<SystemParameter> getAllSystemParametersSortedByInnerNameAsc() {
        List<SystemParameter> allSystemParametersSortedByInnerName = systemParameterRepository.findAll(new Sort(Sort.Direction.ASC, "innerName"));
        return allSystemParametersSortedByInnerName;
    }

    @Override
    public boolean deleteSystemParameters(Iterable<SystemParameter> systemParameters) {
        systemParameterRepository.deleteInBatch(systemParameters);
        return true;
    }
}
