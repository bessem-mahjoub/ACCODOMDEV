package accodom.dom.Service;

import accodom.dom.Entities.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> getAllContracts();
    Contract getContractById(Long contractId);
    Contract createContract(Contract contract);
    Contract updateContract(Contract contract);
}
