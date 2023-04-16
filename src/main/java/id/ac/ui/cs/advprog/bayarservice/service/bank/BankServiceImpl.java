package id.ac.ui.cs.advprog.bayarservice.service.bank;

import id.ac.ui.cs.advprog.bayarservice.exception.BankAlreadyExistsException;
import id.ac.ui.cs.advprog.bayarservice.exception.BankDoesNotExistException;
import id.ac.ui.cs.advprog.bayarservice.dto.Bank.BankRequest;
import id.ac.ui.cs.advprog.bayarservice.exception.BillDoesNotExistException;
import id.ac.ui.cs.advprog.bayarservice.model.bank.Bank;
import id.ac.ui.cs.advprog.bayarservice.model.bill.Bill;
import id.ac.ui.cs.advprog.bayarservice.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    @Override
    public Bank create(BankRequest request) {
        if (isBankAlreadyExist(request.getName())) {
            throw new BankAlreadyExistsException(request.getName());
        }
        Bank newBank = Bank.builder()
                .name(request.getName())
                .adminFee(request.getAdminFee())
                .build();
        return bankRepository.save(newBank);
    }

    @Override
    public void deleteById(Integer id) {
        if (isBankDoesNotExist(id)) {
            throw new BankDoesNotExistException(id);
        } else {
            bankRepository.deleteById(id);
        }
    }

    @Override
    public Bank findById(Integer id) {
        Optional<Bank> bank = bankRepository.findById(id);
        // TODO
        return bank.get();
    }

    @Override
    public Bank update(Integer id, BankRequest request) {
        if (isBankDoesNotExist(id)) {
            throw new BankDoesNotExistException(id);
        }
        else {
            if (bankRepository.findById(id).isPresent()) {
                if (isBankAlreadyExist(request.getName())) {
                    throw new BankAlreadyExistsException(request.getName());
                } else if (request.getName().equals(bankRepository.findById(id).get().getName())) {
                    Bank bank = bankRepository.findById(id).get();
                    bank.setName(request.getName());
                    bank.setAdminFee(request.getAdminFee());
                    return bankRepository.save(bank);
                } else {
                    throw new BankAlreadyExistsException(request.getName());
                }
            }
            else {
                throw new BankDoesNotExistException(id);
            }
        }
    }

    private boolean isBankAlreadyExist(String name) {
        return bankRepository.findByName(name).isPresent();
    }

    private boolean isBankDoesNotExist(Integer id) {
        return bankRepository.findById(id).isEmpty();
    }
}
