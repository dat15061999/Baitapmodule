//package com.example.demo6.controller;
//
//
//import com.example.demo6.models.Product;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Controller
//@RequestMapping("/product")
//public class ProductController {
//    private final IProductService productService = new ProductService();
//
//    private final ICategoryService categoryService = new CategoryService();
//
//
//    @GetMapping("")
//    public String index(Model model) {
//        List<Product> products = productService.findAll();
//        model.addAttribute("products", products);
//        return "/cp/product/list";
//    }
//
//    @GetMapping("/create")
//    public ModelAndView showCreateForm() {
//        ModelAndView modelAndView = new ModelAndView("/cp/product/create");
//        modelAndView.addObject("product", new Product());
//        modelAndView.addObject("categories", categoryService.findAll());
//        modelAndView.addObject("category", new Category());
//        return modelAndView;
//    }
//
//    @GetMapping("/update/{id}")
//    public ModelAndView showUpdateForm(@PathVariable int id) {
//        ModelAndView modelAndView = new ModelAndView("/cp/product/edit");
//            Product product = productService.findById(id);
//        if (product == null) {
//            modelAndView.addObject("error", "Không tìm thấy sản phẩm");
//        } else {
//            modelAndView.addObject("productUpdate", product);
//            modelAndView.addObject("categories", categoryService.findAll());
//        }
//        return modelAndView;
//    }
//
//    @PostMapping("/create")
//    public ModelAndView saveProduct(@ModelAttribute Product product) {
//        product.setCategory(categoryService.findById(product.getCategory().getId()));
//        productService.save(product);
//        ModelAndView modelAndView = new ModelAndView("/cp/product/create");
//        modelAndView.addObject("product", new Product());
//        modelAndView.addObject("categories", categoryService.findAll());
//        modelAndView.addObject("message", "Created new product successfully !");
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable int id) {
//        productService.remove(id);
//        return "redirect:/product?message=Deleted product successfully!";
//    }
//
//    @PostMapping("/update/{id}")
//    public ModelAndView updateProduct(@PathVariable int id, @ModelAttribute Product product) {
//        ModelAndView modelAndView = new ModelAndView("/cp/product/edit");
//        if (product.getPrice().compareTo(BigDecimal.valueOf(10000L)) < 0) {
//            modelAndView.addObject("error", "Gía phải lớn hơn 10000 ");
//        } else {
//            product.setCategory(categoryService.findById(product.getCategory().getId()));
//            productService.update(id, product);
//            modelAndView.addObject("message", "Cập nhật thành công");
//        }
//        modelAndView.addObject("productUpdate", product);
//        modelAndView.addObject("categories", categoryService.findAll());
//        return modelAndView;
//    }
//
//}
