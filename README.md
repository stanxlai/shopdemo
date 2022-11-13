# Getting Started

### JDK

17.0

### shop.postman_collection.json

postman v2.1

使用postman刪除會員後再查詢會有cache住的情形發生，點其他 url 後再回點便會有正確結果。

### 下訂單

一張單一個商品。(未來可視營業狀況來擴展))

### Thread safe

記得Vector及 Hashtable是 thread safe的，而 ArrayList 不是，而我使用ArrayList，在add時我有加synchronized的用法，我認為ArrayList在add時空間不夠時會擴展它陣列的大小。也許該注意吧。

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
