SELECT
	ic.comment_level,
	ic.content,
	ic.sepc_name,
	ic.created_time,
	u.face AS user_face,
	u.nickname 
FROM
	items_comments AS ic
	LEFT JOIN users AS u ON ic.user_id = u.id 
WHERE
	ic.item_id = 'cake-1001' 
	AND ic.comment_level = 1;