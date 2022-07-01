-- band mainRows
select row_number() over (order by dp.designation) as enum,
       d.designation                               as dev,
       cdev.name                                   as devprod,
       dp.designation                              as devpt,
       cpart.name                                  as devptdev,
       extract(year from dlp.create_ts)               yr
from mobdekbkp_device_list_project_entry e
         join mobdekbkp_typonominal tn on tn.id = e.typonominal_id
         join mobdekbkp_device_list_project dlp on e.device_list_project_id = dlp.id
         join mobdekbkp_device d on d.device_project_list_id = dlp.id
         join mobdekbkp_device_part_list_planned dplp on dplp.device_id = d.id
         join mobdekbkp_device_part dp on dp.id = dplp.device_part_id
         join mobdekbkp_company cpart on cpart.id = dp.developer_id
         join mobdekbkp_company cdev on cdev.id = d.developer_id
where tn.id = ${element}

-- connector field - id
