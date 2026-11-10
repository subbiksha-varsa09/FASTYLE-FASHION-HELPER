CREATE DATABASE fastyle;
show databases;

USE fastyle;

SHOW TABLES;

#Table 1: Users
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DESCRIBE users;

#able 2: WardrobeItems
CREATE TABLE WardrobeItems (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    item_category ENUM('Clothing','Footwear','Accessory','Bag') NOT NULL,
    item_type VARCHAR(50) NOT NULL,
    suitable_for ENUM('Men','Women','Unisex') DEFAULT 'Unisex',
    color VARCHAR(30),
    material VARCHAR(50),
    occasion VARCHAR(100),
    comfort_level ENUM('Low','Medium','High'),
    weather_type ENUM('Hot','cold','Rainy','Windy','All Weather') DEFAULT 'All Weather',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

DESCRIBE wardrobeItems;

#Table 3: Outfits
CREATE TABLE Outfits (
    outfit_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    outfit_name VARCHAR(100),
    occasion VARCHAR(50),
    weather_type VARCHAR(50),
    color_theme VARCHAR(50),
    comfort_level VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

#Table 4: OutfitItems
CREATE TABLE OutfitItems (
    outfit_item_id INT PRIMARY KEY AUTO_INCREMENT,
    outfit_id INT NOT NULL,
    item_id INT NOT NULL,
    FOREIGN KEY (outfit_id) REFERENCES Outfits(outfit_id) ON DELETE CASCADE,
	FOREIGN KEY (item_id) REFERENCES WardrobeItems(item_id) ON DELETE CASCADE,
    UNIQUE (outfit_id, item_id)
);
#Table 5: UserPreferences
CREATE TABLE UserPreferences (
    preference_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE,
    preferred_style VARCHAR(50),
    preferred_color VARCHAR(50),
    preferred_fit VARCHAR(50),
    preferred_comfort ENUM('Low','Medium','High'),
    preferred_material VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);
#Table 6: WeatherPreferences
CREATE TABLE WeatherPreferences (
    weather_preference_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    weather_type ENUM(
        'Hot',
        'Cold',
        'Rainy',
        'Windy',
        'All Weather'
    ) NOT NULL,
    preferred_clothing VARCHAR(100),
    preferred_material VARCHAR(50),
    comfort_priority ENUM(
        'Low',
        'Medium',
        'High'
    ) DEFAULT 'Medium',

    FOREIGN KEY (user_id)
        REFERENCES Users(user_id)
        ON DELETE CASCADE,

    UNIQUE (user_id, weather_type)
);
#Table 7: Favorites
CREATE TABLE Favorites (
    favorite_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    item_id INT,
    outfit_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES Users(user_id)
        ON DELETE CASCADE,

    FOREIGN KEY (item_id)
        REFERENCES WardrobeItems(item_id)
        ON DELETE CASCADE,

    FOREIGN KEY (outfit_id)
        REFERENCES Outfits(outfit_id)
        ON DELETE CASCADE,

    CHECK (
        (item_id IS NOT NULL AND outfit_id IS NULL)
        OR
        (item_id IS NULL AND outfit_id IS NOT NULL)
    )
);

#Table 8: OutfitHistory
CREATE TABLE OutfitHistory (
    history_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    outfit_id INT NOT NULL,
    action_type ENUM(
        'Generated',
        'Selected',
        'Worn'
    ) DEFAULT 'Generated',
    action_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES Users(user_id)
        ON DELETE CASCADE,

    FOREIGN KEY (outfit_id)
        REFERENCES Outfits(outfit_id)
        ON DELETE CASCADE
);


INSERT INTO Users (user_name, email, password_hash)
VALUES ('Sharmili', 'sharmili@example.com', NULL);


INSERT INTO WardrobeItems
(user_id, item_name, item_category, item_type,
 suitable_for, color, material, occasion,
 comfort_level, weather_type)
VALUES

-- =========================
-- TOPS
-- =========================

(1, 'Classic Cotton T-Shirt', 'Clothing', 'T-Shirt',
 'Unisex', 'White', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Oversized T-Shirt', 'Clothing', 'T-Shirt',
 'Unisex', 'Black', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Graphic T-Shirt', 'Clothing', 'T-Shirt',
 'Unisex', 'Blue', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Formal Shirt', 'Clothing', 'Shirt',
 'Men', 'White', 'Cotton', 'Office', 'High', 'All Weather'),

(1, 'Checked Shirt', 'Clothing', 'Shirt',
 'Unisex', 'Blue', 'Cotton', 'Casual', 'High', 'All Weather'),

(1, 'Denim Shirt', 'Clothing', 'Shirt',
 'Unisex', 'Blue', 'Denim', 'Casual', 'Medium', 'Cold'),

(1, 'Crop Top', 'Clothing', 'Crop Top',
 'Women', 'Pink', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Floral Top', 'Clothing', 'Top',
 'Women', 'Yellow', 'Rayon', 'Casual', 'High', 'Hot'),

(1, 'Tank Top', 'Clothing', 'Tank Top',
 'Unisex', 'Black', 'Cotton', 'Sports', 'High', 'Hot'),

(1, 'Polo T-Shirt', 'Clothing', 'Polo Shirt',
 'Unisex', 'Navy Blue', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Casual Hoodie', 'Clothing', 'Hoodie',
 'Unisex', 'Grey', 'Fleece', 'Casual', 'High', 'Cold'),

(1, 'Zip-Up Hoodie', 'Clothing', 'Hoodie',
 'Unisex', 'Black', 'Cotton', 'Casual', 'High', 'Cold'),

(1, 'Knitted Sweater', 'Clothing', 'Sweater',
 'Unisex', 'Beige', 'Wool', 'Casual', 'High', 'Cold'),

(1, 'Classic Sweatshirt', 'Clothing', 'Sweatshirt',
 'Unisex', 'Green', 'Cotton', 'Casual', 'High', 'Cold'),

(1, 'Traditional Kurta Top', 'Clothing', 'Kurta Top',
 'Unisex', 'Cream', 'Cotton', 'Festival', 'High', 'Hot'),

(1, 'Long Tunic', 'Clothing', 'Tunic',
 'Women', 'Purple', 'Rayon', 'Casual', 'High', 'Hot'),

-- =========================
-- BOTTOMS
-- =========================

(1, 'Classic Blue Jeans', 'Clothing', 'Jeans',
 'Unisex', 'Blue', 'Denim', 'Casual', 'High', 'All Weather'),

(1, 'Black Straight Jeans', 'Clothing', 'Jeans',
 'Unisex', 'Black', 'Denim', 'Casual', 'High', 'All Weather'),

(1, 'Wide-Leg Jeans', 'Clothing', 'Jeans',
 'Unisex', 'Blue', 'Denim', 'Casual', 'High', 'Hot'),

(1, 'Formal Trousers', 'Clothing', 'Trousers',
 'Unisex', 'Black', 'Polyester', 'Office', 'Medium', 'All Weather'),

(1, 'Beige Chinos', 'Clothing', 'Chinos',
 'Unisex', 'Beige', 'Cotton', 'Office', 'High', 'Hot'),

(1, 'Olive Cargo Pants', 'Clothing', 'Cargo Pants',
 'Unisex', 'Olive', 'Cotton', 'Casual', 'High', 'All Weather'),

(1, 'Cotton Shorts', 'Clothing', 'Shorts',
 'Unisex', 'Grey', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Sports Joggers', 'Clothing', 'Joggers',
 'Unisex', 'Black', 'Polyester', 'Sports', 'High', 'All Weather'),

(1, 'Track Pants', 'Clothing', 'Track Pants',
 'Unisex', 'Navy Blue', 'Polyester', 'Sports', 'High', 'All Weather'),

(1, 'Casual Leggings', 'Clothing', 'Leggings',
 'Women', 'Black', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Pleated Skirt', 'Clothing', 'Skirt',
 'Women', 'Black', 'Polyester', 'Casual', 'High', 'Hot'),

(1, 'Long Palazzos', 'Clothing', 'Palazzos',
 'Women', 'Cream', 'Rayon', 'Casual', 'High', 'Hot'),

(1, 'Formal Slim-Fit Pants', 'Clothing', 'Formal Pants',
 'Men', 'Navy Blue', 'Polyester', 'Office', 'Medium', 'All Weather'),

-- =========================
-- DRESSES AND ONE-PIECE
-- =========================

(1, 'Floral Summer Dress', 'Clothing', 'Casual Dress',
 'Women', 'Yellow', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Black Midi Dress', 'Clothing', 'Midi Dress',
 'Women', 'Black', 'Rayon', 'Party', 'Medium', 'All Weather'),

(1, 'Maxi Dress', 'Clothing', 'Maxi Dress',
 'Women', 'Blue', 'Chiffon', 'Party', 'Medium', 'Hot'),

(1, 'Formal Shirt Dress', 'Clothing', 'Shirt Dress',
 'Women', 'White', 'Cotton', 'Office', 'High', 'Hot'),

(1, 'Evening Gown', 'Clothing', 'Gown',
 'Women', 'Maroon', 'Satin', 'Party', 'Medium', 'Cold'),

(1, 'Casual Jumpsuit', 'Clothing', 'Jumpsuit',
 'Unisex', 'Black', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Traditional Ethnic Outfit', 'Clothing', 'Traditional Dress',
 'Women', 'Red', 'Silk', 'Festival', 'Medium', 'All Weather'),

(1, 'Casual Co-ord Set', 'Clothing', 'Co-ord Set',
 'Unisex', 'Beige', 'Cotton', 'Casual', 'High', 'Hot'),

-- =========================
-- FOOTWEAR
-- =========================

(1, 'White Sneakers', 'Footwear', 'Sneakers',
 'Unisex', 'White', 'Canvas', 'Casual', 'High', 'All Weather'),

(1, 'Black Sneakers', 'Footwear', 'Sneakers',
 'Unisex', 'Black', 'Mesh', 'Casual', 'High', 'All Weather'),

(1, 'Running Shoes', 'Footwear', 'Running Shoes',
 'Unisex', 'Grey', 'Mesh', 'Sports', 'High', 'Hot'),

(1, 'Formal Oxford Shoes', 'Footwear', 'Formal Shoes',
 'Unisex', 'Black', 'Leather', 'Office', 'Medium', 'All Weather'),

(1, 'Brown Loafers', 'Footwear', 'Loafers',
 'Unisex', 'Brown', 'Leather', 'Office', 'High', 'All Weather'),

(1, 'Casual Sandals', 'Footwear', 'Sandals',
 'Unisex', 'Brown', 'Leather', 'Casual', 'High', 'Hot'),

(1, 'Flip-Flops', 'Footwear', 'Flip-Flops',
 'Unisex', 'Blue', 'Rubber', 'Casual', 'High', 'Hot'),

(1, 'Ankle Boots', 'Footwear', 'Boots',
 'Unisex', 'Black', 'Leather', 'Casual', 'Medium', 'Cold'),

(1, 'Block Heels', 'Footwear', 'Heels',
 'Women', 'Beige', 'Leather', 'Party', 'Medium', 'All Weather'),

(1, 'Ballet Flats', 'Footwear', 'Flats',
 'Women', 'Pink', 'Leather', 'Casual', 'High', 'Hot'),

(1, 'Traditional Ethnic Footwear', 'Footwear', 'Ethnic Footwear',
 'Unisex', 'Gold', 'Leather', 'Festival', 'Medium', 'All Weather'),

-- =========================
-- ACCESSORIES
-- =========================

(1, 'Black Sunglasses', 'Accessory', 'Sunglasses',
 'Unisex', 'Black', 'Plastic', 'Casual', 'High', 'Hot'),

(1, 'Aviator Sunglasses', 'Accessory', 'Sunglasses',
 'Unisex', 'Gold', 'Metal', 'Casual', 'High', 'Hot'),

(1, 'Classic Wristwatch', 'Accessory', 'Watch',
 'Unisex', 'Black', 'Metal', 'Office', 'High', 'All Weather'),

(1, 'Smartwatch', 'Accessory', 'Smartwatch',
 'Unisex', 'Black', 'Silicone', 'Sports', 'High', 'All Weather'),

(1, 'Cotton Scarf', 'Accessory', 'Scarf',
 'Unisex', 'Beige', 'Cotton', 'Casual', 'High', 'Cold'),

(1, 'Printed Bandana', 'Accessory', 'Bandana',
 'Unisex', 'Red', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Classic Leather Belt', 'Accessory', 'Belt',
 'Unisex', 'Brown', 'Leather', 'Office', 'High', 'All Weather'),

(1, 'Silver Bracelet', 'Accessory', 'Bracelet',
 'Unisex', 'Silver', 'Metal', 'Party', 'High', 'All Weather'),

(1, 'Silver Chain', 'Accessory', 'Chain',
 'Unisex', 'Silver', 'Metal', 'Casual', 'High', 'All Weather'),

(1, 'Classic Ring', 'Accessory', 'Ring',
 'Unisex', 'Gold', 'Metal', 'Party', 'High', 'All Weather'),

(1, 'Baseball Cap', 'Accessory', 'Cap',
 'Unisex', 'Black', 'Cotton', 'Casual', 'High', 'Hot'),

(1, 'Knitted Beanie', 'Accessory', 'Beanie',
 'Unisex', 'Grey', 'Wool', 'Casual', 'High', 'Cold'),

(1, 'Formal Cufflinks', 'Accessory', 'Cufflinks',
 'Unisex', 'Silver', 'Metal', 'Office', 'High', 'All Weather'),

(1, 'Classic Necktie', 'Accessory', 'Tie',
 'Unisex', 'Navy Blue', 'Silk', 'Office', 'High', 'All Weather'),

(1, 'Pocket Square', 'Accessory', 'Pocket Square',
 'Unisex', 'White', 'Cotton', 'Office', 'High', 'All Weather'),

(1, 'Winter Gloves', 'Accessory', 'Gloves',
 'Unisex', 'Black', 'Wool', 'Casual', 'High', 'Cold'),

(1, 'Pearl Earrings', 'Accessory', 'Earrings',
 'Unisex', 'White', 'Pearl', 'Party', 'High', 'All Weather'),

(1, 'Decorative Brooch', 'Accessory', 'Brooch',
 'Unisex', 'Gold', 'Metal', 'Party', 'High', 'All Weather'),

(1, 'Hair Band', 'Accessory', 'Hair Accessories',
 'Unisex', 'Pink', 'Plastic', 'Casual', 'High', 'All Weather'),

(1, 'Bow Tie', 'Accessory', 'Bow Tie',
 'Unisex', 'Black', 'Silk', 'Party', 'High', 'All Weather'),

-- =========================
-- BAGS
-- =========================

(1, 'Everyday Backpack', 'Bag', 'Backpack',
 'Unisex', 'Black', 'Canvas', 'College', 'High', 'All Weather'),

(1, 'Laptop Bag', 'Bag', 'Laptop Bag',
 'Unisex', 'Grey', 'Nylon', 'Office', 'High', 'All Weather'),

(1, 'Canvas Tote Bag', 'Bag', 'Tote Bag',
 'Unisex', 'Beige', 'Canvas', 'Casual', 'High', 'Hot'),

(1, 'Crossbody Bag', 'Bag', 'Crossbody Bag',
 'Unisex', 'Brown', 'Leather', 'Casual', 'High', 'All Weather'),

(1, 'Sling Bag', 'Bag', 'Sling Bag',
 'Unisex', 'Black', 'Nylon', 'Casual', 'High', 'All Weather'),

(1, 'Travel Duffel Bag', 'Bag', 'Duffel Bag',
 'Unisex', 'Navy Blue', 'Polyester', 'Travel', 'High', 'All Weather'),

(1, 'Classic Handbag', 'Bag', 'Handbag',
 'Women', 'Brown', 'Leather', 'Office', 'Medium', 'All Weather'),

(1, 'Party Clutch', 'Bag', 'Clutch',
 'Unisex', 'Gold', 'Satin', 'Party', 'Medium', 'All Weather'),

(1, 'Messenger Bag', 'Bag', 'Messenger Bag',
 'Unisex', 'Brown', 'Canvas', 'College', 'High', 'All Weather'),

(1, 'Travel Suitcase', 'Bag', 'Travel Bag',
 'Unisex', 'Black', 'Polyester', 'Travel', 'High', 'All Weather');
SELECT item_category, COUNT(*) AS total_items
FROM WardrobeItems
GROUP BY item_category;
SELECT *
FROM WardrobeItems
ORDER BY item_category, item_type, item_name;

#Next step: Let's add user preferences
INSERT INTO UserPreferences
(user_id, preferred_style, preferred_color,
 preferred_fit, preferred_comfort, preferred_material)
VALUES
(1, 'Casual', 'Black', 'Regular', 'High', 'Cotton');
INSERT INTO WeatherPreferences
(user_id, weather_type, preferred_clothing,
 preferred_material, comfort_priority)
VALUES
(1, 'Hot', 'T-Shirts and Cotton Tops', 'Cotton', 'High'),
(1, 'Cold', 'Hoodies and Sweaters', 'Wool', 'High'),
(1, 'Rainy', 'Lightweight Clothing', 'Polyester', 'High'),
(1, 'Windy', 'Jackets and Full-Sleeve Tops', 'Cotton', 'Medium'),
(1, 'All Weather', 'Comfortable Everyday Clothing', 'Cotton', 'High');
SELECT * FROM UserPreferences;
SELECT * FROM WeatherPreferences;
DESCRIBE userpreferences;
DESCRIBE wardrobeitems;
USE fastyle;
DESCRIBE outfits;
UPDATE wardrobeitems
SET color = 'Blue'
WHERE item_id = 80 AND user_id = 2;
SELECT item_id, item_name, color
FROM wardrobeitems
WHERE item_id = 80 AND user_id = 2;
DESCRIBE outfithistory;
DESCRIBE weatherpreferences;
DESCRIBE outfititems;

SELECT *
FROM outfits
WHERE outfit_id = 6;

SELECT oi.outfit_id, oi.item_id, w.item_name, w.color
FROM outfititems oi
JOIN wardrobeitems w ON oi.item_id = w.item_id
WHERE oi.outfit_id = 6;

USE fastyle;

SELECT user_id, user_name, email,
       password_hash
FROM users
WHERE user_name = 'sharmili';

USE fastyle;

DESCRIBE users;

USE fastyle;

delete FROM users
WHERE email = 'sowmya@example.com';


select * from users;

ALTER TABLE user_7_wardrobeitems
MODIFY COLUMN user_id INT NULL;

drop table user_7_weatherpreferences;

USE fastyle;

CREATE TABLE user_8_favorites LIKE favorites;

SHOW TABLES LIKE 'user_1_%';

show tables;

RENAME TABLE weatherpreferences TO user_1_weatherpreferences;


SELECT *
FROM user_8_wardrobeitems;


INSERT INTO user_8_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES
(8, 'pink blouse', 'Clothing', 'blouse', 'Women',
 'pink', 'cotton', 'casual', 'High', 'Hot'),

(8, 'yellow kurti', 'Clothing', 'kurti', 'Women',
 'yellow', 'cotton', 'casual', 'High', 'Hot'),

(8, 'green kurti', 'Clothing', 'kurti', 'Women',
 'green', 'rayon', 'formal', 'Medium', 'Hot'),

(8, 'black t-shirt', 'Clothing', 't-shirt', 'Women',
 'black', 'cotton', 'casual', 'High', 'All Weather'),

(8, 'white blouse', 'Clothing', 'blouse', 'Women',
 'white', 'linen', 'formal', 'Medium', 'Hot'),

(8, 'blue denim skirt', 'Clothing', 'skirt', 'Women',
 'blue', 'denim', 'casual', 'Medium', 'All Weather'),

(8, 'black trousers', 'Clothing', 'trousers', 'Women',
 'black', 'polyester', 'formal', 'Medium', 'All Weather'),

(8, 'white trousers', 'Clothing', 'trousers', 'Women',
 'white', 'cotton', 'formal', 'Medium', 'Hot'),

(8, 'floral summer dress', 'Clothing', 'dress', 'Women',
 'pink', 'cotton', 'party', 'High', 'Hot'),

(8, 'navy blue blazer', 'Clothing', 'blazer', 'Women',
 'navy', 'polyester', 'formal', 'Medium', 'Cold'),

(8, 'brown sandals', 'Footwear', 'sandals', 'Women',
 'brown', 'leather', 'casual', 'High', 'Hot'),

(8, 'black sneakers', 'Footwear', 'sneakers', 'Women',
 'black', 'canvas', 'casual', 'High', 'All Weather'),

(8, 'silver heels', 'Footwear', 'heels', 'Women',
 'silver', 'synthetic', 'party', 'Medium', 'All Weather'),

(8, 'brown loafers', 'Footwear', 'loafers', 'Women',
 'brown', 'leather', 'formal', 'Medium', 'All Weather'),

(8, 'pink handbag', 'Bag', 'handbag', 'Women',
 'pink', 'leather', 'party', 'Medium', 'All Weather'),

(8, 'blue backpack', 'Bag', 'backpack', 'Women',
 'blue', 'nylon', 'casual', 'High', 'Rainy'),

(8, 'black belt', 'Accessory', 'belt', 'Women',
 'black', 'leather', 'formal', 'Medium', 'All Weather'),

(8, 'gold necklace', 'Accessory', 'necklace', 'Women',
 'gold', 'metal', 'party', 'Medium', 'All Weather'),

(8, 'silver earrings', 'Accessory', 'earrings', 'Women',
 'silver', 'metal', 'party', 'High', 'All Weather'),

(8, 'beige scarf', 'Accessory', 'scarf', 'Women',
 'beige', 'wool', 'casual', 'High', 'Cold');
 
 
USE fastyle;

INSERT INTO user_8_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES
(8, 'denim jacket', 'Clothing', 'jacket', 'Women',
 'blue', 'denim', 'casual', 'Medium', 'Cold'),
(8, 'leather jacket', 'Clothing', 'jacket', 'Women',
 'black', 'leather', 'casual', 'Medium', 'Cold'),
(8, 'long cardigan', 'Clothing', 'cardigan', 'Women',
 'cream', 'wool', 'casual', 'High', 'Cold'),
(8, 'printed maxi dress', 'Clothing', 'dress', 'Women',
 'purple', 'rayon', 'party', 'High', 'Hot'),
(8, 'cotton salwar suit', 'Clothing', 'salwar suit', 'Women',
 'green', 'cotton', 'traditional', 'High', 'Hot'),
(8, 'silk saree', 'Clothing', 'saree', 'Women',
 'maroon', 'silk', 'traditional', 'Medium', 'All Weather'),
(8, 'white shorts', 'Clothing', 'shorts', 'Women',
 'white', 'cotton', 'casual', 'High', 'Hot'),
(8, 'grey joggers', 'Clothing', 'joggers', 'Women',
 'grey', 'cotton', 'sports', 'High', 'All Weather'),
(8, 'striped polo shirt', 'Clothing', 'polo shirt', 'Women',
 'navy', 'cotton', 'casual', 'High', 'Hot'),
(8, 'maroon sweater', 'Clothing', 'sweater', 'Women',
 'maroon', 'wool', 'casual', 'High', 'Cold'),
(8, 'brown ankle boots', 'Footwear', 'boots', 'Women',
 'brown', 'leather', 'casual', 'Medium', 'Cold'),
(8, 'white running shoes', 'Footwear', 'running shoes', 'Women',
 'white', 'mesh', 'sports', 'High', 'All Weather'),
(8, 'black wedges', 'Footwear', 'wedges', 'Women',
 'black', 'synthetic', 'party', 'Medium', 'Hot'),
(8, 'transparent raincoat', 'Clothing', 'raincoat', 'Women',
 'clear', 'plastic', 'casual', 'High', 'Rainy'),
(8, 'grey beanie', 'Accessory', 'beanie', 'Women',
 'grey', 'wool', 'casual', 'High', 'Cold'),
(8, 'brown sunglasses', 'Accessory', 'sunglasses', 'Women',
 'brown', 'plastic', 'casual', 'High', 'Hot'),
(8, 'silver bracelet', 'Accessory', 'bracelet', 'Women',
 'silver', 'metal', 'party', 'Medium', 'All Weather'),
(8, 'black wristwatch', 'Accessory', 'watch', 'Women',
 'black', 'metal', 'formal', 'Medium', 'All Weather'),
(8, 'purple clutch bag', 'Bag', 'clutch', 'Women',
 'purple', 'synthetic', 'party', 'Medium', 'All Weather'),
(8, 'green tote bag', 'Bag', 'tote bag', 'Women',
 'green', 'canvas', 'casual', 'High', 'All Weather');
 

USE fastyle;

SHOW COLUMNS FROM user_1_outfithistory ;


USE fastyle;

ALTER TABLE user_1_outfithistory
MODIFY COLUMN action_type
ENUM('Generated', 'Selected', 'Worn', 'SAVED', 'VIEWED')
DEFAULT 'Generated';


SHOW TABLES LIKE 'user_10_%';
DESCRIBE user_1_outfits;
select * from user_1_outfititems;
select * from users;
SELECT * FROM user_1_wardrobeitems;
show tables;


CREATE TABLE IF NOT EXISTS user_10_wardrobeitems LIKE user_1_wardrobeitems;
CREATE TABLE IF NOT EXISTS user_10_favorites LIKE user_1_favorites;
CREATE TABLE IF NOT EXISTS user_10_outfits LIKE user_1_outfits;
CREATE TABLE IF NOT EXISTS user_10_outfititems LIKE user_1_outfititems;
CREATE TABLE IF NOT EXISTS user_10_outfithistory LIKE user_1_outfithistory;
CREATE TABLE IF NOT EXISTS user_10_userpreferences LIKE user_1_userpreferences;
CREATE TABLE IF NOT EXISTS user_10_weatherpreferences LIKE user_1_weatherpreferences;

USE fastyle;

INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES
(10, 'Black T-Shirt', 'Clothing', 'T-Shirt', 'Unisex',
 'Black', 'Cotton', 'Casual', 'High', 'Hot'),

(10, 'Blue Jeans', 'Clothing', 'Jeans', 'Unisex',
 'Blue', 'Denim', 'Casual', 'High', 'All Weather'),

(10, 'White Formal Shirt', 'Clothing', 'Shirt', 'Unisex',
 'White', 'Cotton', 'Formal', 'Medium', 'Hot'),

(10, 'Black Formal Trousers', 'Clothing', 'Trousers', 'Unisex',
 'Black', 'Polyester', 'Formal', 'Medium', 'All Weather'),

(10, 'Red Party Dress', 'Clothing', 'Dress', 'Women',
 'Red', 'Silk', 'Party', 'Medium', 'Hot'),

(10, 'Yellow Kurti', 'Clothing', 'Kurti', 'Women',
 'Yellow', 'Cotton', 'Casual', 'High', 'Hot'),

(10, 'Blue Hoodie', 'Clothing', 'Hoodie', 'Unisex',
 'Blue', 'Wool', 'Casual', 'High', 'Cold'),

(10, 'Black Jacket', 'Clothing', 'Jacket', 'Unisex',
 'Black', 'Leather', 'Travel', 'Medium', 'Cold'),

(10, 'Green Sports T-Shirt', 'Clothing', 'Sports T-Shirt', 'Unisex',
 'Green', 'Polyester', 'Sports', 'High', 'Hot'),

(10, 'Grey Track Pants', 'Clothing', 'Track Pants', 'Unisex',
 'Grey', 'Cotton', 'Sports', 'High', 'All Weather'),

(10, 'Floral Summer Dress', 'Clothing', 'Dress', 'Women',
 'Pink', 'Cotton', 'Casual', 'High', 'Hot'),

(10, 'Brown Sweater', 'Clothing', 'Sweater', 'Unisex',
 'Brown', 'Wool', 'Casual', 'High', 'Cold'),

(10, 'Raincoat', 'Clothing', 'Raincoat', 'Unisex',
 'Blue', 'Polyester', 'Travel', 'Medium', 'Rainy'),

(10, 'Black Blazer', 'Clothing', 'Blazer', 'Unisex',
 'Black', 'Polyester', 'Formal', 'Medium', 'All Weather'),

(10, 'White Party Shirt', 'Clothing', 'Shirt', 'Unisex',
 'White', 'Linen', 'Party', 'High', 'Hot');
 
 USE fastyle;

INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES

(10, 'Black Party Top', 'Clothing', 'Top', 'Women',
 'Black', 'Silk', 'Party', 'High', 'All Weather'),

(10, 'Black Party Pants', 'Clothing', 'Pant', 'Women',
 'Black', 'Polyester', 'Party', 'High', 'All Weather'),

(10, 'Black Party Heels', 'Footwear', 'Heels', 'Women',
 'Black', 'Leather', 'Party', 'Medium', 'All Weather'),

(10, 'Silver Party Accessories', 'Accessory', 'Jewellery', 'Women',
 'Silver', 'Metal', 'Party', 'High', 'All Weather');
 
 USE fastyle;

INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES

-- Earrings
(10, 'Gold Hoop Earrings', 'Accessory', 'Earrings', 'Women',
 'Gold', 'Metal', 'Party', 'High', 'All Weather'),

(10, 'Pearl Stud Earrings', 'Accessory', 'Earrings', 'Women',
 'White', 'Pearl', 'Formal', 'High', 'All Weather'),

(10, 'Silver Jhumka', 'Accessory', 'Earrings', 'Women',
 'Silver', 'Metal', 'Party', 'Medium', 'All Weather'),

(10, 'Diamond Drop Earrings', 'Accessory', 'Earrings', 'Women',
 'Silver', 'Stone', 'Party', 'Medium', 'All Weather'),

-- Necklaces and chains
(10, 'Gold Pendant Necklace', 'Accessory', 'Necklace', 'Women',
 'Gold', 'Metal', 'Party', 'High', 'All Weather'),

(10, 'Silver Chain', 'Accessory', 'Chain', 'Unisex',
 'Silver', 'Metal', 'Casual', 'High', 'All Weather'),

(10, 'Pearl Necklace', 'Accessory', 'Necklace', 'Women',
 'White', 'Pearl', 'Formal', 'High', 'All Weather'),

(10, 'Black Choker', 'Accessory', 'Choker', 'Women',
 'Black', 'Fabric', 'Party', 'High', 'All Weather'),

-- Bracelets and rings
(10, 'Gold Bracelet', 'Accessory', 'Bracelet', 'Women',
 'Gold', 'Metal', 'Party', 'High', 'All Weather'),

(10, 'Silver Bangles', 'Accessory', 'Bangles', 'Women',
 'Silver', 'Metal', 'Casual', 'Medium', 'All Weather'),

(10, 'Silver Finger Ring', 'Accessory', 'Ring', 'Unisex',
 'Silver', 'Metal', 'Party', 'High', 'All Weather'),

-- Hair accessories
(10, 'Black Hair Claw Clip', 'Accessory', 'Hair Clip', 'Women',
 'Black', 'Plastic', 'Casual', 'High', 'All Weather'),

(10, 'Pink Scrunchie', 'Accessory', 'Scrunchie', 'Women',
 'Pink', 'Fabric', 'Casual', 'High', 'All Weather'),

-- Other accessories
(10, 'Black Sunglasses', 'Accessory', 'Sunglasses', 'Unisex',
 'Black', 'Plastic', 'Casual', 'High', 'Hot'),

(10, 'Brown Leather Belt', 'Accessory', 'Belt', 'Unisex',
 'Brown', 'Leather', 'Formal', 'High', 'All Weather');
 
 INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES

(10, 'Black Party Clutch', 'Bag', 'Clutch', 'Women',
 'Black', 'Leather', 'Party', 'High', 'All Weather'),

(10, 'Brown Handbag', 'Bag', 'Handbag', 'Women',
 'Brown', 'Leather', 'Casual', 'High', 'All Weather'),

(10, 'Black Sling Bag', 'Bag', 'Sling Bag', 'Women',
 'Black', 'Leather', 'Casual', 'High', 'All Weather'),

(10, 'Beige Tote Bag', 'Bag', 'Tote Bag', 'Women',
 'Beige', 'Canvas', 'Casual', 'High', 'All Weather'),

(10, 'Blue Backpack', 'Bag', 'Backpack', 'Unisex',
 'Blue', 'Polyester', 'College', 'High', 'All Weather'),

(10, 'White Shoulder Bag', 'Bag', 'Shoulder Bag', 'Women',
 'White', 'Leather', 'Party', 'High', 'All Weather');
 
 USE fastyle;

INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES

(10, 'White Sneakers', 'Footwear', 'Sneakers', 'Unisex',
 'White', 'Canvas', 'Casual', 'High', 'All Weather'),

(10, 'Black Formal Shoes', 'Footwear', 'Formal Shoes', 'Unisex',
 'Black', 'Leather', 'Formal', 'Medium', 'All Weather'),

(10, 'Brown Loafers', 'Footwear', 'Loafers', 'Unisex',
 'Brown', 'Leather', 'Formal', 'High', 'All Weather'),

(10, 'Pink Sandals', 'Footwear', 'Sandals', 'Women',
 'Pink', 'Synthetic', 'Casual', 'High', 'Hot'),

(10, 'Black Flats', 'Footwear', 'Flats', 'Women',
 'Black', 'Leather', 'Casual', 'High', 'All Weather'),

(10, 'Brown Ankle Boots', 'Footwear', 'Boots', 'Unisex',
 'Brown', 'Leather', 'Travel', 'Medium', 'Cold'),

(10, 'Sports Running Shoes', 'Footwear', 'Sports Shoes', 'Unisex',
 'Grey', 'Mesh', 'Sports', 'High', 'Hot'),

(10, 'Silver Party Heels', 'Footwear', 'Heels', 'Women',
 'Silver', 'Synthetic', 'Party', 'Medium', 'All Weather');
 
 SELECT item_id, item_name, item_category, item_type,
       color, material, occasion, weather_type
FROM user_10_wardrobeitems
WHERE item_category IN ('Accessory', 'Bag')
ORDER BY item_category, item_type;

USE fastyle;

INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES
(10, 'Gold Finger Ring', 'Accessory', 'Ring', 'Women',
 'Gold', 'Metal', 'Party', 'High', 'All Weather'),

(10, 'Silver Finger Ring', 'Accessory', 'Ring', 'Women',
 'Silver', 'Metal', 'Casual', 'High', 'All Weather'),

(10, 'Diamond Stone Ring', 'Accessory', 'Ring', 'Women',
 'Silver', 'Stone', 'Party', 'Medium', 'All Weather'),

(10, 'Pearl Ring', 'Accessory', 'Ring', 'Women',
 'White', 'Pearl', 'Formal', 'High', 'All Weather'),

(10, 'Rose Gold Ring', 'Accessory', 'Ring', 'Women',
 'Rose Gold', 'Metal', 'Party', 'High', 'All Weather'),

(10, 'Black Fashion Ring', 'Accessory', 'Ring', 'Unisex',
 'Black', 'Metal', 'Casual', 'High', 'All Weather');
 SELECT *
FROM user_10_wardrobeitems;

USE fastyle;

USE fastyle;

INSERT INTO user_10_wardrobeitems
(user_id, item_name, item_category, item_type, suitable_for,
 color, material, occasion, comfort_level, weather_type)
VALUES

-- TOPS
(10, 'White Casual T-Shirt', 'Clothing', 'Top', 'Unisex',
 'White', 'Cotton', 'College', 'High', 'Hot'),

(10, 'Blue Denim Shirt', 'Clothing', 'Top', 'Unisex',
 'Blue', 'Denim', 'College', 'Medium', 'All Weather'),

(10, 'Pink Casual Kurti', 'Clothing', 'Top', 'Women',
 'Pink', 'Cotton', 'College', 'High', 'Hot'),

(10, 'Black Oversized T-Shirt', 'Clothing', 'Top', 'Unisex',
 'Black', 'Cotton', 'College', 'High', 'Hot'),

(10, 'Green Checked Shirt', 'Clothing', 'Top', 'Unisex',
 'Green', 'Cotton', 'College', 'High', 'All Weather'),

-- BOTTOMS
(10, 'Blue Straight Fit Jeans', 'Clothing', 'Bottom', 'Unisex',
 'Blue', 'Denim', 'College', 'High', 'All Weather'),

(10, 'Black Wide Leg Trousers', 'Clothing', 'Bottom', 'Women',
 'Black', 'Cotton', 'College', 'High', 'Hot'),

(10, 'Beige Cargo Pants', 'Clothing', 'Bottom', 'Unisex',
 'Beige', 'Cotton', 'College', 'High', 'Hot'),

(10, 'Grey Joggers', 'Clothing', 'Bottom', 'Unisex',
 'Grey', 'Cotton', 'College', 'High', 'Cold'),

(10, 'Blue Denim Skirt', 'Clothing', 'Bottom', 'Women',
 'Blue', 'Denim', 'College', 'Medium', 'Hot'),

-- FOOTWEAR
(10, 'White College Sneakers', 'Footwear', 'Sneakers', 'Unisex',
 'White', 'Canvas', 'College', 'High', 'All Weather'),

(10, 'Black Casual Loafers', 'Footwear', 'Loafers', 'Unisex',
 'Black', 'Leather', 'College', 'High', 'All Weather'),

(10, 'Brown College Sandals', 'Footwear', 'Sandals', 'Women',
 'Brown', 'Synthetic', 'College', 'High', 'Hot'),

-- BAGS
(10, 'Black College Backpack', 'Bag', 'Backpack', 'Unisex',
 'Black', 'Polyester', 'College', 'High', 'All Weather'),

(10, 'Beige College Tote', 'Bag', 'Tote Bag', 'Women',
 'Beige', 'Canvas', 'College', 'High', 'All Weather'),

-- ACCESSORIES
(10, 'Silver College Stud Earrings', 'Accessory', 'Earrings', 'Women',
 'Silver', 'Metal', 'College', 'High', 'All Weather'),

(10, 'Simple Silver Pendant', 'Accessory', 'Necklace', 'Women',
 'Silver', 'Metal', 'College', 'High', 'All Weather'),

(10, 'Casual Wrist Watch', 'Accessory', 'Watch', 'Unisex',
 'Black', 'Metal', 'College', 'High', 'All Weather'),

(10, 'Black College Hair Clip', 'Accessory', 'Hair Clip', 'Women',
 'Black', 'Plastic', 'College', 'High', 'All Weather');
 
 

SELECT item_id, item_name, item_category, item_type, occasion
FROM user_10_wardrobeitems
WHERE item_category = 'Accessory'
AND (
    item_type LIKE '%Ring%'
    OR item_name LIKE '%Ring%'
);
