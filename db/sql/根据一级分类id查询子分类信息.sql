SELECT
	f.id,
	f.`name`,
	f.type,
	f.father_id,
	c.id AS sub_id,
	c.`name` AS sub_name,
	c.type AS sub_type,
	c.father_id AS sub_father_id 
FROM
	`category` AS f
	LEFT JOIN `category` AS c ON f.id = c.father_id 
WHERE
	f.father_id = 1