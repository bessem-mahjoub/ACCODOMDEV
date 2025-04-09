package accodom.dom.Service;

import accodom.dom.Entities.Contract;
import accodom.dom.Repository.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContractServiceImpl implements ContractService{
    @Autowired
    ContractRepo contractRepo;
    @Override
    public List<Contract> getAllContracts() {
        return contractRepo.findAll();
    }

    @Override
    public Contract getContractById(Long contractId) {
        return contractRepo.findById(contractId).orElse(null);
    }

    @Override
    public Contract createContract(Contract contract) {
        return contractRepo.save(contract);
    }

    @Override
    public Contract updateContract(Contract contract) {
        return contractRepo.save(contract);
    }


}
