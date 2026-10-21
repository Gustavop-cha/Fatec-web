package com.fatec.lojinha;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class PedidoController {
    @Autowired
    private PedidoRepository bd;

    @Autowired
    private ClienteRepository bdCliente;

    @PostMapping("/api/pedido")
    public String gravar(@RequestBody Pedido obj){
        if(!bdCliente.existsById(obj.getCodigoCliente())){
            return "Cliente não existe!";
        }
        if(obj.getData() == null){
            obj.setData(LocalDate.now());
        }
        bd.save(obj);
        return "Pedido gravado com sucesso "+ obj.getCodigo();
    }
    @PutMapping("/api/pedido")
    public String alterar(@RequestBody Pedido obj){
        if(bd.existsById(obj.getCodigo())){
            bd.save(obj);
            return "Pedido alterado com sucesso";
        } else {
            return "Pedido não existe!";
        }
    }
    @GetMapping("/api/pedido/{codigo}")
    public Pedido carregar(@PathVariable("codigo") int codigo){
        if(bd.existsById(codigo)){
            return bd.findById(codigo).get();
        } else {
            return new Pedido();
        }
    }
    @GetMapping("/api/pedidos/cliente/{codigoCliente}")
    public List<Pedido> listarPorCliente(@PathVariable("codigoCliente") int codigoCliente){
        return bd.findByCodigoCliente(codigoCliente);
    }
    @DeleteMapping("/api/pedido/{codigo}")
    public String remover(@PathVariable("codigo") int codigo){
        if(bd.existsById(codigo)){
            bd.deleteById(codigo);
            return "Registro removido com sucesso!";
        } else {
            return "registro não existe!";
        }
    }
}
