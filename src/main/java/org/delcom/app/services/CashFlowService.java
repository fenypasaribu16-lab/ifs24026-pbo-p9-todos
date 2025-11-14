package org.delcom.app.services;

import org.delcom.app.entities.CashFlow;
import org.delcom.app.repositories.CashFlowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CashFlowService {

    private final CashFlowRepository cashFlowRepository;

    public CashFlowService(CashFlowRepository cashFlowRepository) {
        this.cashFlowRepository = cashFlowRepository;
    }

    public CashFlow createCashFlow(String type, String source, String label, Integer amount, String description) {
        CashFlow flow = new CashFlow(type, source, label, amount, description);
        flow.onCreate();
        return cashFlowRepository.save(flow);
    }

    public List<CashFlow> getAllCashFlows(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return cashFlowRepository.findByKeyword(keyword.trim());
        }
        return cashFlowRepository.findAll();
    }

    public CashFlow getCashFlowById(UUID id) {
        return cashFlowRepository.findById(id).orElse(null);
    }

    public List<String> getCashFlowLabels() {
        return cashFlowRepository.findDistinctLabels();
    }

    public CashFlow updateCashFlow(UUID id, String type, String source, String label, Integer amount, String description) {
        Optional<CashFlow> existed = cashFlowRepository.findById(id);

        if (existed.isEmpty()) return null;

        CashFlow flow = existed.get();
        flow.setType(type);
        flow.setSource(source);
        flow.setLabel(label);
        flow.setAmount(amount);
        flow.setDescription(description);
        flow.onUpdate();

        return cashFlowRepository.save(flow);
    }

    public boolean deleteCashFlow(UUID id) {
        if (!cashFlowRepository.existsById(id)) {
            return false;
        }
        cashFlowRepository.deleteById(id);
        return true;
    }
}
