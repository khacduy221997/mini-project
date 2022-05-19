import { LocalStorage } from "quasar";

export const ADD_TO_CART = (state, { product, quantity }) => {
    let productInCart = state.cart.find(item => {
        return item.product.id === product.id;
    });

    if (productInCart) {
        productInCart.quantity += quantity;
        LocalStorage.set("cart", state.cart);
        return;
    }

    state.cart.push({
        product,
        quantity
    })

    LocalStorage.set("cart", state.cart);

}

export const CLEAR_CART = (state, {}) => {
    state.cart = [];
    LocalStorage.remove("cart");
}

// TODO: Remove it
export const GET_CART = (state, cartItems) => {
    state.cart = cartItems;
    LocalStorage.set("cart", state.cart);
}

export const SET_CART = (state, cart) => {
    state.cart = cart;
    LocalStorage.set("cart", state.cart);
}

export const INCREASE_QUANTITY = (state, { product }) => {
    let productInCart = state.cart.find(item => {
        return item.product.id === product.id;
    })

    if (productInCart) {
        if (productInCart.quantity + productInCart.product.unit < productInCart.product.max) {
            productInCart.quantity = productInCart.quantity + productInCart.product.unit;
        } else if (productInCart.quantity + productInCart.product.unit === productInCart.product.max) {
            productInCart.quantity = productInCart.product.max
        }
        LocalStorage.set("cart", state.cart);
    }
}

export const DECREASE_QUANTITY = (state, { product }) => {
    let productInCart = state.cart.find(item => {
        return item.product.id === product.id;
    })

    if (productInCart) {
        if (productInCart.quantity - productInCart.product.unit > 0) {
            productInCart.quantity = productInCart.quantity - productInCart.product.unit;
        } else {
            state.cart = state.cart.filter(item => {
                return item.product.id !== product.id
            })
        }
        LocalStorage.set("cart", state.cart);
    }
}