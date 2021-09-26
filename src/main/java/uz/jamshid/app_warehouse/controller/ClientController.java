package uz.jamshid.app_warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.jamshid.app_warehouse.entity.Client;
import uz.jamshid.app_warehouse.payload.Result;
import uz.jamshid.app_warehouse.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @PostMapping
    public Result addClient(@RequestBody Client client) {
        return clientService.add(client);
    }

    @GetMapping
    public Result getClients(@RequestParam int page) {
        return clientService.get(page);
    }

    @GetMapping("/{id}")
    public Result getClientById(@PathVariable Integer id) {
        return clientService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteClient(@PathVariable Integer id) {
        return clientService.delete(id);
    }

    @PutMapping("/{id}")
    public Result editClient(@PathVariable Integer id, @RequestBody Client client) {
        return clientService.edit(id, client);
    }
}
