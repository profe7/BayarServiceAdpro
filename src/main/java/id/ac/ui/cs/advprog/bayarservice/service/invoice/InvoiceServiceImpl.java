package id.ac.ui.cs.advprog.bayarservice.service.invoice;

import id.ac.ui.cs.advprog.bayarservice.dto.Invoice.InvoiceRequest;
import id.ac.ui.cs.advprog.bayarservice.exceptions.InvoiceDoesNotExistException;
import id.ac.ui.cs.advprog.bayarservice.model.invoice.Invoice;
import id.ac.ui.cs.advprog.bayarservice.model.invoice.PaymentMethod;
import id.ac.ui.cs.advprog.bayarservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoicesRepository;

    @Override
    public List<Invoice> findAll() {
        return invoicesRepository.findAll()
                .stream()
                .map(invoices -> invoices)
                .toList();
    }

    @Override
    public Invoice findById(UUID sessionId) {
        if (isInvoiceDoesNotExist(sessionId)) {
            throw new InvoiceDoesNotExistException(sessionId);
        }
        return invoicesRepository.findById(sessionId).orElse(null);
    }

    @Override
    public Invoice create(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        Invoice newInvoice = mappingRequestToObject(invoice, request);
        return invoicesRepository.save(newInvoice);
    }

    @Override
    public Invoice update(UUID sessionId, InvoiceRequest request){
        if (isInvoiceDoesNotExist(sessionId)) {
            throw new InvoiceDoesNotExistException(sessionId);
        }
        Invoice invoice = null;
        if (request.getPaymentMethod().equals(PaymentMethod.BANK.name())) {
            invoice = Invoice.builder()
                    .sessionId(sessionId)
                    .paymentMethod(PaymentMethod.BANK)
                    .adminFee(request.getAdminFee())
                    .totalAmount(request.getTotalAmount())
                    .discount(request.getDiscount())
                    .build();
        } else if (request.getPaymentMethod().equals(PaymentMethod.CASH.name())) {
            invoice = Invoice.builder()
                    .sessionId(sessionId)
                    .paymentMethod(PaymentMethod.CASH)
                    .adminFee(request.getAdminFee())
                    .totalAmount(request.getTotalAmount())
                    .discount(request.getDiscount())
                    .build();
        }
        return this.invoicesRepository.save(invoice);
    }

    @Override
    public void delete(UUID sessionId) {
        if (isInvoiceDoesNotExist(sessionId)) {
            throw new InvoiceDoesNotExistException(sessionId);
        }
        invoicesRepository.deleteById(sessionId);
    }

    private boolean isInvoiceDoesNotExist(UUID sessionId) {
        return invoicesRepository.findById(sessionId).isEmpty();
    }

    private Invoice mappingRequestToObject(Invoice invoice, InvoiceRequest request) {
        invoice.setSessionId(request.getSessionId());
        invoice.setDiscount(request.getDiscount());
        invoice.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        invoice.setTotalAmount(request.getTotalAmount());
        invoice.setAdminFee(request.getAdminFee());
        return invoice;
    }

}