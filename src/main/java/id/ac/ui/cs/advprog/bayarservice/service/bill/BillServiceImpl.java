package id.ac.ui.cs.advprog.bayarservice.service.bill;

import id.ac.ui.cs.advprog.bayarservice.exception.BillDoesNotExistException;
import id.ac.ui.cs.advprog.bayarservice.dto.Bill.BillRequest;
import id.ac.ui.cs.advprog.bayarservice.model.bill.Bill;
import id.ac.ui.cs.advprog.bayarservice.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.bayarservice.service.invoice.InvoiceService;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final InvoiceService invoiceService;

    @Override
    public Bill findById(Integer id) {
        return this.billRepository.findById(id)
                .orElseThrow(() -> new BillDoesNotExistException(id));
    }

    @Override
    public Bill create(BillRequest request) {
        Bill bill = Bill.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .subTotal(request.getSubTotal())
                .invoice(invoiceService.findById(request.getInvoiceId()))
                .build();
        bill.getInvoice().setTotalAmount((bill.getInvoice().getTotalAmount() + bill.getSubTotal());
        return billRepository.save(bill);
    }

    @Override
    public void delete(Integer id) {
        if (isBillDoesNotExist(id)) {
            throw new BillDoesNotExistException(id);
        } else {
            billRepository.deleteById(id);
        }
    }

    @Override
    public Bill update(Integer id, BillRequest request) {
        if (isBillDoesNotExist(id)) {
            throw new BillDoesNotExistException(id);
        } else {
            Bill bill = billRepository.findById(id).orElseThrow();
            bill.setName(request.getName());
            bill.setPrice(request.getPrice());
            bill.setQuantity(request.getQuantity());
            bill.setSubTotal(request.getSubTotal());
            bill.setInvoice(invoiceService.findById(request.getInvoiceId()));
            return this.billRepository.save(bill);
        }
    }

    private boolean isBillDoesNotExist(Integer id) {
        return billRepository.findById(id).isEmpty();
    }
}
