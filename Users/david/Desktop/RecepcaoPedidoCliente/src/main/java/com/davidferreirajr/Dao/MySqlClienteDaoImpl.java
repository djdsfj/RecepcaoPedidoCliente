package com.davidferreirajr.Dao;

import com.davidferreirajr.Enity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;




/**
 * Created by david on 06/05/2017.
 */
@Repository("mysql")
public class MySqlClienteDaoImpl implements ClienteDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class ClienteRowMapper implements RowMapper<Cliente>{

        @Override
        public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
            Cliente cliente = new Cliente();
            cliente.setId(resultSet.getInt("id"));
            cliente.setNome(resultSet.getString("Nome"));
            cliente.setProduto(resultSet.getString("Produto"));
            cliente.setValorProd(resultSet.getDouble("Valor do Produto"));
            cliente.setQuantidade(resultSet.getInt("quantidade"));
            cliente.setCodCliente(resultSet.getInt("cod.cliente"));
            return cliente;
        }
    }

    @Override
    public Collection<Cliente> getAllsClientes() {
       // SELECT column_name(s) FROM table_name
        final String sql = "SELECT id, nome, produto, valorProd, quantidade, codCliente FROM clientes";
        List<Cliente> clientes = jdbcTemplate.query(sql, new ClienteRowMapper());
        return clientes;
    }

    @Override
    public Cliente getClienteById(int id) {
        // SELECT column_name(s) FROM table_name where column = value
        final String sql = "SELECT id, nome, produto, valorProd, quantidade, codCliente FROM clientes where id = ?";
        Cliente cliente = jdbcTemplate.queryForObject(sql, new ClienteRowMapper(), id);
        return cliente;
    }

    @Override
    public void removeClienteById(int id) {
        //DELETE FROM table_name
        //WHERE some_column = some_value
        final String sql = "DELETE FROM clientes WHERE id= ?";
     jdbcTemplate.update(sql, id);

    }

    @Override
    public void updateCliente(Cliente cliente) {
       // UPDATE table_name
       // SET column1=value, column2=value2,...
        //WHERE some_column=some_value
        final String sql = "UPDATE clientes SET nome=?, produto=?, valorProd=?, quantidade=?, codCliente=? WHERE id = ?";
       final int id = cliente.getId();
        final String nome = cliente.getNome();
        final String produto= cliente.getProduto();
        final Double valorProd = cliente.getValorProd();
        final int quantidade = cliente.getQuantidade();
        final int codCliente = cliente.getCodCliente();

        jdbcTemplate.update(sql, new Object[] {nome, produto, valorProd, quantidade, codCliente});

    }

    @Override
    public void insertClienteToDb(Cliente cliente) {
        //INSERT INTO table_name (column1, column2, column3,...)
        //VALUES (value1, value2, value3,...)
        final String sql = "INSERT INTO clientes (nome, produto, valorProd, quantidade, codCliente) VALUES (?, ?, ?, ?, ?)";
        final String nome = cliente.getNome();
        final String produto= cliente.getProduto();
        final Double valorProd = cliente.getValorProd();
        final int quantidade = cliente.getQuantidade();
        final int codCliente = cliente.getCodCliente();
        jdbcTemplate.update(sql, new Object[] {nome, produto, valorProd, quantidade, codCliente});


    }
}
