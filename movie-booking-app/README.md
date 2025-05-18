先根據網路上的教學安裝 [MySQL 資料庫](https://chwang12341.medium.com/mysql-%E5%AD%B8%E7%BF%92%E7%AD%86%E8%A8%98-%E4%BA%8C-%E4%B8%80%E5%88%86%E9%90%98%E8%BC%95%E9%AC%86%E7%9E%AD%E8%A7%A3%E5%A6%82%E4%BD%95%E5%9C%A8windows%E4%B8%8A%E5%AE%89%E8%A3%9Dmysql-63cce07c6a6c)

**安裝時，請將 username 和 password 都設置成 `root`。

安裝好後，按照像是上面教學所提到的打開 MySQL Command Line Client。
先確認這個指令

```mysql
show databases;
```

有沒有出現 Database，有的話就可以使用

```mysql
create database movie_booking;
```

來建立我們要的資料庫，建立完後用

```mysql
show databases;
```

確認是否完成建立。

完成後就可以嘗試執行專案啦！

基本上就是找到 `movie-booking-app/src/main/java/com/javaoop/movie_booking_app/MovieBookingAppApplication.java`，然後像是在寫作業的時候直接執行那個 class 或 Main()。

執行起來如果程式沒有終止掉（也就是紅色的停止鍵還亮著），那就能打開瀏覽器輸入網址 [localhost:8080/movies](http://localhost:8080) 看到結果囉。
