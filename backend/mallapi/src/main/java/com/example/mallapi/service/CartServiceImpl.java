package com.example.mallapi.service;

import java.util.*;

import com.example.mallapi.domain.Cart;
import com.example.mallapi.domain.CartItem;
import com.example.mallapi.domain.Member;
import com.example.mallapi.domain.Product;
import com.example.mallapi.dto.CartItemDTO;
import com.example.mallapi.dto.CartItemListDTO;
import com.example.mallapi.repository.CartItemRepository;
import com.example.mallapi.repository.CartRepository;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO) {

        String email = cartItemDTO.getEmail();

        Long pno = cartItemDTO.getPno();

        int qty = cartItemDTO.getQty();

        Long cino = cartItemDTO.getCino();

        log.info("======================================================");
        log.info(cartItemDTO.getCino() == null);

        if(cino != null) { //장바구니 아이템 번호가 있어서 수량만 변경하는 경우

            Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);

            CartItem cartItem = cartItemResult.orElseThrow();

            cartItem.changeQty(qty);

            cartItemRepository.save(cartItem);

            return getCartItems(email);
        }

        //장바구니 아이템 번호 cino가 없는 경우

        //사용자의 카트
        Cart cart = getCart(email);

        CartItem cartItem = null;

        //이미 동일한 상품이 담긴적이 있을 수 있으므로
        cartItem = cartItemRepository.getItemOfPno(email, pno);

        if(cartItem == null){
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).qty(qty).build();

        }else {
            cartItem.changeQty(qty);
        }

        //상품 아이템 저장
        cartItemRepository.save(cartItem);

        return getCartItems(email);
    }


    //사용자의 장바구니가 없었다면 새로운 장바구니를 생성하고 반환
    private Cart getCart(String email ){

        Cart cart = null;

        Optional<Cart> result = cartRepository.getCartOfMember(email);

        if(result.isEmpty()) {

            log.info("Cart of the member is not exist!!");

            Member member = Member.builder().email(email).build();

            Cart tempCart = Cart.builder().owner(member).build();

            cart = cartRepository.save(tempCart);

        }else {
            cart = result.get();
        }

        return cart;

    }

    @Override
    public List<CartItemListDTO> getCartItems(String email) {

        return cartItemRepository.getItemsOfCartDTOByEmail(email);
    }

    @Override
    public List<CartItemListDTO> remove(Long cino) {

        Long cno  = cartItemRepository.getCartFromItem(cino);

        log.info("cart no: " + cno);

        cartItemRepository.deleteById(cino);

        return cartItemRepository.getItemsOfCartDTOByCart(cno);
    }
}
