SELECT
	os.order_id,
	os.order_status,
	os.created_time,
	os.pay_time,
	os.deliver_time,
	os.success_time,
	os.close_time,
	os.comment_time 
FROM
	orders AS o
	LEFT JOIN order_status AS os ON o.id = os.order_id 
WHERE
	o.is_delete = 0 
	AND o.user_id = '1908189H7TNWDTXP' 
	AND os.order_status IN ( 20, 30, 40 ) 
ORDER BY
	os.order_id DESC