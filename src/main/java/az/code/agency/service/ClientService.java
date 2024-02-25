package az.code.agency.service;

import az.code.agency.dto.ClientDTO;
import az.code.agency.entity.Client;
import az.code.agency.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO getClientById(long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client != null) {
            return convertToDTO(client);
        }
        return null;
    }

    public ClientDTO saveClient(ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(clientDTO.getClientId()).orElse(null);
        if (existingClient != null) {
            existingClient.setChatId(clientDTO.getChatId());
            existingClient.setFullName(clientDTO.getFullName());
            existingClient.setPhoneNumber(clientDTO.getPhoneNumber());
            existingClient = clientRepository.save(existingClient);
            return convertToDTO(existingClient);
        } else {
            Client client = convertToEntity(clientDTO);
            client = clientRepository.save(client);
            return convertToDTO(client);
        }
    }

    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(client.getClientId());
        clientDTO.setChatId(client.getChatId());
        clientDTO.setFullName(client.getFullName());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        return clientDTO;
    }

    public Client convertToEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setClientId(clientDTO.getClientId());
        client.setChatId(clientDTO.getChatId());
        client.setFullName(clientDTO.getFullName());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        return client;
    }


}
