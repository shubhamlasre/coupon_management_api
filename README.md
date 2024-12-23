# coupon_management_api

====== Implemented Classes ===========

- Controller Classes:

* CouponController : Contains APIs for creating, fetching, updating and deleting coupons.
* CartController: Contains API for fetching applicable coupons based on cart and another API for applying coupon based on cart

- Service Classes:

* CartWiseCouponServiceImpl: Class for creating Cart-Wise coupons contains single method for creation.
* ProductWiseCouponServiceImpl: Class for creating Product-Wise coupons contains single method for creation.
* BxGyCouponServiceImpl: Class for creating Buy and Get coupons contains single method for creation.
* RetrieveCouponServiceImpl: Class for retreiving coupons, contains 2 methods first fetchAllCoupon() to fetch all coupon and another method fetchCoupon() to fetch coupons based on coupon id.
* UpdateCouponServiceImpl: Class for Updating Coupons, contains 2 method updateCoupon() and deleteCoupon() to update and delete respectively.
* ApplyCouponServiceImpl: Class for applying coupons on cart, conatins 4 methods. Method applyCouponOnCart() internally calls other methods getCartResultForCartWise(), getCartResultForProductWise() and getCartResultForBuyAndGet() based on coupon type applied and fetched the final cart along with discount and final price.
* ApplicableCouponServiceImpl: Class for fetching coupons which are applicable on the cart. contains single method.

- Utility classes

* couponUtility: Conatins method to generated coupon descriptions based on type of coupons. contains 3 methods each for repective coupon type.

- Domain/Entity Classes and Repository

* Coupon: Entity class along with its repository to store coupon details.
* Product: Entity class along with its repository to store Product details.
* CreationCriteria: Domain class which is used as an input to create coupons.
* ProductDetail: It is used inside CreationCriteria to store inforrmation about purchased and free available product.
* CouponForCart: It is Entity class along with its repository to store Cart-Wise details.
* ProductCouponMap: It is ENtity class along with its repository to store Product-Wise coupon details.
* BuyAndGetCouponMap: It is Entity class along with its repository to Store BuyandGet Coupon details.
* CouponSearchResult: It is Domain class which is returned as a result of applicable coupons. conatins list of coupons.
* Cart: It is Domain class which conatins list of products as items.
* CartDetails: It is a domain class which is used as input for searching applicable coupons and applying coupons on cart. It contains Cart class.
* CartResult:It is a domain class which is returened afteer application of coupon on a cart.

================== Limitation ================

- If a Buy and Get coupon has multiple get offer for a Buy offer for example: buy 2 X or 3 Y and get 2 A or 1 B. For this scenario there is no implementation.

================= Assumption ======================

1. For multiple buy offer only single type of product is available. Example buy 2 X and 3 Y and get 2 C is fine but buy 2 X or 3 Y and get 2 A or 1 B is an invalid scenario. No exceptions will be thrown but only 1st type of free product can be availed, in above scenarion only 2A can be availed but not 1 B.
2. Product contained in cart are all available.
   3 Inserted 10 values of products in product table for checking basic scenario.
3. Only coupons which resulted in applicable coupons are applied to the cart to get final price and discount.
4. All coupons have an expiry date of 1 year as per my, according to business need we can change later;
