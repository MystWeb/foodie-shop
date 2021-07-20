SELECT
	od.id AS order_id,
	od.created_time,
	od.pay_method,
	od.real_pay_amount,
	od.post_amount,
	od.is_comment,
	os.order_status,
	oi.item_id,
	oi.item_name,
	oi.item_img,
	oi.item_spec_id,
	oi.item_spec_name,
	oi.buy_counts,
	oi.price 
FROM
	orders AS od
	LEFT JOIN order_status AS os ON od.id = os.order_id
	LEFT JOIN order_items AS oi ON od.id = oi.order_id 
WHERE
	od.user_id = '1908189H7TNWDTXP' 
	AND od.is_delete = '0' 
ORDER BY
	od.updated_time ASC