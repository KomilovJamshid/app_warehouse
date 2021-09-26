package uz.jamshid.app_warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.jamshid.app_warehouse.entity.Client;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Result add(Client client) {
        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Phone number already exists", false);

        clientRepository.save(client);
        return new Result("Client saved", true);
    }

    public Result get(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new Result("Clients sent successfully", true, clientRepository.findAll(pageable));
    }

    public Result getById(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return new Result("Client by id sent", true, optionalClient.orElseGet(Client::new));
    }

    public Result delete(Integer id) {
        try {
            clientRepository.deleteById(id);
            return new Result("Client deleted", true);
        } catch (Exception exception) {
            return new Result("Client not found", false);
        }
    }

    public Result edit(Integer id, Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent())
            return new Result("Client not found", false);
        Client currentClient = new Client();

        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Phone number already exists", false);
        currentClient.setName(client.getName());
        currentClient.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(currentClient);
        return new Result("Client edited", true);
    }
}
