SELECT
	i.id AS item_id,
	i.item_name,
	i.sell_counts,
	ii.url AS img_url,
	temp_spec.price_discount AS price 
FROM
	items AS i
	LEFT JOIN items_img AS ii ON i.id = ii.item_id
	LEFT JOIN ( SELECT item_id, MIN( price_discount ) AS price_discount FROM items_spec GROUP BY item_id ) AS temp_spec ON i.id = temp_spec.item_id 
WHERE
	ii.is_main = 1;