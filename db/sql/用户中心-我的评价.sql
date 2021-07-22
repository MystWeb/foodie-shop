SELECT ic.id        AS comment_id,
       ic.content,
       ic.created_time,
       ic.item_id,
       ic.item_name,
       ic.item_spec_id,
       ic.sepc_name AS item_spec_name,
       ii.url       AS item_img
FROM items_comments AS ic
         LEFT JOIN items_img ii ON ic.item_id = ii.item_id
WHERE ic.user_id = '1908017YR51G1XWH'
  AND ii.is_main = 1
ORDER BY ic.created_time DESC