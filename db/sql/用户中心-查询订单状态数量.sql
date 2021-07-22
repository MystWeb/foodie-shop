SELECT
	COUNT( 0 ) 
FROM
	orders AS o
	LEFT JOIN order_status AS os ON o.id = os.order_id 
WHERE
	o.user_id = '1908189H7TNWDTXP' 
	AND os.order_status = 20
	AND o.is_comment = 0