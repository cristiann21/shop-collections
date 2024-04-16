package org.ies.tierno.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private String name;
    private Map<Integer, Product> productsById;
    private TreeSet<Customer> customers;


    public Optional<Customer> findCustomer(String nif) {
        return customers.stream()
                .filter(customer -> customer.getNif().equals(nif))
                .findFirst();
    }

    public Optional<Order> findCustomerOrder(String nif, int orderId) {
        return findCustomer(nif)
                .flatMap(customer ->
                        customer.getOrders()
                                .stream()
                                .filter(order -> order.getId() == orderId)
                                .findFirst()
                );
    }

    public Product findById(int productId) {
        if (productsById.containsKey(productId)) {
            return productsById.get(productId);
        } else {
            return null;
        }
    }

    public Optional<List<Product>> getOrderProducts(int orderId, String nif) {
        Optional<Order> orderOpt = findCustomerOrder(nif, orderId);
        return orderOpt.map(order ->
                order
                        .getItems()
                        .stream()
                        .map(item -> productsById.get(item.getProductId()))
                        .collect(Collectors.toList())
        );
    }

    public List<Product> findProductsByTag(String tag) {
        return productsById
                .values()
                .stream()
                .filter(product -> product.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    public Optional<Double> calculateCustomerExpenditures(String nif) {
        Optional<Customer> customerOpt = findCustomer(nif);

        return customerOpt
                .map(customer ->
                        customer
                                .getOrders()
                                .stream()
                                .mapToDouble(order -> order.getPrice())
                                .sum()
                );
    }

}
