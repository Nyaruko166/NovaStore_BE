//package com.sd64.novastore.controller.admin;
//
//import com.sd64.novastore.dto.common.ProductHomeDto;
//import com.sd64.novastore.model.Account;
//import com.sd64.novastore.repository.AccountRepository;
//import com.sd64.novastore.repository.SizeRepository;
//import com.sd64.novastore.repository.user.ProductViewRepository;
//import com.sd64.novastore.response.ProductHomeResponse;
//import com.sd64.novastore.service.user.ProductViewService;
//import com.sd64.novastore.utils.TempCodeManager;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RestController
//public class testController {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AccountRepository repository;
//
//    @Autowired
//    private ProductViewRepository productViewRepository;
//
//    @Autowired
//    private ProductViewService productViewService;
//
//    TempCodeManager tempCodeManager = new TempCodeManager();
//
////    @GetMapping("/index")
////    public String test() {
////        return "/admin/crud";
////    }
//
//    @GetMapping("/p")
//    public String testP(HttpServletRequest request) {
//        Account test = repository.findFirstByEmail("51241");
//        if (passwordEncoder.matches("123123", test.getPassword())) {
//            System.out.println("Ngonnnnnnnnnnnnnn");
////            System.out.println(request.getRemoteAddr());
//            String id = request.getRemoteAddr();
//            String generatedCode = tempCodeManager.createTemporaryCode(id);
//            System.out.println("Generated code: " + generatedCode + " Id: " + id);
//
//        } else {
//            System.out.println("deongon");
//        }
//        return "forgor";
////        private void scheduleCodeRemoval(String id) {
////            new Thread(() -> {
////                try {
////                    Thread.sleep(EXPIRATION_TIME);
////                    temporaryCodes.remove(id);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }).start();
////        }
//    }
//
//    //    @GetMapping("/t/{code}")
////    public String testCode(@PathVariable String code, HttpServletRequest request) {
////        if (tempCodeManager.verifyTemporaryCode(request.getRemoteAddr(), code)) {
////            System.out.println("Bú");
////        } else {
////            System.out.println("Không bú");
////        }
////        return "register";
////    }
//    @Autowired
//    private SizeRepository sizeRepository;
//
//
//    @GetMapping("/sss")
//    public ResponseEntity<?> search(@RequestParam(required = false) String productNameSearch,
//                                    @RequestParam(required = false) Integer brandId,
//                                    @RequestParam(required = false) Integer materialId,
//                                    @RequestParam(required = false) Integer categoryId,
//                                    @RequestParam(required = false) Integer formId,
////                                    @RequestParam(required = false) String description,
////                                    @RequestParam(required = false) BigDecimal priceMax,
////                                    @RequestParam(required = false) BigDecimal priceMin,
//                                    @RequestParam(required = false) Integer price,
//                                    @RequestParam(defaultValue = "0") int page,
//                                    Model model) {
//        BigDecimal priceMax = null;
//        BigDecimal priceMin = null;
//        if (price == 1) {
//            priceMin = BigDecimal.valueOf(0);
//            priceMax = BigDecimal.valueOf(Integer.MAX_VALUE);
//        }
//        List<ProductHomeDto> list = productViewRepository.searchProductResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin);
//        System.out.println();
//        return ResponseEntity.ok(list);
//    }
//
////    @GetMapping("/test")
//    public ResponseEntity<?> test() {
//        Page<ProductHomeResponse> pageProductYouMayLikeResponse = productViewService.getAllProductYouMayLike(0);
//        return ResponseEntity.ok(pageProductYouMayLikeResponse.getContent());
//    }
//}
