package com.sporting.api.consts;

public interface ApiPath {
    String API = "/api/v1";

    //Acount
    String ACCOUNT_GET_ALL = API + "/account/get-all";
    String ACCOUNT_GET_UUID = API + "/account/get-by-uuid";
    String ACCOUNT_CREATE = API + "/account/create";
    String ACCOUNT_DELETE = API + "/account/delete";
    String ACCOUNT_UPDATE = API + "/account/update";
    String ACCOUNT_PERFORM_LOCK = API + "/account/perform-lock";
    String ACCOUNT_VALIDATE_LOGIN = API + "/account/validate-login";

    //Cart Detail
    String CART_DETAIL_GET_ALL = API + "/cart-detail/get-all";
    String CART_DETAIL_GET_UUID = API + "/cart-detail/get-by-uuid";
    String CART_DETAIL_CREATE = API + "/cart-detail/create";
    String CART_DETAIL_DELETE = API + "/cart-detail/delete";
    String CART_DETAIL_UPDATE = API + "/cart-detail/update";
    String CART_DETAIL_PERFORM_LOCK = API + "/cart-detail/perform-lock";

    //Category
    String CATEGORY_GET_ALL = API + "/category/get-all";
    String CATEGORY_GET_UUID = API + "/category/get-by-uuid";
    String CATEGORY_CREATE = API + "/category/create";
    String CATEGORY_DELETE = API + "/category/delete";
    String CATEGORY_UPDATE = API + "/category/update";
    String CATEGORY_PERFORM_LOCK = API + "/category/perform-lock";

    //Customer
    String CUSTOMER_GET_ALL = API + "/customer/get-all";
    String CUSTOMER_GET_UUID = API + "/customer/get-by-uuid";
    String CUSTOMER_CREATE = API + "/customer/create";
    String CUSTOMER_DELETE = API + "/customer/delete";
    String CUSTOMER_UPDATE = API + "/customer/update";
    String CUSTOMER_PERFORM_LOCK = API + "/customer/perform-lock";

    //Order Detail
    String ORDER_DETAIL_GET_ALL = API + "/order-detail/get-all";
    String ORDER_DETAIL_GET_UUID = API + "/order-detail/get-by-uuid";
    String ORDER_DETAIL_CREATE = API + "/order-detail/create";
    String ORDER_DETAIL_DELETE = API + "/order-detail/delete";
    String ORDER_DETAIL_UPDATE = API + "/order-detail/update";
    String ORDER_DETAIL_PERFORM_LOCK = API + "/order-detail/perform-lock";

    //Payment
    String PAYMENT_GET_ALL = API + "/payment/get-all";
    String PAYMENT_GET_UUID = API + "/payment/get-by-uuid";
    String PAYMENT_CREATE = API + "/payment/create";
    String PAYMENT_DELETE = API + "/payment/delete";
    String PAYMENT_UPDATE = API + "/payment/update";
    String PAYMENT_PERFORM_LOCK = API + "/payment/perform-lock";

    //Product
    String PRODUCT_GET_ALL = API + "/product/get-all";
    String PRODUCT_GET_UUID = API + "/product/get-by-uuid";
    String PRODUCT_GET_CATE_ID = API + "/product/get-by-cateId";
    String PRODUCT_CREATE = API + "/product/create";
    String PRODUCT_DELETE = API + "/product/delete";
    String PRODUCT_UPDATE = API + "/product/update";
    String PRODUCT_PERFORM_LOCK = API + "/product/perform-lock";

    // upload file
    String UPLOAD_FILE = API + "/upload-file";
}
