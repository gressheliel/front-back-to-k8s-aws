CREATE DATABASE restaurantdb;
USE restaurantdb;

CREATE TABLE `restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `restaurant_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('123 Main Street', 'Anytown', 'The Hungry Hut', 'A cozy family-owned restaurant offering a diverse menu of international cuisines.From sizzling steaks to mouthwatering pasta dishes, we have something to satisfy every craving');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('456 Elm Avenue', 'Townsville', 'Spice Fusion', 'Experience the vibrant flavors of India at Spice Fusion. Our talented chefs blend traditional Indian spices with a modern twist, creating a culinary journey that will delight your taste buds');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('789 Oak Lane', 'Metropolis', 'Seafood Paradise', 'Indulge in the freshest catch of the day at Seafood Paradise. With a prime waterfront location, our restaurant offers a wide selection of seafood delicacies, prepared with a touch of coastal flair');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('321 Maple Road', 'Riverside', 'Tandoori Delight', 'Step into a slice of Italy at La Bella Trattoria. Our charming trattoria-style restaurant serves authentic Italian dishes made from scratch using the finest ingredients. Buon appetito');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('987 Pine Street', 'Lakeside', 'The Green Garden', 'Embrace healthy eating at The Green Garden. Our vegetarian and vegan-friendly restaurant showcases the best of plant-based cuisine, with colorful salads, hearty bowls, and nutritious smoothies.');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('555 Chestnut Avenue', 'Texas', 'Curry House', 'The Savory Bistro offers an exquisite dining experience with a focus on seasonal, locally sourced ingredients. Our talented chefs create artful plates that blend flavors from around the world, ensuring a memorable culinary adventure');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('222 Walnut Street', 'Belleview', 'Saffron Sizzlers', 'Sushi Haven invites you to savor the art of Japanese cuisine. Our skilled sushi chefs meticulously craft an array of fresh sushi rolls, sashimi, and delectable tempura, all served in a modern and elegant setting');
INSERT INTO restaurant (address, city, name, restaurant_description) VALUES ('777 Oakwood Drive', 'Harborview', 'Fireside Grill & Bar', 'Set against a rustic backdrop, Fireside Grill & Bar is the perfect spot for a cozy meal. Our menu features hearty grilled favorites, signature cocktails, and a warm ambiance that will make you feel right at home');


CREATE DATABASE userdb;
USE userdb;

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERt INTO user (address, city, user_name, user_password) VALUES ('Guanajuato 198', 'Estado de Mexico', 'gressheliel', 'elieta103');

CREATE DATABASE foodcataloguedb;
USE foodcataloguedb;

CREATE TABLE `food_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_veg` bit(1) NOT NULL,
  `item_description` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `restaurant_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Delicious vegetarian dish', 'Vegetable Biryani', 300, 0, 1);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Succulent chicken in a creamy sauce', 'Butter Chicken', 310, 0, 1);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Mouthwatering lentil curry', 'Dal Tadka', 350, 0, 2);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Spicy and flavorful lamb curry', 'Rogan Josh', 315, 0, 2);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Crispy and spicy potato patties', 'Aloo Tikki', 500, 0, 3);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Paneer cubes marinated in tandoori spices', 'Tandoori Paneer Tikka', 900, 0, 3);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Fragrant and aromatic rice dish', 'Chicken Biryani', 120, 0, 4);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Mixed vegetable curry', 'Vegetable Korma', 110, 0, 4);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Spicy and tangy shrimp curry', 'Goan Prawn Curry', 140, 0, 5);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Fluffy Indian bread', 'Naan', 300, 0, 5);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Chicken marinated in yogurt and spices', 'Chicken Tikka', 100, 0, 6);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Aromatic rice pudding', 'Kheer', 600, 0, 6);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Savory lentil donuts', 'Medu Vada', 400, 0, 7);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Crispy crepe filled with spiced potatoes', 'Masala Dosa', 800, 0, 7);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Refreshing yogurt-based drink', 'Mango Lassi', 500, 0, 8);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'0', 'Assorted Indian bread basket', 'Basket of Rotis', 700, 0, 8);
INSERT INTO food_item (is_veg, item_description, item_name, price, quantity, restaurant_id) VALUES (b'1', 'Lentils cooked with mixed spices', 'Dal Fry', 350, 0, 1);
