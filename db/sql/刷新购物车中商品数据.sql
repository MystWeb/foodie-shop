SELECT t_items.id          AS item_id,
       t_items.item_name,
       t_items_img.url     AS item_img_url,
       t_items_spec.id     AS spec_id,
       t_items_spec.`name` AS spec_name,
       t_items_spec.price_discount,
       t_items_spec.price_normal
FROM items_spec AS t_items_spec
         LEFT JOIN items AS t_items ON t_items_spec.item_id = t_items.id
         LEFT JOIN items_img AS t_items_img ON t_items_img.item_id = t_items.id
WHERE t_items_img.is_main = 1
  AND t_items_spec.id IN (
                          '1',
                          '3',
                          '5')