export const cartItemCount = (state) => {
    return state.cart ? state.cart.length : 0;
}

export const cartTotalPrice = (state) => {
    let total = 0;

    state.cart.forEach(item => {
        total += item.product.remainPrice * item.quantity;
    });

    return total;
}