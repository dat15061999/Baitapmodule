package com.example.demo6.services.deposit;

import com.example.demo6.models.Deposit;
import com.example.demo6.repositories.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepositService implements IDepositService{
    @Autowired
    private DepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return null;
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void delete(Deposit deposit) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
