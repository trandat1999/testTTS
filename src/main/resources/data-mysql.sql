INSERT INTO `manager`.`tbl_category` (`created_by`, `last_modified_by`,`name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'Quần áo' as name) AS tmp
WHERE NOT EXISTS (
        SELECT name FROM `manager`.`tbl_category` WHERE name = 'Quần áo'
    ) LIMIT 1;
INSERT INTO `manager`.`tbl_category` (`created_by`, `last_modified_by`,`name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'Tủ lạnh' as name) AS tmp
WHERE NOT EXISTS (
        SELECT name FROM `manager`.`tbl_category` WHERE name = 'Tủ lạnh'
    ) LIMIT 1;
INSERT INTO `manager`.`tbl_category` (`created_by`, `last_modified_by`,`name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'Tivi' as name) AS tmp
WHERE NOT EXISTS (
        SELECT name FROM `manager`.`tbl_category` WHERE name = 'Tivi'
    ) LIMIT 1;
INSERT INTO `manager`.`tbl_category` (`created_by`, `last_modified_by`,`name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'Điện thoại' as name) AS tmp
WHERE NOT EXISTS (
        SELECT name FROM `manager`.`tbl_category` WHERE name = 'Điện thoại'
    ) LIMIT 1;

    
INSERT INTO `manager`.`tbl_product` (`created_by`, `last_modified_by`,`name`, `short_description`, `price`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'iphone 11 pro' as name, 'điện thoại của hãng apple' as short_description, 1000000 as price) AS tmp
WHERE NOT EXISTS (
        SELECT name FROM `manager`.`tbl_product` WHERE name = 'iphone 11 pro'
    ) LIMIT 1;
    
INSERT INTO `manager`.`product_category` (`product_id`, `category_id`)
SELECT * FROM (SELECT 1 as product_id, 4 as category_id) AS tmp
WHERE NOT EXISTS (
        SELECT * FROM `manager`.`product_category` WHERE product_id = 1 AND category_id=4
    ) LIMIT 1;