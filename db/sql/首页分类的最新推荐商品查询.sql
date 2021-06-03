SELECT
	f.id AS root_cat_id,
	f.`name` AS root_cat_name,
	f.slogan,
	f.cat_image,
	f.bg_color,
	i.id AS item_id,
	i.item_name AS item_name,
	ii.url AS item_url,
	i.created_time 
FROM
	category f
	LEFT JOIN items i ON f.id = i.root_cat_id
	LEFT JOIN items_img ii ON i.id = ii.item_id 
WHERE
	f.type = 1 
	AND i.root_cat_id = 7 
	AND ii.is_main = 1 
ORDER BY
	i.created_time DESC 
	LIMIT 0,6