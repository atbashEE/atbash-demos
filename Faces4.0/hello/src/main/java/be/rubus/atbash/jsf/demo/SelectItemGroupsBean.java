/*
 * Copyright 2022 Rudy De Busscher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.atbash.jsf.demo;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.model.SelectItem;
import jakarta.faces.model.SelectItemGroup;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Named
public class SelectItemGroupsBean {

    private Product selectedProduct;
    private List<Category> categories;
    private List<SelectItem> categorySelectItems;

    @PostConstruct
    public void init() {
        categories = loadExampleCategories();
        categorySelectItems = categories.stream().map(category -> {
            SelectItemGroup group = new SelectItemGroup(category.getName());
            group.setSelectItems(category.getProducts().stream()
                    .map(product -> new SelectItem(product, product.getName()))
                    .toArray(SelectItem[]::new));
            return group;
        }).collect(Collectors.toList());
    }

    private List<Category> loadExampleCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Animals", new Product("Cat"), new Product("Dog"), new Product("Parrot")));
        categories.add(new Category("Cars", new Product("Alfa Romeo"), new Product("BMW"), new Product("Hyundai"), new Product("Toyota")));
        return categories;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<SelectItem> getCategorySelectItems() {
        return categorySelectItems;
    }
}
