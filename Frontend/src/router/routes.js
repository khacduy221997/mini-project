const routes = [
  {
    path: "/login",
    component: () => import("pages/Login.vue"),
  },
  {
    path: "/register",
    component: () => import("pages/Register.vue"),
  },
  {
    path: "/",
    component: () => import("layouts/MainLayout.vue"),
    children: [
      { path: "", component: () => import("pages/Index.vue") },
      { path: "product-detail", component: () => import("pages/ProductDetail.vue") },
    ],
  },
  {
    path: "/",
    component: () => import("layouts/LayoutNoFooter.vue"),
    children: [
      { path: "cart", component: () => import("pages/Cart.vue") },
      { path: "checkout", component: () => import('pages/Checkout.vue') },
    ],
  },
  {
    path: "/:catchAll(.*)*",
    component: () => import("pages/Error404.vue"),
  },
];

export default routes;
