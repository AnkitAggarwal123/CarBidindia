package com.carbid.demo.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "AuditAwareImp")
public class AuditAwareImp implements AuditorAware {
    /**
     * @return auditor
     */
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("CarBid_SYS");
    }
}