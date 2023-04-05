package id.ac.ui.cs.advprog.bayarservice.service.bill;

import id.ac.ui.cs.advprog.bayarservice.model.bill.Bill;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.bayarservice.dto.Bill.BillRequest;

@Service
public interface BillService {
    Bill findById(Integer id);
    Bill create(BillRequest request);
    void delete(Integer id);
}
