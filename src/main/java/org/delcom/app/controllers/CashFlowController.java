package org.delcom.app.controllers;

import org.delcom.app.configs.ApiResponse;
import org.delcom.app.entities.CashFlow;
import org.delcom.app.services.CashFlowService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cash-flows")
public class CashFlowController {

    private final CashFlowService cashFlowService;

    public CashFlowController(CashFlowService cashFlowService) {
        this.cashFlowService = cashFlowService;
    }

    private boolean isValidCashFlow(CashFlow flow) {
        return flow.getType() != null && !flow.getType().trim().isEmpty() &&
               flow.getSource() != null && !flow.getSource().trim().isEmpty() &&
               flow.getLabel() != null && !flow.getLabel().trim().isEmpty() &&
               flow.getAmount() != null && flow.getAmount() > 0 &&
               flow.getDescription() != null && !flow.getDescription().trim().isEmpty();
    }

    @PostMapping
    public ApiResponse<Map<String, UUID>> createCashFlow(@RequestBody CashFlow cashFlow) {
        if (!isValidCashFlow(cashFlow)) {
            return new ApiResponse<>("fail", "Data Cash Flow tidak valid.", null);
        }

        CashFlow created = cashFlowService.createCashFlow(
                cashFlow.getType(),
                cashFlow.getSource(),
                cashFlow.getLabel(),
                cashFlow.getAmount(),
                cashFlow.getDescription()
        );

        Map<String, UUID> data = new HashMap<>();
        data.put("id", created.getId());

        return new ApiResponse<>("success", "Berhasil menambahkan transaksi cash flow.", data);
    }

    @GetMapping
    public ApiResponse<Map<String, List<CashFlow>>> getAllCashFlows(@RequestParam(required = false) String keyword) {
        List<CashFlow> cashFlows = cashFlowService.getAllCashFlows(keyword);

        Map<String, List<CashFlow>> data = new HashMap<>();
        data.put("cash_flows", cashFlows);

        return new ApiResponse<>("success", "Berhasil mengambil semua data transaksi cash flow.", data);
    }

    @GetMapping("/{id}")
    public ApiResponse<Map<String, CashFlow>> getCashFlowById(@PathVariable UUID id) {
        CashFlow cashFlow = cashFlowService.getCashFlowById(id);

        if (cashFlow == null) {
            return new ApiResponse<>("fail", "Transaksi dengan ID " + id + " tidak ditemukan.", null);
        }

        Map<String, CashFlow> data = new HashMap<>();
        data.put("cashFlow", cashFlow);

        return new ApiResponse<>("success", "Berhasil mengambil data transaksi.", data);
    }

    @PutMapping("/{id}")
    public ApiResponse<CashFlow> updateCashFlow(@PathVariable UUID id, @RequestBody CashFlow details) {
        if (!isValidCashFlow(details)) {
            return new ApiResponse<>("fail", "Data Cash Flow tidak valid.", null);
        }

        CashFlow updated = cashFlowService.updateCashFlow(
                id,
                details.getType(),
                details.getSource(),
                details.getLabel(),
                details.getAmount(),
                details.getDescription()
        );

        if (updated == null) {
            return new ApiResponse<>("fail", "Transaksi dengan ID " + id + " tidak ditemukan.", null);
        }

        return new ApiResponse<>("success", "Berhasil memperbarui transaksi cash flow.", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCashFlow(@PathVariable UUID id) {
        boolean deleted = cashFlowService.deleteCashFlow(id);

        if (!deleted) {
            return new ApiResponse<>("fail", "Gagal menghapus transaksi: ID " + id + " tidak ditemukan.", null);
        }

        return new ApiResponse<>("success", "Berhasil menghapus transaksi dengan ID " + id + ".", null);
    }

    @GetMapping("/labels")
    public ApiResponse<Map<String, List<String>>> getCashFlowLabels() {
        List<String> labels = cashFlowService.getCashFlowLabels();

        Map<String, List<String>> data = new HashMap<>();
        data.put("labels", labels);

        return new ApiResponse<>("success", "Berhasil mengambil data label.", data);
    }
}
