import { LocalStorage } from "quasar";

export const addProductToCart = ({ commit }, { product, quantity }) => {
    commit('ADD_TO_CART', { product, quantity });
}

export const clearCart = ({ commit }, { }) => {
    commit('CLEAR_CART', { });
}

export const getCartItems = ({ commit }, ) => {
    commit('GET_CART', LocalStorage.getItem("cart") ? LocalStorage.getItem("cart") : []);
}

export const setCart = ({ commit }, {cart}) => {
    commit('SET_CART', cart);
}

export const increaseQuantity = ({ commit }, { product }) => {
    commit('INCREASE_QUANTITY', { product });
}

export const decreaseQuantity = ({ commit }, { product, quantity }) => {
    commit('DECREASE_QUANTITY', { product, quantity });
}