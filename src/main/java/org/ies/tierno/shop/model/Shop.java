package org.ies.tierno.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

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

    public List<Product> getOrderProducts(int orderId, String nif) {
        Order order = findCustomerOrder(nif, orderId);
        if (order != null) {
            List<Product> products = new ArrayList<>();
            for (var item : order.getItems()) {
                products.add(productsById.get(item.getProductId()));
            }
            return products;
        }
        return null;
    }

    public List<Product> findProductsByTag(String tag) {
        List<Product> products = new ArrayList<>();
        for (var product : productsById.values()) {
            if (product.getTags().contains(tag)) {
                products.add(product);
            }
        }
        return products;
    }

    public Double calculateCustomerExpenditures(String nif) {
        Customer customer = findCustomer(nif);
        if (customer != null) {
            double expenditures = 0;
            for (var order : customer.getOrders()) {
                expenditures += order.getPrice();
            }
            return expenditures;
        }
        return null;
    }

}
